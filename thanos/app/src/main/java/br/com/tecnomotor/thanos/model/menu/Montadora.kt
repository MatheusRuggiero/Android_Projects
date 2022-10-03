package br.com.tecnomotor.thanos.model.menu

import br.com.tecnomotor.thanos.database.menu.entity.MontadoraEntity
import br.com.tecnomotor.thanos.util.ConvertClass

class Montadora(): AbstractMenuBase() {

    constructor(id: Long, nome: String) : this() {
        this.id = id
        this.nome = nome
    }

    override fun toString(): String {
        return "Montadora("+super.toString()+")"
    }
}

fun MontadoraEntity.toMontadora(): Montadora{
    return ConvertClass.convert(this, Montadora::class.java.name) as Montadora
}