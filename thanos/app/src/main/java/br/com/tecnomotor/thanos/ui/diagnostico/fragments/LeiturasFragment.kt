package br.com.tecnomotor.thanos.ui.diagnostico.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.diagnostico.LeiturasAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.diagnostico.Leitura
import br.com.tecnomotor.thanos.ui.dialog.EscolhaRelatorioDialog
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.DiagnosticViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.header.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@DelicateCoroutinesApi
class LeiturasFragment : Fragment() {

    private val selecao = Selecao.getInstance()
    private val viewModel: DiagnosticViewModel by activityViewModels()

    private val adapter: LeiturasAdapter by lazy {
        context?.let {
            LeiturasAdapter(it)
        } ?: throw IllegalArgumentException("Context Inválido")
    }

    private val controlador by lazy {
        findNavController()
    }

    private var loadingSpinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        adapter.descartarSelecao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_leituras, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.leituras_title)
        configTitleBar()

        loadingSpinner = view.findViewById(R.id.loading_spinner)
        if (adapter.itemCount > 0) loadingSpinner?.visibility = View.INVISIBLE

        viewModel.valueLiveData.observe(requireActivity()) {
            if (it != null)
                loadingSpinner?.visibility = View.INVISIBLE
            adapter.atualiza(viewModel.valueLiveData)
        }

        viewModel.getValueListDebug()
        configuraRecyclerView()
        configuraCabecalho()
    }

    private fun configTitleBar() {
        viewModel.valueLiveData.observe(viewLifecycleOwner) {
            val leituras = adapter.getLeiturasSelecionadas()
            if (leituras.size > 0) {
                var textSelecao = "leitura selecionada"
                if (leituras.size > 1) {
                    textSelecao = "leituras selecionada"
                }
                activity?.title = "${leituras.size} $textSelecao"
            } else {
                activity?.title = getString(R.string.leituras_title)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_fragment_leituras, menu)
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    @SuppressLint("ResourceType")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_diagnostic_report -> {
                enviaLeiturasParaRelatorio()
            }
            R.id.menu_item_selecionar_todos -> {
                adapter.selectTodasLeituras()
            }
            R.id.menu_item_descartar_selecao -> {
                adapter.descartarSelecao()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun enviaLeiturasParaRelatorio() {
        val leituras = adapter.getLeiturasSelecionadas()
        if (leituras.size > 0) {
            if (viewModel.relatorioSelecionado == null) {
                viewModel.guardaLeiturasSelecionadasViewModel(leituras)
                selecionarRelatorio()
            } else {
                viewModel.enviaLeiturasParaRelatorio()
                adapter.descartarSelecao()
//                snackbarEnviandoRelatorio()
            }
        } else {
            alertaNehumaLeituraSelecionada()
        }
    }

    private fun snackbarEnviandoRelatorio() {
        Snackbar.make(
            requireView(),
            "Enviando para o relatório...",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun selecionarRelatorio() {
        EscolhaRelatorioDialog(
            requireContext(),
            requireParentFragment(),
        ).mostra(LeiturasFragmentDirections.actionGlobalSelectRelatorio())
    }

    private fun alertaNehumaLeituraSelecionada() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Nenhuma leitura selecionada!")
            .setMessage("Segure pressionado as leituras que deseja salvar no relatório.")
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
            }
            .show()
    }

    private fun configuraCabecalho() {
        tv_montadora.text = selecao.montadora.nome
        tv_modelo.text = selecao.veiculo.nome
        tv_motorizacao.text = selecao.motorizacao.nome
        tv_sistema.text = selecao.sistema.getNomeAno()
    }


    private fun configuraRecyclerView() {
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            requireContext(),
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        val rvItens = view?.findViewById<RecyclerView>(R.id.rvListLeituras)
        rvItens?.layoutManager = lManager
        rvItens?.adapter = adapter
        adapter.itemClicado = this::itemClicado
    }

    fun itemClicado(leitura: Leitura) {
//        println("imprimindo o ID da Leitura clicada " + leitura.nome)
        configTitleBar()
    }
}
