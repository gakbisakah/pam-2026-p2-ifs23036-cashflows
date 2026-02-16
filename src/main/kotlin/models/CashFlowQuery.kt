package org.delcom.models

data class CashFlowQuery(
    val type: String? = null,
    val source: String? = null,
    val search: String? = null,
    val gteAmount: Double? = null,
    val lteAmount: Double? = null
)
