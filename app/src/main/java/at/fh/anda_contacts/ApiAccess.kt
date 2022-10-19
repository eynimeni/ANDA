package at.fh.anda_contacts
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ApiContact(val id: Int, val name: String, val telephoneNumber: Int, val age: Int)

fun createHttpClient() = HttpClient(Android) {
    expectSuccess = true
    engine {
        connectTimeout = 60000
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}