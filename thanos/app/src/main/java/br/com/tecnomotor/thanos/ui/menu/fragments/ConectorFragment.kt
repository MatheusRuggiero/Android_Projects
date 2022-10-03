package br.com.tecnomotor.thanos.ui.menu.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.menu.ConectorAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.menu.Conector
import br.com.tecnomotor.thanos.model.menu.toConector
import br.com.tecnomotor.thanos.ui.menu.viewmodel.ConectorViewModel
import kotlinx.android.synthetic.main.fragment_conectores.*
import kotlinx.coroutines.*

class ConectorFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this)[ConectorViewModel::class.java]
    }

    val adapter: ConectorAdapter by lazy { ConectorAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    private var loadingSpinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaConectores()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun buscaConectores() {
        val selecao = Selecao.getInstance()
        viewModel.getConectores(
            selecao.plataforma,
            selecao.versao,
            selecao.montadora,
            selecao.veiculo,
            selecao.motorizacao,
            selecao.tipoDeSistema,
            selecao.sistema
        ).observe(this, Observer { conectoresEntity ->
            val conectores = mutableListOf<Conector>()
            conectoresEntity.forEach { conectorEntity ->
                val conector = conectorEntity.toConector()
                conectores.add(conector)
                viewModel.getVersaoByConector(selecao.plataforma, selecao.montadora, selecao.veiculo,
                    selecao.motorizacao, selecao.tipoDeSistema, selecao.sistema, conector).observe(this, Observer { versaoBd ->
                    conector.versaoBd = versaoBd?.id ?: 0
                    conector.estaNaVersao = (versaoBd?.id ?: 0 <= selecao.versao.id)
                    conector.montHabilitada = selecao.montadora.montHabilitada
                })
            }
            GlobalScope.launch(Dispatchers.Main) {
                delay(50)
                while (conectores.size < conectoresEntity.size) delay(50)

                if (conectores.isNotEmpty())
                    loadingSpinner?.visibility = View.INVISIBLE
                if ((conectores.size == 1) && (conectores[0].versaoOkMontadoraOk())) avancoAutomatico(
                    conectores[0]
                )
                else adapter.atualiza(conectores)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.title = "Conectores"
        return inflater.inflate(R.layout.fragment_conectores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingSpinner = view.findViewById(R.id.loading_spinner)
        if (adapter.itemCount > 0) loadingSpinner?.visibility = View.INVISIBLE
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
        rvListConectores.layoutManager = lManager
        rvListConectores.adapter = adapter
    }

    private fun avancoAutomatico(conector: Conector) {
        Selecao.getInstance().setConector(conector)
        val direcao = Directions.getPosConectores(controlador.previousBackStackEntry?.destination?.id ?: R.id.fragment_sistemas)
        controlador.popBackStack(R.id.fragment_conectores, true)
        controlador.navigate(direcao)
    }

    fun ItemClicado(conector: Conector) {
        Selecao.getInstance().setConector(conector)
        val direcao = Directions.getPosConectores(R.id.fragment_posicaoConector)
        controlador.navigate(direcao)
    }

}