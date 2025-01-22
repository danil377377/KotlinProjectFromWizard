package org.example.project.data.Preferences

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

actual class PlatformHttpClient actual constructor() {
    actual val httpClient = HttpClient(Darwin) {
        install(ContentNegotiation) {
            json()
        }
    }
}