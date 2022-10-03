package br.com.tecnomotor.thanos.model.menu

import br.com.tecnomotor.thanos.database.menu.entity.AplicacaoEntity
import br.com.tecnomotor.thanos.util.ConvertClass

class Aplicacao(): AbstractMenu() {
    var modulo: Long = 0.toLong()
    var reparacao: Long = 0.toLong()
    var manOperacao: String = ""

    constructor(id: Long, modulo: Long) : this() {
        this.id = id
        this.modulo = modulo
    }

    override fun clear() {
        super.clear()
        this.modulo = 0.toLong()
        this.reparacao = 0.toLong()
        this.manOperacao = ""
    }

    override fun toString(): String {
        return "Aplicacao(${super.toString()},modulo='$modulo',reparacao='$reparacao',manOperacao='$manOperacao')"
    }
}

fun AplicacaoEntity.toAplicacao(): Aplicacao {
    return ConvertClass.convert(this, Aplicacao::class.java.name) as Aplicacao
}