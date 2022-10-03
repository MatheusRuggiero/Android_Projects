package br.com.tecnomotor.thanos.model.menu

import br.com.tecnomotor.thanos.database.menu.entity.PlataformaEntity
import br.com.tecnomotor.thanos.util.ConvertClass

data class Plataforma(
    var nome: String = "",
    var versaoHabilitada: Long = 0
): AbstractMenu() {

    constructor(id: Long, nome: String, versaoHabilitada: Long) : this() {
        this.id = id
        this.nome = nome
        this.versaoHabilitada = versaoHabilitada
    }

    override fun clear() {
        this.nome = ""
        this.versaoHabilitada = 0
    }

    override fun toString(): String {
        return "Plataforma(id='$id',nome='$nome', versaoHabilitada=$versaoHabilitada)"
    }
}

fun PlataformaEntity.toPlataforma(): Plataforma{
    return ConvertClass.convert(this, Plataforma::class.java.name) as Plataforma
}