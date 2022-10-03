package br.com.tecnomotor.thanos.ui.diagnostico.fragments

import ScreenShotView
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tecnomotor.rasther.module.DiagValueItem
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.diagnostico.AnaliseGraficaAdapter
import br.com.tecnomotor.thanos.database.cliente.ClienteDatabase
import br.com.tecnomotor.thanos.database.messages.MessagesDatabase
import br.com.tecnomotor.thanos.model.diagnostico.AnaliseGrafica
import br.com.tecnomotor.thanos.repository.diagnostico.AnaliseGraficaRepository
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.AnaliseGraficaViewModel
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.factory.AnaliseGraficaViewModelFactory
import br.com.tecnomotor.thanos.util.Parameters
import kotlinx.android.synthetic.main.fragment_analise_grafica.*

class AnaliseGraficaFragment : Fragment() {

    private lateinit var analiseGraficaModulo: ArrayList<DiagValueItem>
    private lateinit var analiseGraficaAdapter: ArrayList<AnaliseGrafica>
    //private lateinit var selecao: Selecao

    private val viewModel by lazy {
        val analiseGraficaRepository = AnaliseGraficaRepository(
            MessagesDatabase.getInstance().valueDao(),
            MessagesDatabase.getInstance().unitDao(),
            ClienteDatabase.getInstance().graficosDao()
        )

        val factory = AnaliseGraficaViewModelFactory(analiseGraficaRepository)
        val provedor = ViewModelProvider(this, factory)
        provedor[AnaliseGraficaViewModel::class.java]
    }

    val adapter: AnaliseGraficaAdapter by lazy { AnaliseGraficaAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    //private var loadingSpinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("onCreateView")
        activity?.title = getString(R.string.analise_grafica_title)
        return inflater.inflate(R.layout.fragment_analise_grafica, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (adapter.itemCount > 0) loading_spinner.visibility = View.INVISIBLE

        viewModel.analiseGraficaLiveData.observe(requireActivity()) {
            this.analiseGraficaAdapter = it
            if (it != null)
                loading_spinner.visibility = View.INVISIBLE
            adapter.atualiza(it)
        }

        val args = this.arguments
        if ((args != null) && (args.size() > 0)) {
            //selecao = args.get(Parameters.selecaoParam) as Selecao
            analiseGraficaModulo = args[Parameters.analiseGraficaParam] as ArrayList<DiagValueItem>
            viewModel.getAnaliseGrafica(analiseGraficaModulo)

        }
        configuraRecyclerView()
        configuraCabecalho()
    }

    private fun configuraCabecalho() {
//        val textMontadora: TextView = requireView().findViewById(R.id.tv_montadora)
//        val textModelo: TextView = requireView().findViewById(R.id.tv_modelo)
//        val textMotorizacao: TextView = requireView().findViewById(R.id.tv_motorizacao)
//        val textSistema: TextView = requireView().findViewById(R.id.tv_sistema)
//        textMontadora.text = selecao.montadoraEntity?.nome ?: ""
//        textModelo.text = selecao.veiculoEntity?.nome ?: ""
//        textMotorizacao.text = selecao.motorizacaoEntity?.nome ?: ""
//        textSistema.text = selecao.sistemaAplicacaoEntity?.nome ?: ""
    }

    private fun configuraRecyclerView() {
        adapter.itemClicado = this::ItemClicado
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            view?.context,
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        rvListAnaliseGrafica.layoutManager = lManager
        rvListAnaliseGrafica.adapter = adapter
    }

    fun ItemClicado(analiseGrafica: AnaliseGrafica) {
        println("imprimindo o ID da Leitura de Analise Grafica " + analiseGrafica.nomeAnalise)
    }

//    private fun buscaANnaliseGrafica() {
//        val idLeituraAna: List<DiagValueItem> = AnaliseGraficaIDs.map{ it }.filter{ it.typeValue.analisisGraph.enabled }
//        val id = idLeituraAna.map { it.msg }
//        viewModel.getAnaliseGrafica(id)
//    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_analise_grafica, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val graficView = requireView().findViewById<View>(R.id.analiseGrafica)
        when (item.itemId) {
            R.id.menu_analiseGrafica_screenshot -> {
                ScreenShotView(context!!).savePrintToGalery(graficView)
                Toast.makeText(context, "Print", Toast.LENGTH_SHORT).show()
            }
//            R.id.menu_analiseGrafica_order -> {
//                Toast.makeText(context, "Ordenar", Toast.LENGTH_SHORT).show()
//            }

            R.id.menu_analiseGrafica_graphs -> {
                Toast.makeText(context, "Gráficos", Toast.LENGTH_SHORT).show()
            }

            R.id.menu_analiseGrafica_reports -> {
                Toast.makeText(context, "Enviando para o relatório...", Toast.LENGTH_SHORT)
                    .show()
                viewModel.recebeGraficoParaRelatorio(graficView, context!!)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}