package org.healthapp.infrastructure.handler.interfaces

interface HandleRegistry {
    suspend fun getHandler(requestType: String): RequestHandler?
}