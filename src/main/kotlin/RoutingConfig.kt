package org.delcom.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.delcom.routes.cashFlowRoutes

fun Application.configureRouting() {

    routing {
        cashFlowRoutes()
    }
}
