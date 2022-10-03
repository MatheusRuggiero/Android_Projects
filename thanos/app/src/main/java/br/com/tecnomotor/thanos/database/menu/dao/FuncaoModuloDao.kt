package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.FuncaoModuloEntity

@Dao
interface FuncaoModuloDao : BaseDao<FuncaoModuloEntity> {
    @Query("SELECT * FROM FUNCAOMODULO")
    fun all(): LiveData<MutableList<FuncaoModuloEntity>>
}
