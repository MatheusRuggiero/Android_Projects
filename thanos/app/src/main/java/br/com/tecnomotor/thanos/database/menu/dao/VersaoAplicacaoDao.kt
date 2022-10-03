package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.VersaoAplicacaoEntity

@Dao
interface VersaoAplicacaoDao : BaseDao<VersaoAplicacaoEntity> {

    @Query("SELECT * FROM VERSAO_APLICACAO")
    fun all(): LiveData<MutableList<VersaoAplicacaoEntity>>

}