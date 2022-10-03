package br.com.tecnomotor.thanos.ui.diagnostico.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.diagnostico.IdentificacaoEcuAdapter
import br.com.tecnomotor.thanos.database.cliente.ClienteDatabase
import br.com.tecnomotor.thanos.database.cliente.dao.EcuDao
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.repository.diagnostico.IdentificacaoEcuRepository
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.IdentificacaoEcuViewModel
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.factory.IdentificacaoEcuViewModelFactory
import kotlinx.android.synthetic.main.header.*

class EcuFragment : Fragment() {

    private val selecao = Selecao.getInstance()

    private var loadingSpinner: ProgressBar? = null
    private val ecuViewModel by lazy {
        val ecuRepository = IdentificacaoEcuRepository(ClienteDatabase.getInstance(context).ecuDao())
        val ecuFactory = IdentificacaoEcuViewModelFactory(ecuRepository)
        val provedor = ViewModelProvider(this, ecuFactory)
        provedor[IdentificacaoEcuViewModel::class.java]
    }

    val adapter: IdentificacaoEcuAdapter by lazy { IdentificacaoEcuAdapter(requireContext()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Identificação da ECU"
        return inflater.inflate(R.layout.fragment_ecu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingSpinner = view.findViewById(R.id.loading_spinner)
        loadingSpinner?.visibility = View.INVISIBLE
//        if (adapter.itemCount > 0) loadingSpinner?.visibility = View.INVISIBLE
        configuraRecyclerView()
        configuraCabecalho()
    }

    private fun configuraRecyclerView() {
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            view?.context,
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        val rvItens = view?.findViewById<RecyclerView>(R.id.rvListEcu)
        rvItens?.layoutManager = lManager
        rvItens?.adapter = adapter
        pegaListaEstatica()


    }

    private fun pegaListaEstatica() {
        ecuViewModel.criaEcuEstatico()
        val ecuList = ecuViewModel.getEcuList()
        adapter.atualiza(ecuList)
    }

    fun itemClicado() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_identificacao_ecu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_codDefeito_screenshot -> {
                Toast.makeText(context, "Print", Toast. LENGTH_SHORT).show()
            }

            R.id.menu_identificacaoEcu_sendReport -> {
                //DialogEscolhaRelatorio(requireContext()).mostra()
                ecuViewModel.enviaEcuEstaticoRelatorio()
                Toast.makeText(context, "Enviado para o relatório", Toast. LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraCabecalho() {
        tv_montadora.text = selecao.montadora.nome
        tv_modelo.text = selecao.veiculo.nome
        tv_motorizacao.text = selecao.motorizacao.nome
        tv_sistema.text = selecao.sistema.getNomeAno()
    }
}