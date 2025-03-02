package net.codenest.stockaccess.web

import jakarta.persistence.EntityNotFoundException
import jakarta.validation.Valid
import net.codenest.stockaccess.model.Stock
import net.codenest.stockaccess.service.StockService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stocks")
class StockController(private val stockService: StockService) {

    @GetMapping
    fun getAllStocks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Stock>> {
        return ResponseEntity.ok(stockService.getAllStocks(PageRequest.of(page, size)))
    }

    @GetMapping("/{id}")
    fun getStockById(@PathVariable id: Long): ResponseEntity<Stock> {
        return ResponseEntity.ok(stockService.getStockById(id))
    }

    @PostMapping
    fun createStock(@Valid @RequestBody stock: Stock): ResponseEntity<Stock> {
        return ResponseEntity(stockService.createStock(stock), HttpStatus.CREATED)
    }

    @PatchMapping("/{id}")
    fun updateStockPrice(
        @PathVariable id: Long,
        @Valid @RequestBody stockUpdate: Stock
    ): ResponseEntity<Stock> {
        return ResponseEntity.ok(stockService.updateStockPrice(id, stockUpdate))
    }

    @DeleteMapping("/{id}")
    fun deleteStock(@PathVariable id: Long): ResponseEntity<Void> {
        stockService.deleteStock(id)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }
}