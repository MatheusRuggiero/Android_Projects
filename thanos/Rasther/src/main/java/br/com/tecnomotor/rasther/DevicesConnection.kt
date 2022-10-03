package br.com.tecnomotor.rasther

data class DevicesConnection(
    var devicesStatus: DevicesStatus = DevicesStatus.NONE,
    var message: String = "",
    var error: String = ""
) {
    enum class DevicesStatus {
        NONE, CONNECTING, CONNECTED, DISCONNECTED, FAIL
    }

    fun setStatus(value: DevicesStatus, message: String = "", error: String = "") {
        this.devicesStatus = value
        this.message = message
        this.error = error
    }
}