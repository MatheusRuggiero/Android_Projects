package br.com.tecnomotor.thanos.model.cliente

import br.com.tecnomotor.thanos.database.cliente.entity.FavoritoEntity
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.menu.*
import br.com.tecnomotor.thanos.util.ConvertClass
import java.util.*

class Favorito() {
    var id: Long = 0.toLong()
    var montadoraId: Long = 0.toLong()
    var montadoraNome: String = ""
    var veiculoId: Long = 0.toLong()
    var veiculoNome: String = ""
    var motorizacaoId: Long = 0.toLong()
    var motorizacaoNome: String = ""
    var tipoSistemaId: Long = 0.toLong()
    var tipoSistemaNome: String = ""
    var sistemaId: Long = 0.toLong()
    var sistemaNome: String = ""
    var conectorId: Long = 0.toLong()
    var conectorNome: String = ""
    var posicaoConector: String = ""
    var pinoX: String = ""
    var pinoY: String = ""
    var aplicacaoId: Long = 0.toLong()
    var modulo: Long = 0.toLong()
    var anoInicial: Long = 0.toLong()
    var anoFinal: Long = 0.toLong()
    var imgConVeiculo: Long = 0.toLong()
    var data: Date = Calendar.getInstance().time

    constructor(
        id: Long,
        montadoraId: Long,
        montadoraNome: String,
        veiculoId: Long,
        veiculoNome: String,
        motorizacaoId: Long,
        motorizacaoNome: String,
        tipoSistemaId: Long,
        tipoSistemaNome: String,
        sistemaId: Long,
        sistemaNome: String,
        conectorId: Long,
        conectorNome: String,
        posicaoConector: String,
        pinoX: String,
        pinoY: String,
        aplicacaoId: Long,
        modulo: Long,
        anoInicial: Long,
        anoFinal: Long,
        imgConVeiculo: Long
    ): this() {
        this.id = id
        this.montadoraId = montadoraId
        this.montadoraNome = montadoraNome
        this.veiculoId = veiculoId
        this.veiculoNome = veiculoNome
        this.motorizacaoId = motorizacaoId
        this.motorizacaoNome = motorizacaoNome
        this.tipoSistemaId = tipoSistemaId
        this.tipoSistemaNome = tipoSistemaNome
        this.sistemaId = sistemaId
        this.sistemaNome = sistemaNome
        this.conectorId = conectorId
        this.conectorNome = conectorNome
        this.posicaoConector = posicaoConector
        this.pinoX = pinoX
        this.pinoY = pinoY
        this.aplicacaoId = aplicacaoId
        this.modulo = modulo
        this.anoInicial = anoInicial
        this.anoFinal = anoFinal
        this.imgConVeiculo = imgConVeiculo
    }

    constructor(selecao: Selecao): this() {
        this.id = 0
        this.montadoraId = selecao.montadora.id
        this.montadoraNome = selecao.montadora.nome
        this.veiculoId = selecao.veiculo.id
        this.veiculoNome = selecao.veiculo.nome
        this.motorizacaoId = selecao.motorizacao.id
        this.motorizacaoNome = selecao.motorizacao.nome
        this.tipoSistemaId = selecao.tipoDeSistema.id
        this.tipoSistemaNome = selecao.tipoDeSistema.nome
        this.sistemaId = selecao.sistema.id
        this.sistemaNome = selecao.sistema.nome
        this.conectorId = selecao.conector.id
        this.conectorNome = selecao.conector.nome
        this.posicaoConector = selecao.conector.posConector
        this.pinoX = selecao.conector.pinoX
        this.pinoY = selecao.conector.pinoY
        this.aplicacaoId = selecao.aplicacao.id
        this.modulo = selecao.aplicacao.modulo
        this.anoInicial = selecao.sistema.anoInicial
        this.anoFinal = selecao.sistema.anoFinal
        this.imgConVeiculo = selecao.conector.imgConVeiculo
    }

    fun getMontadora() = Montadora(this.montadoraId, this.montadoraNome)
    fun getVeiculo() = Veiculo(this.veiculoId, this.veiculoNome)
    fun getMotorizacao() = Motorizacao(this.motorizacaoId, this.motorizacaoNome)
    fun getTipoDeSistema() = TipoDeSistema(this.tipoSistemaId, this.tipoSistemaNome)
    fun getSistema() = Sistema(this.sistemaId,this.sistemaNome,this.anoInicial, this.anoFinal)
    fun getConector() = Conector(this.conectorId, this.conectorNome, this.imgConVeiculo,
        this.pinoX, this.pinoY, this.posicaoConector)
    fun getAplicacao() = Aplicacao(this.aplicacaoId, this.modulo)

    fun toFavoritoEntity(): FavoritoEntity {
        return ConvertClass.convert(this, FavoritoEntity::class.java.name) as FavoritoEntity
    }

    override fun toString(): String {
        return "Favorito(id=$id, montadoraId=$montadoraId, montadoraNome='$montadoraNome', veiculoId=$veiculoId, veiculoNome='$veiculoNome', motorizacaoId=$motorizacaoId, motorizacaoNome='$motorizacaoNome', tipoSistemaId=$tipoSistemaId, tipoSistemaNome='$tipoSistemaNome', sistemaId=$sistemaId, sistemaNome='$sistemaNome', conectorId=$conectorId, conectorNome='$conectorNome', posicaoConector='$posicaoConector', pinoX='$pinoX', pinoY='$pinoY', aplicacaoId=$aplicacaoId, modulo=$modulo, anoInicial=$anoInicial, anoFinal=$anoFinal, imgConVeiculo=$imgConVeiculo, data=$data)"
    }
}

fun FavoritoEntity.toFavorito(): Favorito {
    return ConvertClass.convert(this, Favorito::class.java.name) as Favorito
}

fun Selecao.loadFavorito(favorito: Favorito) {
    this.setMontadora(favorito.getMontadora())
        .setVeiculo(favorito.getVeiculo())
        .setMotorizacao(favorito.getMotorizacao())
        .setTipoSistema(favorito.getTipoDeSistema())
        .setSistema(favorito.getSistema())
        .setConector(favorito.getConector())
        .setAplicacao(favorito.getAplicacao())
    this.conector.montHabilitada = true
    this.conector.estaNaVersao = true
}