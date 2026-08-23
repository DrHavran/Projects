import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OAUTHServer {
    private val oAUTHSecret = "UzNatoSeru:D"
    private val encoder = Encode()
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val JWT = JWTFunctions(oAUTHSecret)

    fun run(port: Int){
        println("Starting OAUTH server on port $port")
        val allowedSites = HashMap<String, String>()
        allowedSites["nabytek.cz"] = "HolaHolaOpratkaVola"

        val activeCodes = HashMap<String, HashMap<String, String>>()

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
                allowHost("localhost:8080", schemes = listOf("http"))
                allowMethod(HttpMethod.Post)
                allowHeader(HttpHeaders.ContentType)
            }

            routing {
                post("/allowedUsers"){
                    val message = call.receive<Map<String, String>>()
                    println("OAUTH: Checking user: " + message["id"])
                    if(allowedSites.contains(message["id"])){
                        println("OAUTH: " + message["id"] + " is a valid user")
                        call.respond(true)
                    }else{
                        println("OAUTH: " + message["id"] + " isn't a valid user")
                        call.respond(false)
                    }
                }
                post("/getCode"){
                    val message = call.receive<Map<String, String>>()
                    println("OAUTH: Generating code for user from site: " + message["id"])
                    val time = LocalDateTime.now().plusSeconds(30).toString()
                    val code = message["id"]+message["redirect_uri"]+message["state"]+message["scope"]+time
                    val hash = encoder.hash(code, oAUTHSecret)
                    val base  = encoder.encodeBase64(hash)
                    call.respondText(base)

                    val map = HashMap<String, String>()
                    map["id"] = message["id"].toString()
                    map["redirect_uri"] = message["redirect_uri"].toString()
                    map["state"] = message["state"].toString()
                    map["scope"] = message["scope"].toString()
                    map["expiration"] = time

                    println("OAUTH: Generated code: $base, expiring in $time")
                    activeCodes[base] = map
                }
                post("/getToken"){
                    val message = call.receive<Map<String, String>>()
                    val code = message["code"]
                    println("OAUTH: User from " + message["id"] + " is trying to switch code " + code + " for a token")
                    if(activeCodes.contains(code)){
                        println("OAUTH: This code is in the database")
                        val specificMap = activeCodes[code]
                        activeCodes.remove(code)
                        if( message["id"] == specificMap?.get("id") &&
                            message["redirect"] == specificMap?.get("redirect_uri") &&
                            message["state"] == specificMap?.get("state") &&
                            message["scope"] == specificMap?.get("scope"))
                        {
                            if(message["secret"] == allowedSites[message["id"]]){
                                val expirationTime = LocalDateTime.parse(specificMap?.get("expiration").toString(), formatter)
                                if (LocalDateTime.now().isBefore(expirationTime)) {
                                    println("OAUTH: Code valid, generating token")
                                    val token = JWT.buildToken(message["id"].toString(), message["scope"].toString())
                                    call.respondText(token)
                                } else {
                                    println("OAUTH: Code expired")
                                }
                            }else{
                                println("OAUTH: Wrong secret!")
                            }
                        }else{
                            println("OAUTH: Info does not match")
                        }
                    }else {
                        println("OAUTH: Code isn't registered")
                    }
                }
                post("/verifyToken"){
                    val message = call.receive<String>()
                    println("OAUTH: Trying to verify a token: $message")
                    if(JWT.checkToken(message)){
                        println("OAUTH: Token is valid")
                        call.respond(true)
                    }else{
                        println("OAUTH: Token is invalid")
                        call.respond(false)
                    }
                }
            }
        }.start(wait = false)
    }
}