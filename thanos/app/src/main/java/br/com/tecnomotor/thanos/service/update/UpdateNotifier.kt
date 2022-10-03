package br.com.tecnomotor.thanos.service.update

/**
 * Classe criada para trabalhar com as notificações das atualizações
 * de software, firmware, entre outras, ...
 * @author diogo
 * @author rogerio
 */
class UpdateNotifier(tipo: Int) {
    // dados da atualização
    var tipo = 0
    var progresso // em %
            = 0
    var maxProgresso = INIT_MAX_PROGRESS // arquivo corrente
        get() = if (field == INIT_MAX_PROGRESS) DEFAULT_MAX_PROGRESS else field
    var status = 0
    var codigoErro = NENHUM_ERRO_ENCONTRADO
    val tipoStr: String
        get() = when (tipo) {
            1 -> "SOFTWARE"
            2 -> "FIRMWARE"
            3 -> "GRAFICA"
            4 -> "HABILITACAO"
            else -> "Tipo não identificado. Verifique UpdateNotifier."
        }

    @JvmName("getCodigoErro1")
    fun getCodigoErro(): Int {
        return codigoErro
    }

    @JvmName("setCodigoErro1")
    fun setCodigoErro(codigoErro: Int) {
        this.codigoErro = codigoErro
        status = STATUS_TERMINADO
    }

    val isOk: Boolean
        get() = getCodigoErro() == NENHUM_ERRO_ENCONTRADO

    fun getStrTipoNotificacao(tipo: Int): String {
        var result = ""
        result = when (tipo) {
            TIPO_SOFTWARE -> "TIPO_SOFTWARE"
            TIPO_FIRMWARE -> "TIPO_FIRMWARE"
            TIPO_GRAFICA -> "TIPO_GRAFICA"
            TIPO_HABILITACAO -> "TIPO_HABILITACAO"
            else -> "Tipo '$tipo' não definido! Implemente em: getStrTipoNotificacao."
        }
        return result
    }

    val strTipoNotificacao: String
        get() = getStrTipoNotificacao(tipo)

    fun getStrStatus(status: Int): String {
        var result = ""
        result = when (status) {
            STATUS_INICIANDO -> "STATUS_INICIANDO"
            STATUS_ATUALIZANDO -> "STATUS_ATUALIZANDO"
            STATUS_VERIFICANDO -> "STATUS_VERIFICANDO"
            STATUS_BAIXANDO -> "STATUS_BAIXANDO"
            STATUS_FINALIZANDO -> "STATUS_FINALIZANDO"
            STATUS_TERMINADO -> "STATUS_TERMINADO"
            else -> "Status '$status' não definido! Implemente em: getStrStatus."
        }
        return result
    }

    val strStatus: String
        get() = getStrStatus(status)

    fun getStrCodigoErro(codigo: Int): String {
        var result = ""
        result = when (codigo) {
            ERRO_SERVIDOR_ATUALIZACAO -> "ERRO_SERVIDOR_ATUALIZACAO"
            NENHUM_ERRO_ENCONTRADO -> "NENHUM_ERRO_ENCONTRADO"
            ERRO_ARQ_NAO_ENCONTRADO -> "NENHUM_ERRO_ENCONTRADO"
            ERRO_DE_COMUNICACAO -> "ERRO_DE_COMUNICACAO"
            ERRO_SEM_REDE -> "ERRO_SEM_REDE"
            ERRO_NO_DOWNLOAD -> "ERRO_NO_DOWNLOAD"
            ERRO_LICENCA_NAO_ACEITA -> "ERRO_LICENCA_NAO_ACEITA"
            ERRO_REGISTRO_CANCELADO -> "ERRO_REGISTRO_CANCELADO"
            else -> "Código '$codigo' não definido! Implemente em: getStrCodigoErro."
        }
        return result
    }

    val strCodigoErro: String
        get() = getStrCodigoErro(codigoErro)

    companion object {
        // tipos de notificação
        const val TIPO_SOFTWARE = 1
        const val TIPO_FIRMWARE = 2
        const val TIPO_GRAFICA = 3
        const val TIPO_HABILITACAO = 4

        // status/estados da atualização
        const val STATUS_INICIANDO = 1
        const val STATUS_ATUALIZANDO = 2
        const val STATUS_VERIFICANDO = 3
        const val STATUS_BAIXANDO = 4
        const val STATUS_FINALIZANDO = 5
        const val STATUS_TERMINADO = 6

        // códigos de erro
        const val ERRO_SERVIDOR_ATUALIZACAO = -5
        const val NENHUM_ERRO_ENCONTRADO = 0
        const val ERRO_ARQ_NAO_ENCONTRADO = 3
        const val ERRO_DE_COMUNICACAO = 4
        const val ERRO_SEM_REDE = 5
        const val ERRO_NO_DOWNLOAD = 6
        const val ERRO_LICENCA_NAO_ACEITA = 7
        const val ERRO_REGISTRO_CANCELADO = 8
        private const val INIT_MAX_PROGRESS = -1
        private const val DEFAULT_MAX_PROGRESS = 100
    }

    init {
        this.tipo = tipo
        progresso = 0
        maxProgresso = INIT_MAX_PROGRESS
        status = -1
    }
}