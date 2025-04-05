package org.healthapp.infrastructure.handler

import org.healthapp.infrastructure.request.Request

interface RequestHandler {
    fun handle(request: Request): String
}