package br.com.tecnomotor.thanos.database.cliente.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioCodigoDefeitosEntity

@Dao
interface CodDefeitosDao : BaseDao<RelatorioCodigoDefeitosEntity> {
    @Query("SELECT * FROM relatorio_codigo_defeitos")
    fun all(): LiveData<MutableList<RelatorioCodigoDefeitosEntity>>

}