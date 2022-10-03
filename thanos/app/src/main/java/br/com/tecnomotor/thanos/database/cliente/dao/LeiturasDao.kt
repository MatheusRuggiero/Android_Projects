package br.com.tecnomotor.thanos.database.cliente.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioLeiturasEntity

@Dao
interface LeiturasDao : BaseDao<RelatorioLeiturasEntity> {
    @Query("SELECT * FROM relatorio_leituras")
    fun all(): LiveData<MutableList<RelatorioLeiturasEntity>>

    @Insert
    fun addLeitura(leitura: RelatorioLeiturasEntity)
}