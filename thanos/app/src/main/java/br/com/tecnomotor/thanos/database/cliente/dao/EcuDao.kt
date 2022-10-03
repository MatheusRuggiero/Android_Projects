package br.com.tecnomotor.thanos.database.cliente.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioEcuEntity

@Dao
interface EcuDao : BaseDao<RelatorioEcuEntity> {
    @Query("SELECT * FROM relatorio_ecu")
    fun all(): LiveData<MutableList<RelatorioEcuEntity>>
}