package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.PlataformaEntity

@Dao
interface PlataformaDao : BaseDao<PlataformaEntity> {
    @Query("SELECT * FROM PLATAFORMA")
    fun all(): LiveData<MutableList<PlataformaEntity>>

    @Query("SELECT * FROM PLATAFORMA P WHERE P.PLAID=:plaId")
    fun getById(plaId: Long):LiveData<PlataformaEntity>
}