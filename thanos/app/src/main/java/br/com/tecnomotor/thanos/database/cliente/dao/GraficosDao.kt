package br.com.tecnomotor.thanos.database.cliente.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioGraficosEntity

@Dao
interface GraficosDao : BaseDao<RelatorioGraficosEntity> {
    @Query("SELECT * FROM relatorio_graficos")
    fun all(): LiveData<MutableList<RelatorioGraficosEntity>>
}