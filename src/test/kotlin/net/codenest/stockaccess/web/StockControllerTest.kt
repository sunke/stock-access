package net.codenest.stockaccess.web

import com.fasterxml.jackson.databind.ObjectMapper
import net.codenest.stockaccess.model.Stock
import net.codenest.stockaccess.service.StockService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDateTime


@WebMvcTest(StockController::class)
class StockControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var stockService: StockService

    @Test
    @WithMockUser(username = "user", password = "password", roles = ["USER"])
    fun testGetAllStocks() {
        val stock = Stock(1L, "Test Stock", BigDecimal("100.0"), LocalDateTime.now())
        val stocks = listOf(stock)
        val page = PageImpl(stocks)

        whenever(stockService.getAllStocks(any())).thenReturn(page)

        mockMvc.perform(
            get("/api/stocks")
                .param("page", "0")
                .param("size", "10")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].id").value(1))
            .andExpect(jsonPath("$.content[0].name").value("Test Stock"))
            .andExpect(jsonPath("$.content[0].currentPrice").value(100.0))
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = ["USER"])
    fun testGetStockById() {
        val stock = Stock(1L, "Test Stock", BigDecimal("100.0"), LocalDateTime.now())

        whenever(stockService.getStockById(1L)).thenReturn(stock)

        mockMvc.perform(get("/api/stocks/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test Stock"))
            .andExpect(jsonPath("$.currentPrice").value(100.0))
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = ["USER"])
    fun testCreateStock() {
        val stock = Stock(null, "New Stock", BigDecimal("150.0"), null)
        val savedStock = Stock(1L, "New Stock", BigDecimal("150.0"), LocalDateTime.now())

        whenever(stockService.createStock(any())).thenReturn(savedStock)

        mockMvc.perform(
            post("/api/stocks").with(csrf()).param("action", "signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stock))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("New Stock"))
            .andExpect(jsonPath("$.currentPrice").value(150.0))
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = ["USER"])
    fun testUpdateStockPrice() {
        val stockUpdate = Stock(null, "Not Used", BigDecimal("200.0"), null)
        val updatedStock = Stock(1L, "Test Stock", BigDecimal("200.0"), LocalDateTime.now())

        whenever(stockService.updateStockPrice(eq(1L), any())).thenReturn(updatedStock)

        mockMvc.perform(
            patch("/api/stocks/1").with(csrf()).param("action", "signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockUpdate))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test Stock"))
            .andExpect(jsonPath("$.currentPrice").value(200.0))
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = ["USER"])
    fun testDeleteStock() {
        doNothing().whenever(stockService).deleteStock(1L)

        mockMvc.perform(delete("/api/stocks/1").with(csrf()).param("action", "signup"))
            .andExpect(status().isNoContent)
    }
}