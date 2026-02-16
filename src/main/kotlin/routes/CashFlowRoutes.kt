package org.delcom.routes

import io.ktor.server.routing.*
import org.delcom.controllers.CashFlowController

fun Route.cashFlowRoutes() {

    val controller = CashFlowController()

    route("/cash-flows") {

        post("/setup") {
            controller.setupData(call)
        }

        get {
            controller.getAll(call)
        }

        get("/{id}") {
            controller.getById(call)
        }

        post {
            controller.create(call)
        }

        put("/{id}") {
            controller.update(call)
        }

        delete("/{id}") {
            controller.delete(call)
        }
    }
}
