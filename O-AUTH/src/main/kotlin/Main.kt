fun main() {
    val oAUTHServer = OAUTHServer()
    val clientServer = ClientServer()

    oAUTHServer.run(8000)
    clientServer.run(8080)
}
