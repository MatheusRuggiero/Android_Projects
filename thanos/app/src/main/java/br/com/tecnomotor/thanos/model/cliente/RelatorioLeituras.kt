package br.com.tecnomotor.thanos.model.cliente

import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioLeiturasEntity
import br.com.tecnomotor.thanos.util.ConvertClass
import java.time.LocalDateTime

class RelatorioLeituras {

    var id : Long = 0
    var tipo: Long = 0
    var nome: String = ""
    var valorMinimo: Double = 0.0
    var valorMaximo: Double = 0.0
    var valor: Double = 0.0
    var unidade: String = ""
    var valorTexto: String = ""
    lateinit var dataHora: LocalDateTime



//    constructor(id: Long, nome: String, valor: String, descricao: String, dataHora: LocalDateTime) : this() {
//        this.id = id
//        this.nome = nome
//        this.valor = valor
//        this.descricao = descricao
//        this.dataHora = dataHora
//    }

    override fun toString(): String {
        return "Relatorio_Leituras("+super.toString()+")"
    }

}

fun RelatorioLeiturasEntity.toRelatorioLeituras(): RelatorioLeituras {
    return ConvertClass.convert(this, RelatorioLeituras::class.java.name) as RelatorioLeituras
}