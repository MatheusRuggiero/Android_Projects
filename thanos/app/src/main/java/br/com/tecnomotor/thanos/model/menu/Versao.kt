package br.com.tecnomotor.thanos.model.menu

import br.com.tecnomotor.thanos.database.menu.entity.VersaoEntity
import br.com.tecnomotor.thanos.util.ConvertClass

class Versao(): AbstractMenu() {

    constructor(id: Long) : this() {
        this.id = id
    }

    override fun toString(): String {
        return "Versao(id='$id')"
    }
}

fun VersaoEntity.toVersao():Versao {
    return ConvertClass.convert(this, Versao::class.java.name) as Versao
}