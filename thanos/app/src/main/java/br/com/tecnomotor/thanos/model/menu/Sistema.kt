package br.com.tecnomotor.thanos.model.menu

import br.com.tecnomotor.thanos.database.menu.entity.SistemaAnoEntity
import br.com.tecnomotor.thanos.database.menu.entity.SistemaEntity
import br.com.tecnomotor.thanos.util.ConvertClass
import java.util.*

class Sistema(): AbstractMenuBase() {
    var anoInicial: Long = 0
    var anoFinal: Long = 0

    constructor(id: Long, nome: String, anoInicial: Long, anoFinal: Long) : this() {
        this.id = id
        this.nome = nome
        this.anoInicial = anoInicial
        this.anoFinal = anoFinal
    }

    override fun clear() {
        super.clear()
        this.anoInicial = 0
        this.anoFinal = 0
    }

    fun getNomeAno(): String {
        var nomeAno = ""
        val anoAtual = Calendar.getInstance().get(Calendar.YEAR)
        val anoInicial = if (this.anoInicial > 0) this.anoInicial else "*"
        val anoFinal = if (this.anoFinal > 0) this.anoFinal else anoAtual
        if ((this.anoInicial > 0) || (this.anoFinal > 0)){
            nomeAno ="${this.nome} ($anoInicial - $anoFinal)"
        } else nomeAno = this.nome
        return nomeAno
    }

    override fun toString(): String {
        return "Sistema(${super.toString()},anoInicial='$anoInicial',anoFinal='$anoFinal')"
    }
}

fun SistemaEntity.toSistema(): Sistema {
    return ConvertClass.convert(this, Sistema::class.java.name) as Sistema
}

fun SistemaAnoEntity.toSistema(): Sistema {
    return ConvertClass.convert(this, Sistema::class.java.name) as Sistema
}