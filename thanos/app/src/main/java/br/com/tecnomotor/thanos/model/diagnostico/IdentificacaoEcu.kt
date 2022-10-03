package br.com.tecnomotor.thanos.model.diagnostico

class IdentificacaoEcu (
    val id: Int,
    val nome: String,
    val valor: String
        ){
    override fun toString(): String {
        return "IdentificacaoEcu(id=$id, codigo='$nome', sintoma='$valor')"
    }
}
