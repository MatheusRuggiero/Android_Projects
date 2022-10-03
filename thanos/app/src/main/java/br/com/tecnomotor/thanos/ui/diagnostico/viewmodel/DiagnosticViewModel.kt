package br.com.tecnomotor.thanos.ui.diagnostico.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import br.com.tecnomotor.rasther.module.DiagMan
import br.com.tecnomotor.rasther.module.DiagMenuItem
import br.com.tecnomotor.rasther.module.DiagModule
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioAplicacaoEntity
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioCodigoDefeitosEntity
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioLeiturasEntity
import br.com.tecnomotor.thanos.database.messages.entity.DtcEntity
import br.com.tecnomotor.thanos.database.messages.entity.MenuEntity
import br.com.tecnomotor.thanos.database.messages.entity.ValueEntity
import br.com.tecnomotor.thanos.model.cliente.Favorito
import br.com.tecnomotor.thanos.model.cliente.Historico
import br.com.tecnomotor.thanos.model.diagnostico.*
import br.com.tecnomotor.thanos.model.menu.Aplicacao
import br.com.tecnomotor.thanos.repository.diagnostico.DiagnosticRepository
import br.com.tecnomotor.thanos.repository.menu.FavoritosRepository
import br.com.tecnomotor.thanos.repository.menu.HistoricoRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@InternalCoroutinesApi
class DiagnosticViewModel : ViewModel() {
    private val favoritosRepository = FavoritosRepository.getInstance()
    private val historicosRepository = HistoricoRepository.getInstance()
    private val diagnosticRepository = DiagnosticRepository()

    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    private lateinit var diagModule: DiagModule
    private lateinit var diagMenuItem: DiagMenuItem

    private lateinit var sourceMenu: LiveData<List<MenuEntity>>
    val menuLiveData = MediatorLiveData<List<DiagnosticoMenu>>()
    private lateinit var sourceDtc: LiveData<List<DtcEntity>>
    val dtcLiveData = MediatorLiveData<List<CodigoDefeito>>()
    private lateinit var sourceValue: LiveData<List<ValueEntity>>
    val valueLiveData = MediatorLiveData<List<Leitura>>()

    private var leiturasSelecionadas: List<Leitura>? = null
    var relatorioSelecionado: RelatorioAplicacaoEntity? = null

    fun attachContext(context: Context) {
        this.context = context
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun inserirFavorito(favorito: Favorito) {
        favoritosRepository.inserir(favorito)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deletarFavorito(favorito: Favorito) {
        favoritosRepository.deletar(favorito)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun insereHistorico(historico: Historico) {
        historicosRepository.inserirHistorico(historico)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun enviaLeiturasRelatorio(relatorioLeituras: RelatorioLeiturasEntity) {
        diagnosticRepository.insereLeiturasRelatorio(relatorioLeituras)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun enviaCodDefeitosRelatorio(relatorioCodDefeitos: RelatorioCodigoDefeitosEntity) {
        diagnosticRepository.insereCodigoDefeitoRelatorio(relatorioCodDefeitos)
    }

    fun selecionaRelatorio(relatorio: RelatorioAplicacaoEntity) {
        relatorioSelecionado = relatorio
    }

    fun guardaLeiturasSelecionadasViewModel(leituras: MutableList<Leitura>) {
//        criaObjetoRelatorio(configDevice) // Executar apenas uma vez - Excluir esse metodo
        leiturasSelecionadas = leituras
    }

    fun enviaLeiturasParaRelatorio() {
        if (leiturasSelecionadas?.size!! > 0 && relatorioSelecionado != null) {
            snackbarEnviandoRelatorio()
            leiturasSelecionadas!!.map { leitura ->
                val leituraTipo = leitura.tipo.ordinal.toLong()
                val relatorioLeituras = RelatorioLeiturasEntity(
                    0, relatorioSelecionado!!.id, leituraTipo,
                    leitura.nome, 20.00, 40.00, leitura.valor.toString().toDouble(),
                    leitura.unidade.nome, leitura.valor.toString(), Calendar.getInstance()
                )
                enviaLeiturasRelatorio(relatorioLeituras)
            }
        } else {
            Toast.makeText(context, "Nenhuma leitura ou relatorio selecionado!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun snackbarEnviandoRelatorio() {
        Toast.makeText(
            context,
            "Enviando para o relatório...",
            Toast.LENGTH_LONG
        ).show()
    }

//    fun criaObjetoRelatorio() {
//        val configDevice = ConfigDevice.getInstance(context)
//        val relatorioEntity = RelatorioEntity(
//            0, configDevice.getSerialNumber(), configDevice.getFirmwareVersion(),
//            configDevice.getPlatform().toString(), configDevice.getVersion().toLong(),
//            configDevice.getVersion().toString(), false
//        )
//        ClienteDatabase.getInstance().relatoriosDao().add(relatorioEntity)
//
//        criaObjetoRelatorioAplicacao(configDevice)  // Executar apenas uma vez - Excluir esse metodo
//    }
//
//    fun criaObjetoRelatorioAplicacao(configDevice: ConfigDevice) {
//        val relatorioAplicacaoEntity = RelatorioAplicacaoEntity(
//            0, 1, "d1213", 2, 2,
//            "Volks", 1, "Gol", 2, "2v",
//            2, "Teste", 2, "SistemaTeste",
//            2020, 2022, 3, Calendar.getInstance()
//        )
//        ClienteDatabase.getInstance().relatoriosAplDao().add(relatorioAplicacaoEntity)
//    }

    fun getFavoritoByAplicacao(aplicacao: Aplicacao) =
        favoritosRepository.getFavoritoByAplicacao(aplicacao)

    fun favoritoExist(favorito: Favorito) = favoritosRepository.isExist(favorito)

    private fun getMenu(idMenuList: List<Int>) {
        if (this::sourceMenu.isInitialized)
            menuLiveData.removeSource(sourceMenu)
        sourceMenu = diagnosticRepository.getMenu(idMenuList)
        menuLiveData.addSource(sourceMenu) { menu ->
            val newlistMenu = arrayListOf<DiagnosticoMenu>()
            menu.forEach { newlistMenu.add(it.toDiagnosticoMenu()) }
            menuLiveData.postValue(newlistMenu)
        }
    }

    fun getDtcListDebug() {
        val dtcIds = diagModule.dtcOBDII?.itens?.map { it.msg } ?: arrayListOf()
        val apenas20: List<Int> = dtcIds.take(20)
        getDtc(apenas20)
    }

    private fun getDtc(idDtcList: List<Int>) {
        if (this::sourceDtc.isInitialized)
            dtcLiveData.removeSource(sourceDtc)
        sourceDtc = diagnosticRepository.getDtc(idDtcList)
        dtcLiveData.addSource(sourceDtc) { dtcList ->
            val codigoDefeitoList = arrayListOf<CodigoDefeito>()
            dtcList.forEach {
                codigoDefeitoList.add(it.toCodigoDefeito())
            }
            dtcLiveData.postValue(codigoDefeitoList)
        }
    }


    fun getValueListDebug() {
        val valueIds = diagModule.value?.itens?.map { it.msg } ?: arrayListOf()
        val apenas20 = valueIds.take(20)
        getValue(apenas20)
    }

    fun getValue(idValueList: List<Int>) {
        if (this::sourceValue.isInitialized)
            valueLiveData.removeSource(sourceValue)
        sourceValue = diagnosticRepository.getValue(idValueList)
        valueLiveData.addSource(sourceValue) { valueList ->
            val leituraList = arrayListOf<Leitura>()
            valueList.forEach {
                leituraList.add(it.toLeitura())
            }
            valueLiveData.postValue(leituraList)
        }
    }

    fun openModulo(moduloId: Long) {
        if (!this::context.isInitialized)
            throw ExceptionInInitializerError("Erro na inicialização do módulo")
        if (!this::diagModule.isInitialized)
            diagModule = DiagModule(context)
        val formatModule = "M0DuL3 F0R %S 7eCn0m70r 3l37r0n1cA d0 BrA51L"
        val moduleSec = formatModule.format(String.format("%08d", moduloId))
        diagModule.loadModules(moduloId.toInt(), moduleSec)
        val idMenuList: List<Int> = diagModule.menu?.itens?.map { it.msg } ?: arrayListOf()
        getMenu(idMenuList)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun openModulo2(moduloId: Long) : MutableLiveData<List<Atuador>> {
        if (!this::context.isInitialized)
            throw ExceptionInInitializerError("Erro na inicialização do módulo")
        if (!this::diagModule.isInitialized)
            diagModule = DiagModule(context)
        val formatModule = "M0DuL3 F0R %S 7eCn0m70r 3l37r0n1cA d0 BrA51L"
        val moduleSec = formatModule.format(String.format("%08d", moduloId))
        diagModule.loadModules(moduloId.toInt(), moduleSec)


       // Log.d("imprimindo os modulos openModulo 2" ,"${diagModule.menu?.itens}" )

        val idMenuSelecionado = 4
       val menuSelecionado = diagModule.menu?.itens?.get(idMenuSelecionado)

        val listaAtuador : ArrayList<Atuador> = arrayListOf()
        val listaAtuadoreLiveData = MutableLiveData<List<Atuador>>(listaAtuador)

        GlobalScope.launch {
        menuSelecionado?.subMenu?.itens?.forEach {

                val menuTexto = diagnosticRepository.getSubMenu(it.msg)

                listaAtuador.add(
                    Atuador(
                        it.msg,
                        menuTexto.valuePtLong ?: "",
                        it.man.get(DiagMan.Idioma.pt).value
                    )
                )
            }
            println("imprimindo a lista de Atuadores ${listaAtuador.joinToString()}")
            listaAtuadoreLiveData.postValue(listaAtuador)
        }

        return listaAtuadoreLiveData

    }



    @OptIn(DelicateCoroutinesApi::class)
    fun openModuloAjuste(moduloId: Long) : MutableLiveData<List<Ajuste>> {
        if (!this::context.isInitialized)
            throw ExceptionInInitializerError("Erro na inicialização do módulo")
        if (!this::diagModule.isInitialized)
            diagModule = DiagModule(context)
        val formatModule = "M0DuL3 F0R %S 7eCn0m70r 3l37r0n1cA d0 BrA51L"
        val moduleSec = formatModule.format(String.format("%08d", moduloId))
        diagModule.loadModules(moduloId.toInt(), moduleSec)


         Log.d("imprimindo os modulos openModuloAjuste" ,"${diagModule.menu?.itens}" )

        val idMenuSelecionado = 5
        val menuSelecionado = diagModule.menu?.itens?.get(idMenuSelecionado)

        val listaAjuste : ArrayList<Ajuste> = arrayListOf()
        val listaAjusteLiveData = MutableLiveData<List<Ajuste>>(listaAjuste)

        GlobalScope.launch {
            menuSelecionado?.subMenu?.itens?.forEach {

                val menuTexto = diagnosticRepository.getSubMenu(it.msg)

                listaAjuste.add(
                    Ajuste(
                        it.msg,
                        menuTexto.valuePtLong ?: "",
                        it.man.get(DiagMan.Idioma.pt).value
                    )
                )
            }
            println("imprimindo a lista de Ajustes ${listaAjuste.joinToString()}")
            listaAjusteLiveData.postValue(listaAjuste)
        }

        return listaAjusteLiveData

    }


    @OptIn(DelicateCoroutinesApi::class)
    fun openModuloProgramacoes(moduloId: Long) : MutableLiveData<List<Programacao>> {
        if (!this::context.isInitialized)
            throw ExceptionInInitializerError("Erro na inicialização do módulo")
        if (!this::diagModule.isInitialized)
            diagModule = DiagModule(context)
        val formatModule = "M0DuL3 F0R %S 7eCn0m70r 3l37r0n1cA d0 BrA51L"
        val moduleSec = formatModule.format(String.format("%08d", moduloId))
        diagModule.loadModules(moduloId.toInt(), moduleSec)


        // Log.d("imprimindo os modulos openModulo 2" ,"${diagModule.menu?.itens}" )

        val idMenuSelecionado = 6
        val menuSelecionado = diagModule.menu?.itens?.get(idMenuSelecionado)

        val listaProgramacao : ArrayList<Programacao> = arrayListOf()
        val listaProgramacaoLiveData = MutableLiveData<List<Programacao>>(listaProgramacao)

        GlobalScope.launch {
            menuSelecionado?.subMenu?.itens?.forEach {

                val menuTexto = diagnosticRepository.getSubMenu(it.msg)

                listaProgramacao.add(
                    Programacao(
                        it.msg,
                        menuTexto.valuePtLong ?: "",
                        it.man.get(DiagMan.Idioma.pt).value
                    )
                )
            }
            println("imprimindo a lista de Programaçoes ${listaProgramacao.joinToString()}")
            listaProgramacaoLiveData.postValue(listaProgramacao)
        }

        return listaProgramacaoLiveData

    }













    fun sendCodDefeitosReport() {
 //       criaObjetoRelatorio() // Executar apenas uma vez - Excluir esse metodo
        for (defeito in dtcLiveData.value!!) {
            val defeitosEncontrados = RelatorioCodigoDefeitosEntity(
                0,
                1,
                defeito.nome,
                "descrição",
                defeito.sintoma,
                defeito.status,
                Calendar.getInstance()
            )
            enviaCodDefeitosRelatorio(defeitosEncontrados)
        }
    }
}



