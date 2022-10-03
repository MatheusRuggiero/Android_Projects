package br.com.tecnomotor.thanos.model.cliente

import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioCodigoDefeitosEntity
import br.com.tecnomotor.thanos.util.ConvertClass
import java.time.LocalDateTime

class RelatorioCodigoDefeitos {
    var id : Long = 0
    var codigo: String = ""
    var descricao: String = ""
    var sintomas: String = ""
    var status: String = ""
    lateinit var dataHora: LocalDateTime

//    constructor(id: Long,codigo: String,descricao: String,sintomas: String,status: String, dataHora: LocalDateTime) : this() {
//        this.id = id
//        this.codigo = codigo
//        this.descricao = descricao
//        this.sintomas = sintomas
//        this.status = status
//        this.dataHora = dataHora
//    }

    override fun toString(): String {
        return "Relatorio_Codigo_Defeitos("+super.toString()+")"
    }

}

fun RelatorioCodigoDefeitosEntity.toRelatorioCodigoDefeitos(): RelatorioCodigoDefeitos {
    return ConvertClass.convert(this, RelatorioCodigoDefeitos::class.java.name) as RelatorioCodigoDefeitos
}