package br.com.tecnomotor.thanos.database.messages.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.messages.entity.UnitEntity

@Dao
interface UnitDao : BaseDao<UnitEntity> {
    @Query("SELECT * FROM UNIT_TAB")
    fun all(): LiveData<MutableList<UnitEntity>>

    @Query("SELECT * FROM UNIT_TAB UNITAB WHERE (UNITAB._id=:unidadeID)")
    fun getUnidade(unidadeID: Int): LiveData<UnitEntity>
}