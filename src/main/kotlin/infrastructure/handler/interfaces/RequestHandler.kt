package org.healthapp.infrastructure.handler.interfaces

import org.healthapp.infrastructure.request.Request

interface RequestHandler {
    suspend fun handle(request: Request)
}