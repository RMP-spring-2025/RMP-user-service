package org.healthapp.infrastructure.dto

import kotlinx.serialization.Serializable


/**
 * Data Transfer Object (DTO) for product statistics request.
 *
 * This class represents a request to retrieve statistics for a specific product for Product MicroService
 *
*/

@Serializable
data class ProductStatRequestDTO(val productId: Long) {
}