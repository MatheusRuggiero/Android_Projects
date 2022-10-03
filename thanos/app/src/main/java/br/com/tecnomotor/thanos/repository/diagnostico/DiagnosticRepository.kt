package br.com.tecnomotor.thanos.repository.diagnostico

import androidx.lifecycle.LiveData
import br.com.tecnomotor.thanos.database.cliente.ClienteDatabase
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioCodigoDefeitosEntity
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioLeiturasEntity
import br.com.tecnomotor.thanos.database.messages.MessagesDatabase
import br.com.tecnomotor.thanos.database.messages.entity.DtcEntity
import br.com.tecnomotor.thanos.database.messages.entity.MenuEntity
import br.com.tecnomotor.thanos.database.messages.entity.ValueEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiagnosticRepository() {
    private val daoMenu = MessagesDatabase.getInstance().menuDao()
    private val daoDtc = MessagesDatabase.getInstance().dtcDao()
    private val daoValue = MessagesDatabase.getInstance().valueDao()

    private val daoLeituras = ClienteDatabase.getInstance().leiturasDao()
    private val daoCodDefeitos = ClienteDatabase.getInstance().codigoDefeitosDao()


    fun getMenu(menuIDs: List<Int>): LiveData<List<MenuEntity>> {
        return daoMenu.getMenu(menuIDs)
    }

    fun getSubMenu(subMenuIDs: Int): MenuEntity {
        return daoMenu.getSubMenu(subMenuIDs)
    }

    fun getDtc(dtcID: List<Int>?): LiveData<List<DtcEntity>> {
        return daoDtc.getCodDefeito(dtcID)
    }

    fun getValue(valueID: List<Int>?): LiveData<List<ValueEntity>> {
        return daoValue.getLeituras(valueID)
    }

    @DelicateCoroutinesApi
    fun insereLeiturasRelatorio(leitura: RelatorioLeiturasEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            daoLeituras.addLeitura(leitura)
        }
    }

    @DelicateCoroutinesApi
    fun insereCodigoDefeitoRelatorio(defeito: RelatorioCodigoDefeitosEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            daoCodDefeitos.add(defeito)
        }
    }

}
