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
import br.com.tecnomotor.thanos.model.menu.Veiculo
import br.com.tecnomotor.thanos.model.menu.toVeiculo
import br.com.tecnomotor.thanos.ui.menu.viewmodel.VeiculoViewModel
import kotlinx.android.synthetic.main.fragment_veiculos.*
import kotlinx.coroutines.*


class VeiculosFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[VeiculoViewModel::class.java]
    }
    private val adapter: AbstractAdapter by lazy { AbstractAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaVeiculos()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun buscaVeiculos() {
        val selecao = Selecao.getInstance()
        viewModel.getVeiculos(
            selecao.plataforma,
            selecao.versao,
            selecao.montadora
        ).observe(this, Observer { veiculosEntity ->
            val veiculos = mutableListOf<Veiculo>()
            veiculosEntity.forEach { veiculoEntity ->
                val veiculo = veiculoEntity.toVeiculo()
                veiculos.add(veiculo)
                viewModel.getVersaoByVeiculo(selecao.plataforma, selecao.montadora, veiculo)
                    .observe(this, Observer { versaoBd ->
                        veiculo.versaoBd = versaoBd?.id ?: 0
                        veiculo.estaNaVersao = (versaoBd?.id ?: 0 <= selecao.versao.id)
                        veiculo.montHabilitada = selecao.montadora.montHabilitada
                    })
            }
            GlobalScope.launch(Dispatchers.Main) {
                delay(500)
                while (veiculos.size < veiculosEntity.size) delay(50)
                if (veiculos.isNotEmpty())
                    loading_spinner.visibility = View.INVISIBLE
                if ((veiculos.size == 1) && (veiculos[0].versaoOkMontadoraOk())) avancoAutomatico(
                    veiculos[0]
                )
                else {
                    val veiculosHabilitadas = arrayListOf<Veiculo>()
                    val veiculosDesabilitadas = arrayListOf<Veiculo>()
                    val listaOrdenadaVeiculos = arrayListOf<Veiculo>()
                    veiculos.map { veiculo ->
                        if (veiculo.versaoOkMontadoraOk()) {
                            veiculosHabilitadas.add(veiculo)
                        } else {
                            veiculosDesabilitadas.add(veiculo)
                        }
                    }

                    listaOrdenadaVeiculos.addAll(veiculosHabilitadas)
                    listaOrdenadaVeiculos.addAll(veiculosDesabilitadas)

                    adapter.atualiza(listaOrdenadaVeiculos)
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Veículos"
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_veiculos, container, false)
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
        rvListVeiculos.layoutManager = lManager
        rvListVeiculos.adapter = adapter
    }

    private fun avancoAutomatico(veiculo: Veiculo) {
        Selecao.getInstance().setVeiculo(veiculo)
        val direcao = Directions.getMotorizacao(
            controlador.previousBackStackEntry?.destination?.id ?: R.id.fragment_montadoras
        )
        controlador.popBackStack(R.id.fragment_veiculos, true)
        controlador.navigate(direcao)
    }

    fun itemClicado(data: AbstractMenuBase) {
        Selecao.getInstance().setVeiculo(data as Veiculo)
        val direcao = Directions.getMotorizacao(R.id.fragment_veiculos)
        controlador.navigate(direcao)
    }


}