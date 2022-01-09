package voicu.babiciu.lab07

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

class ServerSocketThread(
    private val
    onMessageReceived: ((String) -> Unit)
) : Thread() {
    private var socket: Socket? = null
    private var isRunning = true
    private val serverSocket = ServerSocket(8089)
    override fun run() {

        try {
            // Creati ServerSocket
            serverSocket.reuseAddress = true
            onMessageReceived.invoke(
                "Server listening on ${
                    serverSocket.localSocketAddress
                }"
            )
            while (isRunning) {
                println("Se așteaptă conectarea clientului..")
                socket =
                    serverSocket.accept()
                onMessageReceived.invoke(
                    "Client ${
                        socket?.remoteSocketAddress
                            .toString().replace("/", "")
                    } " +
                            "connected"
                )
                startReader(socket!!)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun start() {
        isRunning = true
        super.start()
    }

    fun close() {
        serverSocket.close()
        socket?.close()
        isRunning = false
        onMessageReceived.invoke("Server closed")
    }

    private fun startReader(mSocket: Socket) {
        object : Thread() {
            override fun run() {
                try {
                    // Obțineți fluxul de citire
                    val `in` = BufferedReader(
                        InputStreamReader(
                            mSocket.getInputStream(),
                            "utf-8"
                        )
                    )
                    var line = ""
                    while (`in`.readLine()
                            .also { line = it } != null
                    ) { // Citiți datele
                        println("mesajul receptionat: $line")
                        val dateFormat = SimpleDateFormat(
                            "dd/M/yyyy hh:mm:ss",
                            Locale.getDefault()
                        )
                        onMessageReceived.invoke("${dateFormat.format(Date())}: $line")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

}