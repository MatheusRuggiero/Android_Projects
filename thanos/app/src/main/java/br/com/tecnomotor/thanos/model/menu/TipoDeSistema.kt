package br.com.tecnomotor.thanos.model.menu

import br.com.tecnomotor.thanos.database.menu.entity.TipoSistemaEntity
import br.com.tecnomotor.thanos.util.ConvertClass

class TipoDeSistema(): AbstractMenuBase() {
    constructor(id: Long, nome: String) : this() {
        this.id = id
        this.nome = nome
    }

    override fun toString(): String {
        return "TipoDeSistema(${super.toString()})"
    }
}

fun TipoSistemaEntity.toTipoDeSistema(): TipoDeSistema {
    return ConvertClass.convert(this, TipoDeSistema::class.java.name) as TipoDeSistema
}