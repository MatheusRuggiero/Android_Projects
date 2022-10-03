package br.com.tecnomotor.thanos.database.cliente.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioAplicacaoEntity

@Dao
interface RelatorioAplDao : BaseDao<RelatorioAplicacaoEntity> {
    @Query("SELECT * FROM relatorio_aplicacao")
    fun all(): LiveData<List<RelatorioAplicacaoEntity>>
}