package br.com.tecnomotor.thanos.repository.diagnostico

import androidx.lifecycle.LiveData
import br.com.tecnomotor.thanos.database.messages.dao.UnitDao
import br.com.tecnomotor.thanos.database.messages.dao.ValueDao
import br.com.tecnomotor.thanos.database.messages.entity.UnitEntity
import br.com.tecnomotor.thanos.database.messages.entity.ValueEntity

class LeiturasRepositry(
    private val leituras: ValueDao,
   private val unidade: UnitDao
) {
    fun getLeituras(leiturasID: List<Int>?): LiveData<List<ValueEntity>> {
        return  leituras.getLeituras(leiturasID)
    }

    fun getUnidade(unidadeId: Int): LiveData<UnitEntity>{
        return unidade.getUnidade(unidadeId)
    }
}