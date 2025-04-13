package org.healthapp.infrastructure.handler.interfaces

interface HandleRegistry {
    fun getHandler(requestType: String): RequestHandler?
}