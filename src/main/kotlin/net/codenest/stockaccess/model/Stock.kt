package net.codenest.stockaccess.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Stock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:NotBlank(message = "Stock name is required")
    val name: String,

    @field:NotNull(message = "Current price is required")
    @field:Positive(message = "Price must be positive")
    val currentPrice: BigDecimal,

    var lastUpdate: LocalDateTime? = null
)