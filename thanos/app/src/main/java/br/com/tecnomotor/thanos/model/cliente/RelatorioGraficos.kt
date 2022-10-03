package br.com.tecnomotor.thanos.model.cliente

import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioGraficosEntity
import br.com.tecnomotor.thanos.util.ConvertClass
import java.sql.Blob
import java.time.LocalDateTime

class RelatorioGraficos {

    lateinit var regImg: Blob
    var id : Long = 0
    var nome: String = ""
    lateinit var dataHora: LocalDateTime

//    constructor(id: Long, nome: String, regImg: Blob) : this() {
//        this.id = id
//        this.nome = nome
//        this.regImg = regImg
//    }

    override fun toString(): String {
        return "Relatorio_Graficos("+super.toString()+")"
    }

}

fun RelatorioGraficosEntity.toRelatorio(): RelatorioGraficos {
    return ConvertClass.convert(this, RelatorioGraficos::class.java.name) as RelatorioGraficos
}