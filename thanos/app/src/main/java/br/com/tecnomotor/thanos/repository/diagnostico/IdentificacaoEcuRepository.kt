package br.com.tecnomotor.thanos.repository.diagnostico

import br.com.tecnomotor.thanos.database.cliente.dao.EcuDao
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioEcuEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IdentificacaoEcuRepository(
    private val ecuDAO: EcuDao
) {

    @DelicateCoroutinesApi
    fun insereEcuRelatorio(ecu: RelatorioEcuEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            ecuDAO.add(ecu)
        }
    }
}