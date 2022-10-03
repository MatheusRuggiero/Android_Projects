package br.com.tecnomotor.thanos.database.messages.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.messages.entity.ValueEntity

@Dao
interface ValueDao : BaseDao<ValueEntity> {
    @Query("SELECT * FROM VALUE_TAB")
    fun all(): LiveData<MutableList<ValueEntity>>

    @Query("SELECT * FROM VALUE_TAB LT WHERE _id IN (:ltId)")
    fun getLeituras(ltId: List<Int>?): LiveData<List<ValueEntity>>

}