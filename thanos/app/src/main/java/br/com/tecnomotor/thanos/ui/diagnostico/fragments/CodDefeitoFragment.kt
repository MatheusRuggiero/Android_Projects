package br.com.tecnomotor.thanos.ui.diagnostico.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.diagnostico.CodDefeitoAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.diagnostico.CodigoDefeito
import br.com.tecnomotor.thanos.ui.dialog.EscolhaRelatorioDialog
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.DiagnosticViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@DelicateCoroutinesApi
class CodDefeitoFragment : Fragment() {

    private val selecao = Selecao.getInstance()

    private val viewModel: DiagnosticViewModel by activityViewModels()

    private val adapter: CodDefeitoAdapter by lazy {
        context?.let {
            CodDefeitoAdapter(it)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.dtc_title)
        return inflater.inflate(R.layout.fragment_codigo_defeito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingSpinner = view.findViewById(R.id.loading_spinner)
        configuraRecyclerView()

        viewModel.dtcLiveData.observe(requireActivity()) { codigoDefeito ->
            if (codigoDefeito.isNotEmpty()) loadingSpinner?.visibility = View.INVISIBLE
            adapter.atualiza(codigoDefeito)
        }
        buscaCodigoDefeito()
        configuraCabecalho()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_fragment_cod_defeitos, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cod_defeito_menu_item_report -> {
                EscolhaRelatorioDialog(
                    requireContext(),
                    requireParentFragment(),
                ).mostra(CodDefeitoFragmentDirections.actionGlobalSelectRelatorio())
//                viewModel.sendCodDefeitosReport()
            }
            R.id.cod_defeito_menu_item_remover -> {
                Toast.makeText(requireContext(), "Remover leituras da memória", Toast.LENGTH_LONG)
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraCabecalho() {
        val textMontadora: TextView = requireView().findViewById(R.id.tv_montadora)
        val textModelo: TextView = requireView().findViewById(R.id.tv_modelo)
        val textMotorizacao: TextView = requireView().findViewById(R.id.tv_motorizacao)
        val textSistema: TextView = requireView().findViewById(R.id.tv_sistema)
        textMontadora.text = selecao.montadora.nome
        textModelo.text = selecao.veiculo.nome
        textMotorizacao.text = selecao.motorizacao.nome
        textSistema.text = selecao.sistema.getNomeAno()
    }

    private fun configuraRecyclerView() {
        adapter.itemClicado = this::itemClicado
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            requireContext(),
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        val rvItens = view?.findViewById<RecyclerView>(R.id.rvListCodigoDefeito)
        rvItens?.layoutManager = lManager
        rvItens?.adapter = adapter
    }

    private fun itemClicado(dtc: CodigoDefeito) {
        Log.d(this.javaClass.simpleName, dtc.toString())
    }

    private fun buscaCodigoDefeito() {
        viewModel.getDtcListDebug()
    }

}
