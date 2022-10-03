package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.AplicacaoConectorEntity

@Dao
interface AplicacaoConectorDao: BaseDao<AplicacaoConectorEntity> {
}