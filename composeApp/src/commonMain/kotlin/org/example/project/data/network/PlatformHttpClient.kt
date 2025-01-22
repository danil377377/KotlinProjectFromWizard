package org.example.project.data.Preferences

import io.ktor.client.HttpClient

expect class PlatformHttpClient() {
    val httpClient: HttpClient
}

