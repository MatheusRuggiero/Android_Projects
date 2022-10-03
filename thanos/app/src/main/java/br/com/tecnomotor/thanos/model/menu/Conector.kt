package br.com.tecnomotor.thanos.model.menu

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import br.com.tecnomotor.rasther.utils.AppPaths
import br.com.tecnomotor.thanos.database.menu.entity.ConectorAplicacaoEntity
import br.com.tecnomotor.thanos.database.menu.entity.ConectorEntity
import br.com.tecnomotor.thanos.util.ConvertClass

class Conector(): AbstractMenu() {
    var nome: String = ""
    var imgConVeiculo: Long = 0
    var imgConJogoPinos: Long = 0
    var pinoX: String = ""
    var pinoY: String = ""
    var posConector: String = ""
    var versaoBd: Long = 0
    var estaNaVersao: Boolean = false
    var montHabilitada: Boolean = false

    constructor(id: Long, nome: String,
                imgConVeiculo: Long,
                pinoX: String, pinoY: String, posConector: String) : this() {
        this.id = id
        this.nome = nome
        this.imgConVeiculo = imgConVeiculo
        this.pinoX = pinoX
        this.pinoY = pinoY
        this.posConector = posConector
    }

    override fun clear() {
        super.clear()
        this.nome = ""
        this.imgConVeiculo = 0
        this.imgConJogoPinos = 0
        this.pinoX = ""
        this.pinoY = ""
        this.posConector = ""
        this.versaoBd = 0
        this.estaNaVersao = false
        this.montHabilitada = false
    }

    fun versaoOkMontadoraOk() = (montHabilitada && estaNaVersao)

    fun getPosicoesConector():Array<String> = posConector.split("/").map { it -> it.trim() }.toTypedArray()

    /**
     * Return Pair(gridImgPath, prefix)
     */
    fun getGridImagePath(): Pair<String, String> {
        // Divide a string posicoes (K4/I6/A3) para strings separadas
        //Verificando qual é a imagem do grid para imprimir as posições do conector
        val flagFora: Boolean = posConector.contains(Regex("^(?i:[ABCDE])"))
        val flagDentro: Boolean = posConector.contains(Regex("^(?i:[FGHIJKLM])"))

        var gridImgPath: String = ""
        var prefix = ""
        if (flagFora && flagDentro) {
            gridImgPath = "Positions/duplo.png"
            prefix = "DUPLO_"
        } else if (flagFora && !flagDentro)
            gridImgPath = "Positions/fora.png"
        else
            gridImgPath = "Positions/dentro.png"
        return Pair(gridImgPath, prefix)
    }

    fun getBitmapConector(resources: Resources): Bitmap {
        val connectorPath = AppPaths.DIR_IMAGES + "/Con" + this.id + ".jpg"
        return BitmapFactory.decodeStream(resources.assets.open(connectorPath))
    }

    fun getBitmapConectorNoVeiculo(resources: Resources): Bitmap {
        val connectorPath = AppPaths.DIR_IMAGES + "/Img" + this.imgConVeiculo + ".jpg"
        return BitmapFactory.decodeStream(resources.assets.open(connectorPath))
    }

    override fun toString(): String {
        return "Conector(${super.toString()},imgConVeiculo='$imgConVeiculo',imgConJogoPinos='$imgConJogoPinos',pinoX='$pinoX',pinoY='$pinoY',posConector='$posConector',versaoBd='$versaoBd',estaNaVersao='$estaNaVersao',montHabilitada='$montHabilitada')"
    }
}

fun ConectorEntity.toConector(): Conector {
    return ConvertClass.convert(this, Conector::class.java.name) as Conector
}

fun ConectorAplicacaoEntity.toConector(): Conector {
    return ConvertClass.convert(this, Conector::class.java.name) as Conector
}