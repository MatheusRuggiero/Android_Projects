package br.com.tecnomotor.thanos.model.menu

import br.com.tecnomotor.thanos.database.menu.entity.MotorizacaoEntity
import br.com.tecnomotor.thanos.util.ConvertClass

class Motorizacao(): AbstractMenuBase() {

    constructor(id: Long, nome: String) : this() {
        this.id = id
        this.nome = nome
    }

    override fun toString(): String {
        return "Motorizacao(${super.toString()})"
    }
}

fun MotorizacaoEntity.toMotorizacao(): Motorizacao {
    return ConvertClass.convert(this, Motorizacao::class.java.name) as Motorizacao
}