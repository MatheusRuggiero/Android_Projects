package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.MontadoraEntity

@Dao
interface MontadoraDao : BaseDao<MontadoraEntity> {
    @Query("SELECT M.* FROM MONTADORA M ORDER BY M.MONNOME ASC")
    fun all(): LiveData<MutableList<MontadoraEntity>>
    /**
     * Seleciona a montadora da versão até a última habilitada
     */
    @Query("SELECT DISTINCT M.* FROM APLICACAO A INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID INNER JOIN VERSAO_APLICACAO VA ON VA.APLID=A.APLID INNER JOIN MONTADORA M ON M.MONID=A.MONID WHERE ((PA.PLAID=:plaId) AND ((VA.VERID=:verId) OR (VA.VERID=:verHabilitada)) AND (A.APLID>=0)) ORDER BY M.MONNOME ASC")
    fun getByPlataformaVersao(plaId: Long, verId: Long, verHabilitada: Long): LiveData<MutableList<MontadoraEntity>>

    @Query("SELECT M.* FROM MONTADORA M WHERE M.MONID=:monId")
    fun getById(monId: Long):LiveData<MontadoraEntity>

    @Query("SELECT M.* FROM MONTADORA M WHERE M.MONID IN (:monIds)")
    fun getById(monIds: ArrayList<Int>):LiveData<MutableList<MontadoraEntity>>
}