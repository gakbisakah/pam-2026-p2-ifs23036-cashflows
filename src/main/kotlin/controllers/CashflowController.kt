package org.delcom.controllers

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.delcom.helpers.loadInitialData
import org.delcom.models.CashFlowQuery
import org.delcom.models.DataResponse
import org.delcom.services.CashFlowService
import org.delcom.entities.CashFlow

class CashFlowController {

    private val cashFlowService = CashFlowService()

    // ================= SETUP DATA =================
    suspend fun setupData(call: ApplicationCall) {

        val query = CashFlowQuery()
        val cashFlows = cashFlowService.getAllCashFlows(query)

        for (cashFlow in cashFlows) {
            cashFlowService.removeCashFlow(cashFlow.id)
        }

        val initCashFlows = loadInitialData()

        for (cashFlow in initCashFlows) {
            cashFlowService.createRawCashFlow(
                cashFlow.id,
                cashFlow.type,
                cashFlow.source,
                cashFlow.label,
                cashFlow.amount,
                cashFlow.description,
                cashFlow.createdAt,
                cashFlow.updatedAt,
            )
        }

        call.respond(
            DataResponse("success", "Berhasil memuat data awal", null)
        )
    }

    // ================= GET ALL =================
    suspend fun getAll(call: ApplicationCall) {

        val query = CashFlowQuery(
            type = call.request.queryParameters["type"],
            source = call.request.queryParameters["source"],
            search = call.request.queryParameters["search"],
            gteAmount = call.request.queryParameters["gteAmount"]?.toDoubleOrNull(),
            lteAmount = call.request.queryParameters["lteAmount"]?.toDoubleOrNull()
        )

        val data = cashFlowService.getAllCashFlows(query)

        call.respond(
            DataResponse("success", "Data ditemukan", data)
        )
    }

    // ================= GET BY ID =================
    suspend fun getById(call: ApplicationCall) {

        val id = call.parameters["id"]!!

        val data = cashFlowService.getCashFlowById(id)

        call.respond(
            DataResponse("success", "Data ditemukan", data)
        )
    }

    // ================= CREATE =================
    suspend fun create(call: ApplicationCall) {

        val request = call.receive<CashFlow>()

        val id = cashFlowService.createCashFlow(
            request.type,
            request.source,
            request.label,
            request.amount,
            request.description
        )

        call.respond(
            DataResponse("success", "Berhasil ditambahkan", mapOf("cashFlowId" to id))
        )
    }

    // ================= UPDATE =================
    suspend fun update(call: ApplicationCall) {

        val id = call.parameters["id"]!!
        val request = call.receive<CashFlow>()

        val success = cashFlowService.updateCashFlow(id, request)

        call.respond(
            DataResponse("success", "Berhasil diupdate", success)
        )
    }

    // ================= DELETE =================
    suspend fun delete(call: ApplicationCall) {

        val id = call.parameters["id"]!!

        val success = cashFlowService.removeCashFlow(id)

        call.respond(
            DataResponse("success", "Berhasil dihapus", success)
        )
    }
}
