package br.com.tecnomotor.rasther.xml

class ConectorConfig(
    val id: Int,
    val name: String,
    val bat: String,
    val gnd: String,
    val pin: Array<String> = Array(15) {""} //perguntar se o valor do array sempre sera 15
) {

    fun getPin(id: Int): String {
        return pin[id]
    }

    fun D0IsEnabled(): Boolean {
        return (pin.isNotEmpty() && pin.size > 2 && (getPin(1) != null) || (getPin(2) != null))
    }

    override fun toString(): String {
        return "ConectorConfig(id=$id, name='$name', bat='$bat', gnd='$gnd', pin=${pin.contentToString()})"
    }


}
