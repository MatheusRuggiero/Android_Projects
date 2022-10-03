package br.com.tecnomotor.thanos.model.diagnostico

data class AnaliseGrafica (
    val id: Int,
    val nomeAnalise: String,
    val tipo: TipoLeitura,
    var valor: Number,
    var dp: Int,
    var valorMin: Double,
    var valorMax: Double,
    var unidade: Unidade
) {
    override fun toString(): String {
        return "AnaliseGrafica(id=$id, nomeAnalise='$nomeAnalise', tipo=$tipo, valor=$valor, dp=$dp, valorMin=$valorMin, valorMax=$valorMax, unidade=$unidade)"
    }
}
