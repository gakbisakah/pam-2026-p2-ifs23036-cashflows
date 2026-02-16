package org.delcom.services

import org.delcom.entities.CashFlow
import org.delcom.models.CashFlowQuery
import java.util.UUID

class CashFlowService {

    private val cashFlows = mutableListOf<CashFlow>()

    fun getAllCashFlows(query: CashFlowQuery): List<CashFlow> {

        return cashFlows.filter {

            (query.type == null || it.type.equals(query.type, true)) &&
                    (query.source == null || it.source.equals(query.source, true)) &&
                    (query.search == null || it.description.contains(query.search, true)) &&
                    (query.gteAmount == null || it.amount >= query.gteAmount) &&
                    (query.lteAmount == null || it.amount <= query.lteAmount)
        }
    }

    fun getCashFlowById(id: String): CashFlow? {
        return cashFlows.find { it.id == id }
    }

    fun createRawCashFlow(
        id: String,
        type: String,
        source: String,
        label: String,
        amount: Double,
        description: String,
        createdAt: String,
        updatedAt: String
    ) {
        cashFlows.add(
            CashFlow(id, type, source, label, amount, description, createdAt, updatedAt)
        )
    }

    fun createCashFlow(
        type: String,
        source: String,
        label: String,
        amount: Double,
        description: String
    ): String {

        val id = UUID.randomUUID().toString()

        val cashFlow = CashFlow(
            id,
            type,
            source,
            label,
            amount,
            description,
            "15-02-2026",
            "15-02-2026"
        )

        cashFlows.add(cashFlow)
        return id
    }

    fun updateCashFlow(id: String, updated: CashFlow): Boolean {
        val index = cashFlows.indexOfFirst { it.id == id }
        if (index == -1) return false

        cashFlows[index] = updated.copy(id = id)
        return true
    }

    fun removeCashFlow(id: String): Boolean {
        return cashFlows.removeIf { it.id == id }
    }
}
