package org.healthapp.infrastructure.handler.interfaces

import org.healthapp.infrastructure.dto.Response
import org.healthapp.infrastructure.request.Request

interface RequestHandler {
    fun handle(request: Request): Response
}