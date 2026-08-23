import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.json.JSONObject

class ClientServer {
    private val encoder = Encode()
    private val appID = "nabytek.cz"
    private val scope = "name"
    private val secret = "HolaHolaOpratkaVola"
    private val redirect = "http://127.0.0.1:5500/Client/redirect.html"
    private val state = "seruNaVšechno"

    private val userData = HashMap<String, String>()

    fun run(port: Int) {
        userData["name"] = "Samuel Kodytek"
        userData["description"] = "Some random guy"
        userData["gender"] = "male?"

        println("Starting client server on port $port")
        embeddedServer(Netty, port = port) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(CORS) {
                anyHost()
                allowHost("127.0.0.1:5500", schemes = listOf("http"))
                allowMethod(HttpMethod.Post)
                allowHeader(HttpHeaders.ContentType)
                allowHeader(HttpHeaders.Authorization)
            }

            routing {
                get("/getInfo"){
                    call.respond(mapOf(
                        "id" to appID,
                        "redirect" to redirect,
                        "state" to state,
                        "scope" to scope
                    ))
                }
                post("/getToken") {
                    println("Client: Someone is trying to exchange token")
                    val message = call.receive<Map<String, String>>()
                    val client = HttpClient(CIO)
                    if(state == message["state"]) {
                        val json = JSONObject()
                            .put("code", message["code"])
                            .put("state", state)
                            .put("id", appID)
                            .put("redirect", redirect)
                            .put("scope", scope)
                            .put("secret", secret)

                        val response = client.post("http://localhost:8000/getToken") {
                            contentType(ContentType.Application.Json)
                            setBody(json.toString())
                        }
                        val result = response.bodyAsText()
                        println("Client: Token: $result")
                        call.respondText(result)
                    }else{
                        println("Client: State does not match")
                    }
                    client.close()
                }
                post("/getData"){
                    val client = HttpClient(CIO)
                    val message = call.receive<Map<String, String>>()
                    val token = call.request.headers["Authorization"]
                    println(message.toString())
                    println("Client: Someone is trying to access " + message["scope"])
                    val response = client.post("http://localhost:8000/verifyToken") {
                        contentType(ContentType.Text.Plain)
                        setBody(token)
                    }

                    if(response.bodyAsText().toBoolean()){
                        val decodedPayload = encoder.decodeBase64(token.toString().split(".")[1])
                        val jsonPayload = JSONObject(decodedPayload)
                        val allowedScope = jsonPayload.getString("scope")
                        val askedScope = message["scope"]

                        if(allowedScope == askedScope){
                            println("Client: Scope correct, fetching data")
                            call.respondText(userData[askedScope].toString())
                        }else{
                            println("Client: Asked data is out of your scope")
                        }
                    }else{
                        println("Client: Token invalid")
                    }
                    client.close()
                }
            }
        }.start(wait = true)
    }
}