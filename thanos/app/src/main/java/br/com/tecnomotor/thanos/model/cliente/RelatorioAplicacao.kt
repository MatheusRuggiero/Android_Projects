package br.com.tecnomotor.thanos.model.cliente

import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioAplicacaoEntity
import br.com.tecnomotor.thanos.util.ConvertClass
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class RelatorioAplicacao {
    var id: Long = 0
    var placa: String = ""
    var aplicacaoId: Long = 0
    var montadoraId: Long = 0
    var montadoraNome: String = ""
    var veiculoId: Long = 0
    var veiculoNome: String = ""
    var motorizacaoId: Long = 0
    var motorizacaoNome: String = ""
    var tipoSistemaId: Long = 0
    var tipoSistemaNome: String = ""
    var sistemaId: Long = 0
    var sistemaNome: String = ""
    var anoInicial: Long = 0
    var anoFinal: Long = 0
    var modulo: Long = 0
    lateinit var dataHora: LocalDateTime

//    constructor(id: Long,placa: String,aplicacaoId: Long,montadoraId: Long,montadoraNome: String,veiculoId: Long,veiculoNome: String,motorizacaoId: Long, motorizacaoNome: String,tipoSistemaId: Long,tipoSistemaNome: String,sistemaId: Long,sistemaNome: String, anoInicial: Long,anoFinal: Long,modulo: Long, dataHora: LocalDateTime) : this() {
//        this.id = id
//        this.placa = placa
//        this.aplicacaoId = aplicacaoId
//        this.montadoraId= montadoraId
//        this.montadoraNome = montadoraNome
//        this.veiculoId = veiculoId
//        this.veiculoNome = veiculoNome
//        this.motorizacaoId = motorizacaoId
//        this.motorizacaoNome = motorizacaoNome
//        this.tipoSistemaId = tipoSistemaId
//        this.tipoSistemaNome = tipoSistemaNome
//        this.sistemaId = sistemaId
//        this.sistemaNome = sistemaNome
//        this.anoInicial = anoInicial
//        this.anoFinal = anoFinal
//        this.modulo = modulo
//        this.dataHora = dataHora
//    }

    override fun toString(): String {
        return "Relatorio_Aplicacao(" + super.toString() + ")"
    }

    fun formattedDate(): String? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        return dateFormat.format(dataHora.toLocalDate())
    }

    fun RelatorioAplicacaoEntity.toRelatorioAplicacao(): RelatorioAplicacao {
        return ConvertClass.convert(this, RelatorioAplicacao::class.java.name) as RelatorioAplicacao
    }
}