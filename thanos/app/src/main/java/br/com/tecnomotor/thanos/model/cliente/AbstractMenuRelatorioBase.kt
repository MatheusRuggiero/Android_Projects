package br.com.tecnomotor.thanos.model.cliente

import java.time.LocalDateTime

abstract class AbstractMenuRelatorioBase: AbstractMenuRelatorio {
    var nome: String = ""
    var nomeSpa: String = ""
    var nomeEng: String = ""
    var valor: String = ""

    lateinit var dataHora: LocalDateTime

    constructor()

    constructor(id: Long, nome: String) : super(id) {
        this.nome = nome
    }

    override fun clear() {
        super.clear()
        this.id = 0
        this.nome = ""
        this.nomeSpa = ""
        this.nomeEng = ""
    }

    override fun toString(): String {
        return "AbstractMenuRelatorioBase(nome='$nome', nomeSpa='$nomeSpa', nomeEng='$nomeEng',  valor='$valor', dataHora=$dataHora)"
    }


}