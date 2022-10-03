package br.com.tecnomotor.thanos.database.messages.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.messages.entity.MenuEntity

@Dao
interface MenuDao : BaseDao<MenuEntity> {
    /**
     * SELECT * FROM MENU_TAB where MENU_IDX IN (:menuIds)
     * ORDER BY
     *  CASE MENU_IDX
     *      WHEN 8  THEN 1  //Código de defeito
     *      WHEN 9  THEN 3  //Leituras
     *      WHEN 13 THEN 4  //Atuadores
     *      WHEN 14 THEN 5  //Ajustes
     *      WHEN 15 THEN 6  //Programações
     *      WHEN 6  THEN 7  //Apaga memória
     *      WHEN 7  THEN 8  //Identificação da ECU
     *  END
     */
    @Query("SELECT * FROM MENU_TAB MT WHERE _id IN (:menuIds) order by CASE MENU_IDX WHEN 8 THEN 1 WHEN 12 THEN 2 WHEN 9 THEN 3 WHEN 13 THEN 4 WHEN 14 THEN 5 WHEN 15 THEN 6 WHEN 6 THEN 7 WHEN 7 THEN 8 END")
    fun getMenu(menuIds: List<Int>?): LiveData<List<MenuEntity>>

    @Query("SELECT * FROM MENU_TAB MT WHERE _id IN (:subMenuIDs)")
    fun getSubMenu(subMenuIDs: Int): MenuEntity
}

