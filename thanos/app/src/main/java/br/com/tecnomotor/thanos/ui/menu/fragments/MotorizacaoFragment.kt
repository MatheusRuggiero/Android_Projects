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
import br.com.tecnomotor.thanos.model.menu.Motorizacao
import br.com.tecnomotor.thanos.model.menu.toMotorizacao
import br.com.tecnomotor.thanos.ui.menu.viewmodel.MotorizacaoViewModel
import kotlinx.android.synthetic.main.fragment_motorizacao.*
import kotlinx.coroutines.*

class MotorizacaoFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[MotorizacaoViewModel::class.java]
    }

    val adapter: AbstractAdapter by lazy { AbstractAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaMotorizacoes()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun buscaMotorizacoes() {
        val selecao = Selecao.getInstance()
        viewModel.getMotorizacoes(
            selecao.plataforma,
            selecao.versao,
            selecao.montadora,
            selecao.veiculo
        )
            .observe(this, Observer { motorizacoesEntity ->
                val motorizacoes = mutableListOf<Motorizacao>()
                motorizacoesEntity.forEach { motorizacaoEntity ->
                    val motorizacao = motorizacaoEntity.toMotorizacao()
                    motorizacoes.add(motorizacao)
                    viewModel.getVersaoByMotorizacao(
                        selecao.plataforma, selecao.montadora,
                        selecao.veiculo, motorizacao
                    ).observe(this, Observer { versaoBd ->
                        motorizacao.versaoBd = versaoBd?.id ?: 0
                        motorizacao.estaNaVersao = (versaoBd?.id ?: 0 <= selecao.versao.id)
                        motorizacao.montHabilitada = selecao.montadora.montHabilitada
                    })
                }
                GlobalScope.launch(Dispatchers.Main) {
                    delay(50)
                    while (motorizacoes.size < motorizacoesEntity.size) delay(50)

                    if (motorizacoes.isNotEmpty())
                        loading_spinner.visibility = View.INVISIBLE
                    if ((motorizacoes.size == 1) && (motorizacoes[0].versaoOkMontadoraOk())) avancoAutomatico(
                        motorizacoes[0]
                    )
                    else {
                        val motorizacoesHabilitadas = arrayListOf<Motorizacao>()
                        val motorizacoesDesabilitadas = arrayListOf<Motorizacao>()
                        val listaOrdenadaMotorizacoes = arrayListOf<Motorizacao>()
                        motorizacoes.map { motorizacao ->
                            if (motorizacao.versaoOkMontadoraOk()) {
                                motorizacoesHabilitadas.add(motorizacao)
                            } else {
                                motorizacoesDesabilitadas.add(motorizacao)
                            }
                        }

                        listaOrdenadaMotorizacoes.addAll(motorizacoesHabilitadas)
                        listaOrdenadaMotorizacoes.addAll(motorizacoesDesabilitadas)

                        adapter.atualiza(listaOrdenadaMotorizacoes)
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //println("onCreateView Motorização")
        activity?.title = "Motorização"
        return inflater.inflate(R.layout.fragment_motorizacao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adapter.itemCount > 0) loading_spinner.visibility = View.INVISIBLE
        configuraRecyclerView()
    }

    private fun configuraRecyclerView() {
        adapter.itemClicado = this::itemClicado
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            view?.context,
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        rvListMotorizacao.layoutManager = lManager
        rvListMotorizacao.adapter = adapter
    }

    private fun avancoAutomatico(motorizacao: Motorizacao) {
        Selecao.getInstance().setMotorizacao(motorizacao)
        val direcao = Directions.getTipoDeSistemas(
            controlador.previousBackStackEntry?.destination?.id ?: R.id.fragment_veiculos
        )
        controlador.popBackStack(R.id.fragment_motorizacao, true)
        controlador.navigate(direcao)
    }

    fun itemClicado(data: AbstractMenuBase) {
        Selecao.getInstance().setMotorizacao(data as Motorizacao)
        val direcao = Directions.getTipoDeSistemas(R.id.fragment_motorizacao)
        controlador.navigate(direcao)
    }
}