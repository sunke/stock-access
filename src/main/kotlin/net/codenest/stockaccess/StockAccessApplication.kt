package net.codenest.stockaccess

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockAccessApplication

fun main(args: Array<String>) {
    runApplication<StockAccessApplication>(*args)
}