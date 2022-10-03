package br.com.tecnomotor.thanos.model.diagnostico

import br.com.tecnomotor.thanos.database.messages.entity.ValueEntity

class Leitura(
    val id: Int,
    val tipo: TipoLeitura,
    val nome: String,
    var valor: Any,
    var unidade: Unidade,
    var checked: Boolean = false
) {
    override fun toString(): String {
        return "Leitura(id=$id, nome='$nome', valor=$valor, unidade=$unidade)"
    }
}

fun ValueEntity.toLeitura(): Leitura {
    return Leitura(
        0,
        TipoLeitura.AnalogText,
        this.valuePtLong ?: "",
        0.00,
        Unidade(0, "teste")
    )
}