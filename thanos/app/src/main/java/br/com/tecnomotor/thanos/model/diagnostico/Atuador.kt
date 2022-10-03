package br.com.tecnomotor.thanos.model.diagnostico

class Atuador (

    var MESGID: Int, // id de mensagem da tabela menu
    var MSG: String, // informativo
    var man: String // informativo
){
    override fun toString(): String {
        return "Atuador(MESGID=$MESGID, MSG='$MSG', man='$man')"
    }
}