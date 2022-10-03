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
import br.com.tecnomotor.thanos.adapter.menu.SistemaAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.menu.Sistema
import br.com.tecnomotor.thanos.model.menu.toSistema
import br.com.tecnomotor.thanos.ui.menu.viewmodel.SistemaViewModel
import kotlinx.android.synthetic.main.fragment_sistemas.*
import kotlinx.coroutines.*

class SistemasFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[SistemaViewModel::class.java]
    }

    val adapter: SistemaAdapter by lazy { SistemaAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaSistemas()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun buscaSistemas() {
        val selecao = Selecao.getInstance()
        viewModel.getSistemas(
            selecao.plataforma, selecao.versao, selecao.montadora, selecao.veiculo,
            selecao.motorizacao, selecao.tipoDeSistema
        ).observe(this, Observer { sistemasEntity ->
            val sistemas = mutableListOf<Sistema>()
            sistemasEntity.forEach { sistemaEntity ->
                val sistema = sistemaEntity.toSistema()
                sistemas.add(sistema)
                viewModel.getVersaoBySistemas(
                    selecao.plataforma, selecao.montadora, selecao.veiculo,
                    selecao.motorizacao, selecao.tipoDeSistema, sistema
                ).observe(this, Observer { versaoBd ->
                    sistema.versaoBd = versaoBd?.id ?: 0
                    sistema.estaNaVersao = (versaoBd?.id ?: 0 <= selecao.versao.id)
                    sistema.montHabilitada = selecao.montadora.montHabilitada
                })
            }
            GlobalScope.launch(Dispatchers.Main) {
                delay(50)
                while (sistemas.size < sistemasEntity.size) delay(50)

                if (sistemas.isNotEmpty())
                    loading_spinner.visibility = View.INVISIBLE
                if ((sistemas.size == 1) && (sistemas[0].versaoOkMontadoraOk())) avancoAutomatico(
                    sistemas[0]
                )
                else {

                    val sistemasHabilitadas = arrayListOf<Sistema>()
                    val sistemasDesabilitadas = arrayListOf<Sistema>()
                    val listaOrdenadaSistemas = arrayListOf<Sistema>()
                    sistemas.map { sistema ->
                        if (sistema.versaoOkMontadoraOk()) {
                            sistemasHabilitadas.add(sistema)
                        } else {
                            sistemasDesabilitadas.add(sistema)
                        }
                    }

                    listaOrdenadaSistemas.addAll(sistemasHabilitadas)
                    listaOrdenadaSistemas.addAll(sistemasDesabilitadas)

                    adapter.atualiza(listaOrdenadaSistemas)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //println("onCreateView SistemaFragment")
        activity?.title = "Sistemas"
        return inflater.inflate(R.layout.fragment_sistemas, container, false)
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
        rvListSistema.layoutManager = lManager
        rvListSistema.adapter = adapter
    }

    private fun avancoAutomatico(sistema: Sistema) {
        Selecao.getInstance().setSistema(sistema)
        val direcao = Directions.getConectores(
            controlador.previousBackStackEntry?.destination?.id ?: R.id.fragment_tipoSistemas
        )
        controlador.popBackStack(R.id.fragment_sistemas, true)
        controlador.navigate(direcao)
    }

    private fun ItemClicado(sistema: Sistema) {
        Selecao.getInstance().setSistema(sistema)
        val direcao = Directions.getConectores(R.id.fragment_sistemas)
        controlador.navigate(direcao)
    }

}