package org.healthapp.infrastructure.handler

class DefaultHandleRegistry(private val handlers: Map<String, RequestHandler>): HandleRegistry {
    override fun getHandler(requestType: String): RequestHandler? = handlers[requestType]

}