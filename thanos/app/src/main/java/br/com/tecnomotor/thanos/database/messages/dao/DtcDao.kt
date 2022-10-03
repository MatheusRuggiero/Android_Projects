package br.com.tecnomotor.thanos.database.messages.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.messages.entity.DtcEntity

@Dao
interface DtcDao : BaseDao<DtcEntity> {
    @Query("SELECT * FROM DTC_TAB")
    fun all(): LiveData<MutableList<DtcEntity>>

    @Query("SELECT * FROM DTC_TAB DTC WHERE _id IN (:dtcId)")
    fun getCodDefeito(dtcId: List<Int>?): LiveData<List<DtcEntity>>
}

