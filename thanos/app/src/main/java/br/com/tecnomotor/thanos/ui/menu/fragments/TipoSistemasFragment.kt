package br.com.tecnomotor.thanos.ui.menu.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.menu.AbstractAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.menu.AbstractMenuBase
import br.com.tecnomotor.thanos.model.menu.TipoDeSistema
import br.com.tecnomotor.thanos.model.menu.toTipoDeSistema
import br.com.tecnomotor.thanos.ui.menu.viewmodel.TipoSistemasViewModel
import kotlinx.android.synthetic.main.fragment_tipo_sistemas.*
import kotlinx.coroutines.*


class TipoSistemasFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[TipoSistemasViewModel::class.java]
    }

    val adapter: AbstractAdapter by lazy { AbstractAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaTipoSistemas()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun buscaTipoSistemas() {
        val selecao = Selecao.getInstance()
        viewModel.getTipoDeSistemas(
            selecao.plataforma,
            selecao.versao,
            selecao.montadora,
            selecao.veiculo,
            selecao.motorizacao
        ).observe(this, Observer { tipoDeSistemasEntity ->
            val tipoDeSistemas = mutableListOf<TipoDeSistema>()
            tipoDeSistemasEntity.forEach { tipoDeSistemaEntity ->
                val tipoDeSistema = tipoDeSistemaEntity.toTipoDeSistema()
                tipoDeSistemas.add(tipoDeSistema)
                viewModel.getVersaoByTipoDeSistemas(
                    selecao.plataforma, selecao.montadora, selecao.veiculo,
                    selecao.motorizacao, tipoDeSistema
                ).observeForever() { versaoBd ->
                    tipoDeSistema.versaoBd = versaoBd?.id ?: 0
                    tipoDeSistema.estaNaVersao = (versaoBd?.id ?: 0 <= selecao.versao.id)
                    tipoDeSistema.montHabilitada = selecao.montadora.montHabilitada
                }
            }
            GlobalScope.launch(Dispatchers.Main) {
                delay(50)
                while (tipoDeSistemas.size < tipoDeSistemasEntity.size) delay(50)
                if (tipoDeSistemas.isNotEmpty())
                    loading_spinner.visibility = View.INVISIBLE
                if ((tipoDeSistemas.size == 1) && (tipoDeSistemas[0].versaoOkMontadoraOk())) avancoAutomatico(
                    tipoDeSistemas[0]
                )
                else {
                    val tipoDeSistemasHabilitadas = arrayListOf<TipoDeSistema>()
                    val tipoDeSistemasDesabilitadas = arrayListOf<TipoDeSistema>()
                    val listaOrdenadaTipoDeSistemas = arrayListOf<TipoDeSistema>()
                    tipoDeSistemas.map { sistema ->
                        if (sistema.versaoOkMontadoraOk()) {
                            tipoDeSistemasHabilitadas.add(sistema)
                        } else {
                            tipoDeSistemasDesabilitadas.add(sistema)
                        }
                    }

                    listaOrdenadaTipoDeSistemas.addAll(tipoDeSistemasHabilitadas)
                    listaOrdenadaTipoDeSistemas.addAll(tipoDeSistemasDesabilitadas)

                    adapter.atualiza(listaOrdenadaTipoDeSistemas)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //println("onCreateView TiposSistemas")
        activity?.title = "Tipos de Sistemas"
        return inflater.inflate(R.layout.fragment_tipo_sistemas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adapter.itemCount > 0) loading_spinner.visibility = View.INVISIBLE
        configuraRecyclerView()
    }

    private fun configuraRecyclerView() {
        adapter.itemClicado = this::ItemClicado
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            view?.context,
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        rvListTipoSistemas.layoutManager = lManager
        rvListTipoSistemas.adapter = adapter
    }

    private fun avancoAutomatico(tipoDeSistema: TipoDeSistema) {
        Selecao.getInstance().setTipoSistema(tipoDeSistema)
        val direcao = Directions.getSistemas(
            controlador.previousBackStackEntry?.destination?.id ?: R.id.fragment_motorizacao
        )
        controlador.popBackStack(R.id.fragment_tipoSistemas, true)
        controlador.navigate(direcao)
    }

    fun ItemClicado(data: AbstractMenuBase) {
        Selecao.getInstance().setTipoSistema(data as TipoDeSistema)
        val direcao = Directions.getSistemas(R.id.fragment_tipoSistemas)
        controlador.navigate(direcao)
    }
}