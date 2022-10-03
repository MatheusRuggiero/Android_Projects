package br.com.tecnomotor.thanos.model

import br.com.tecnomotor.thanos.model.menu.*

/**
 * Armazena a seleção de menu durante a navegação das telas do menu
 */
class Selecao(){
    companion object { // singleton
        @Volatile
        private var instance: Selecao? = null

        //Singleton instance
        fun getInstance(): Selecao {
            return instance ?: synchronized(this) {
                Selecao().also { instance = it }
            }
        }
    }

    var plataforma = Plataforma()
        private set
    var versao = Versao()
        private set
    var montadora = Montadora()
        private set
    var veiculo = Veiculo()
        private set
    var motorizacao = Motorizacao()
        private set
    var tipoDeSistema = TipoDeSistema()
        private set
    var sistema = Sistema()
        private set
    var conector = Conector()
        private set
    var aplicacao = Aplicacao()
        private set

    fun clearAll() {
        plataforma.clear()
        versao.clear()
        this.clear()
    }

    fun clear() {
        montadora.clear()
        veiculo.clear()
        motorizacao.clear()
        tipoDeSistema.clear()
        sistema.clear()
        conector.clear()
        aplicacao.clear()
    }

    fun setPlataforma(plataforma: Plataforma): Selecao {
        this.plataforma = plataforma
        return this
    }

    fun setVersao(versao: Versao): Selecao {
        this.versao = versao
        return this
    }

    fun setMontadora(montadora: Montadora): Selecao {
        this.montadora = montadora
        return this
    }

    fun setVeiculo(veiculo: Veiculo): Selecao {
        this.veiculo = veiculo
        return this
    }

    fun setMotorizacao(motorizacao: Motorizacao): Selecao {
        this.motorizacao = motorizacao
        return this
    }

    fun setTipoSistema(tipoDeSistema: TipoDeSistema): Selecao {
        this.tipoDeSistema = tipoDeSistema
        return this
    }

    fun setSistema(sistema: Sistema): Selecao {
        this.sistema = sistema
        return this
    }

    fun setConector(conector: Conector): Selecao {
        this.conector = conector
        return this
    }
    fun setAplicacao(aplicacao: Aplicacao): Selecao {
        this.aplicacao = aplicacao
        return this
    }

    fun plataformaEVersaoValida() = ((this.plataforma.id > 0) && (versao.id > 0))

    override fun toString(): String {
        return "Selecao(plataforma=$plataforma, versao=$versao, montadora=$montadora, veiculo=$veiculo, motorizacao=$motorizacao, tipoDeSistema=$tipoDeSistema, sistema=$sistema, conector=$conector, aplicacao=$aplicacao)"
    }
}