package org.delcom

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

import org.delcom.plugins.configureHTTP
import org.delcom.plugins.configureRouting
import org.delcom.plugins.configureSerialization

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureSerialization()
    configureRouting()
}
