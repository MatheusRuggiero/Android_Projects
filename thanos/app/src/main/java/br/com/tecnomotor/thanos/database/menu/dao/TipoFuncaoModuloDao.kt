package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.TipoFuncaoModuloEntity

@Dao
interface TipoFuncaoModuloDao : BaseDao<TipoFuncaoModuloEntity> {
    @Query("SELECT * FROM TIPOFUNCAOMODULO")
    fun all(): LiveData<MutableList<TipoFuncaoModuloEntity>>
}