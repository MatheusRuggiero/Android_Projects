package br.com.tecnomotor.thanos.model.cliente

import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioEcuEntity
import br.com.tecnomotor.thanos.util.ConvertClass
import java.time.LocalDateTime

class RelatorioEcu {
    var id : Long = 0
    var descricao: String = ""
    var valor: String = ""
    lateinit var dataHora: LocalDateTime





//    constructor(id: Long, nome: String, valor: String, tipo: Long,valorMinimo: Double, valorMaximo: Double,unidade: String,valorTexto: String, dataHora: LocalDateTime) : this() {
//        this.id = id
//        this.nome = nome
//        this.valor = valor
//        this.tipo = tipo
//        this.valorMinimo = valorMinimo
//        this.valorMaximo = valorMaximo
//        this.valorTexto = valorTexto
//        this.dataHora = dataHora
//    }

    override fun toString(): String {
        return "Relatorio_Ecu("+super.toString()+")"
    }

}

fun RelatorioEcuEntity.toRelatorioEcu(): RelatorioEcu {
    return ConvertClass.convert(this, RelatorioEcu::class.java.name) as RelatorioEcu
}