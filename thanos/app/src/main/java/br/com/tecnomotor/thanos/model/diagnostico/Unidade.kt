package br.com.tecnomotor.thanos.model.diagnostico

class Unidade(
    val id: Int = 0,
    var nome: String = "") {

    override fun toString(): String {
        return "Unidade(id=$id, nome='$nome')"
    }
}