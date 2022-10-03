package br.com.tecnomotor.thanos.database.messages.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.messages.entity.StateEntity

@Dao
interface StateDao : BaseDao<StateEntity> {
    @Query("SELECT * FROM STATE_TAB")
    fun all(): LiveData<MutableList<StateEntity>>

    @Query("SELECT * FROM STATE_TAB STAB WHERE (STAB._id=:stabId)")
    fun getStateTab(stabId: Long): LiveData<List<StateEntity>>


}