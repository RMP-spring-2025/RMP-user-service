package org.healthapp.infrastructure.handler

import org.healthapp.infrastructure.handler.interfaces.HandleRegistry
import org.healthapp.infrastructure.handler.interfaces.RequestHandler

class DefaultHandleRegistry(private val handlers: Map<String, RequestHandler>) : HandleRegistry {
    override suspend fun getHandler(requestType: String): RequestHandler? = handlers[requestType]

}