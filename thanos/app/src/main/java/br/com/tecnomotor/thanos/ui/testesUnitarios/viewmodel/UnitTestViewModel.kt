package br.com.tecnomotor.thanos.ui.testesUnitarios.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.tecnomotor.rasther.Rasther
import br.com.tecnomotor.rasther.RastherInfo
import br.com.tecnomotor.rasther.RastherRepository
import kotlinx.coroutines.*
/**
@Author Matheus_Ruggiero
 */


@OptIn(InternalCoroutinesApi::class)
class UnitTestViewModel: ViewModel() {

    @OptIn(DelicateCoroutinesApi::class)
    var rasther: Rasther = RastherRepository.getInstance().rasther
    private val _rastherInfo = MutableLiveData<RastherInfo>()


    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "ViewModel dos Botoes de Teste $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }


   @OptIn(DelicateCoroutinesApi::class)
   fun innicialization(){
       //rasther.initialization()
   }

    fun startDiagnostico(){
        println("Funcao startDiagnostico no viewModel funcionando ")

    }


}