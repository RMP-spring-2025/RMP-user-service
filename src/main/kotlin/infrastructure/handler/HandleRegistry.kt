package org.healthapp.infrastructure.handler

interface HandleRegistry {
    fun getHandler(requestType: String) : RequestHandler?
}