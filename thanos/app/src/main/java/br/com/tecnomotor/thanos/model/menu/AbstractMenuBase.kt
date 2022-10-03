package br.com.tecnomotor.thanos.model.menu

abstract class AbstractMenuBase: AbstractMenu {
    var nome: String = ""
    var nomeSpa: String = ""
    var nomeEng: String = ""
    var montHabilitada: Boolean = false
    var versaoBd: Long = 0
    var estaNaVersao: Boolean = false

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
        this.montHabilitada = false
        this.versaoBd = 0
        this.estaNaVersao = false
    }

    fun versaoOkMontadoraOk() = ((montHabilitada && estaNaVersao) || (this.id == 0.toLong()))

    override fun toString(): String {
        return "id='$id',nome='$nome', nomeSpa='$nomeSpa', nomeEng='$nomeEng', montHabilitada=$montHabilitada, versaoBd=$versaoBd, estaNaVersao=$estaNaVersao"
    }
}