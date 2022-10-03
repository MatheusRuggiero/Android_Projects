package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.VeiculoEntity
import br.com.tecnomotor.thanos.database.menu.entity.VersaoEntity

@Dao
interface VeiculoDao : BaseDao<VeiculoEntity> {
    @Query("SELECT DISTINCT V.* FROM APLICACAO A INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID INNER JOIN VERSAO_APLICACAO VA ON VA.APLID=A.APLID INNER JOIN VEICULO V ON V.VEIID=A.VEIID WHERE ((PA.PLAID=:plaId) AND ((VA.VERID=:verId) OR (VA.VERID=:verHabilitada)) AND (A.APLID>=0) AND (A.MONID=:monId)) ORDER BY V.VEINOME ASC")
    fun getVeiculos(plaId: Long, verId: Long, verHabilitada: Long, monId: Long): LiveData<MutableList<VeiculoEntity>>

    @Query("SELECT V.* FROM VERSAO_APLICACAO VA INNER JOIN APLICACAO A ON A.APLID=VA.APLID INNER JOIN VERSAO V ON V.VERID=VA.VERID INNER JOIN PLATAFORMA_APLICACAO PA ON PA.APLID=A.APLID WHERE A.MONID=:monId AND A.VEIID=:veiId AND PA.PLAID=:plaId ORDER BY V.VERID ASC LIMIT 1")
    fun getVersaoByVeiculo(plaId: Long, monId: Long, veiId: Long): LiveData<VersaoEntity>
}



