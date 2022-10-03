package br.com.tecnomotor.thanos.model.diagnostico

import br.com.tecnomotor.thanos.database.messages.entity.DtcEntity

class CodigoDefeito(
    val id: Long,
    val nome: String,
    var status: String = "Presente",
    var sintoma: String = "Falha mecânica"
) {
    override fun toString(): String {
        return "CodigoDefeito(id=$id, nome='$nome', status='$status', sintoma='$sintoma')"
    }
}

fun DtcEntity.toCodigoDefeito(): CodigoDefeito {
    return CodigoDefeito(
        this.id ?: 0,
        this.valuePtLong ?: ""
    )
}
