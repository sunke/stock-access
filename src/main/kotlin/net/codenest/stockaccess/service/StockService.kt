package net.codenest.stockaccess.service

import jakarta.persistence.EntityNotFoundException
import net.codenest.stockaccess.model.Stock
import net.codenest.stockaccess.repository.StockRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class StockService(private val stockRepository: StockRepository) {

    fun getAllStocks(pageable: Pageable): Page<Stock> {
        return stockRepository.findAll(pageable)
    }

    fun getStockById(id: Long): Stock {
        return stockRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Stock not found with id: $id") }
    }

    @Transactional
    fun createStock(stock: Stock): Stock {
        // Create a copy with updated timestamp
        val stockToSave = stock.copy(lastUpdate = LocalDateTime.now())
        return stockRepository.save(stockToSave)
    }

    @Transactional
    fun updateStockPrice(id: Long, stockUpdate: Stock): Stock {
        val existingStock = getStockById(id)
        // Create a copy with updated price and timestamp
        val updatedStock = existingStock.copy(
            currentPrice = stockUpdate.currentPrice,
            lastUpdate = LocalDateTime.now()
        )
        return stockRepository.save(updatedStock)
    }

    @Transactional
    fun deleteStock(id: Long) {
        val stock = getStockById(id)
        stockRepository.delete(stock)
    }
}