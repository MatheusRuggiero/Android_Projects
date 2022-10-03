package br.com.tecnomotor.thanos.database.cliente.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.cliente.entity.HistoricoEntity

@Dao
interface HistoricosDao : BaseDao<HistoricoEntity> {
    @Query("SELECT * FROM historico")
    fun getHistorico(): LiveData<MutableList<HistoricoEntity>>

    @Delete
    fun deletaHistorico(historicoEntity: HistoricoEntity)

    @Query("SELECT * FROM historico ORDER BY id DESC LIMIT 1")
    fun getUltimoHistoricoInserido(): LiveData<HistoricoEntity>

    @Insert
    fun insereHistorico(historicoEntity: HistoricoEntity)

    @Query("SELECT COUNT(id) FROM historico")
    fun qtdHistoricos() : LiveData<Int>
}