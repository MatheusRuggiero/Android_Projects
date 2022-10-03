package br.com.tecnomotor.rasther

data class RastherInfo(
    var montadoras: ArrayList<Int> = arrayListOf(),
    var versaoFirmware: String = "",
    var numSerie: String = "",
    var bootId: Int = 0,
    var versao: Int = 0,
    var plataforma: Int = 0)
