package br.com.tecnomotor.thanos.model.menu

import br.com.tecnomotor.thanos.database.menu.entity.VeiculoEntity
import br.com.tecnomotor.thanos.util.ConvertClass

class Veiculo(): AbstractMenuBase() {

    constructor(id: Long, nome: String) : this() {
        this.id = id
        this.nome = nome
    }

    override fun toString(): String {
        return "Veiculo("+super.toString()+")"
    }
}

fun VeiculoEntity.toVeiculo(): Veiculo{
    return ConvertClass.convert(this, Veiculo::class.java.name) as Veiculo
}