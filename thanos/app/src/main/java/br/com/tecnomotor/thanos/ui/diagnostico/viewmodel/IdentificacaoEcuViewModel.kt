package br.com.tecnomotor.thanos.ui.diagnostico.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioEcuEntity
import br.com.tecnomotor.thanos.model.diagnostico.IdentificacaoEcu
import br.com.tecnomotor.thanos.repository.diagnostico.IdentificacaoEcuRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

class IdentificacaoEcuViewModel(private val ecuRepository: IdentificacaoEcuRepository): ViewModel() {



    private val ecuList: ArrayList<IdentificacaoEcu> = arrayListOf()
//    val ecuLiveData: MutableLiveData<ArrayList<IdentificacaoEcu>> = MutableLiveData(ecuList)

    fun criaEcuEstatico(){
        ecuList.add(IdentificacaoEcu(1, "Abc123edc876FGTR4125", "Sensor teste 1"))
        ecuList.add(IdentificacaoEcu(2, "Abc321Fdc", "Sensor teste 2"))
        ecuList.add(IdentificacaoEcu(3, "Abc987eDG", "Sensor teste 3"))
        ecuList.add(IdentificacaoEcu(4, "Abc345hgf", "Sensor teste 4"))
    }

    fun getEcuList(): ArrayList<IdentificacaoEcu> {
        return ecuList
    }

//    fun getUnidade(unidade: Unidade) {
//        ecuRepository.getUnidades(unidade.id).observeForever {
//            if (it != null) {
//                Log.d(this.javaClass.simpleName, unidade.toString())
//                unidade.nome = it.valuePtLong ?: ""
//            }
//        }
//    }

    @OptIn(DelicateCoroutinesApi::class)
    fun enviaEcuRelatorio(relatorioEcu: RelatorioEcuEntity) {
        ecuRepository.insereEcuRelatorio(relatorioEcu)
    }

    fun enviaEcuEstaticoRelatorio(){
        for(ecuCode in ecuList){
            val ecuEncontrado = RelatorioEcuEntity(
                0,
                1,
                ecuCode.nome,
                ecuCode.valor,
                Calendar.getInstance()
            )
            enviaEcuRelatorio(ecuEncontrado)
        }
    }

}