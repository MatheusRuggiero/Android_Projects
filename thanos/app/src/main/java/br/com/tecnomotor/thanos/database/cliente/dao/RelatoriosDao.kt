package br.com.tecnomotor.thanos.database.cliente.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioEntity

@Dao
interface RelatoriosDao : BaseDao<RelatorioEntity> {
    @Query("SELECT * FROM relatorio")
    fun all(): LiveData<MutableList<RelatorioEntity>>
}