package br.com.tecnomotor.thanos.database.messages.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.messages.entity.CondDtcEntity

@Dao
interface CondDtcDao : BaseDao<CondDtcEntity> {
    @Query("SELECT * FROM CONDDTC_TAB")
    fun all(): LiveData<MutableList<CondDtcEntity>>
}
