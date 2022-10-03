package br.com.tecnomotor.thanos.ui.diagnostico.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.diagnostico.SelectRelatorioAdapter
import br.com.tecnomotor.thanos.database.cliente.ClienteDatabase
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioAplicacaoEntity
import br.com.tecnomotor.thanos.repository.cliente.RelatorioAplRepository
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.DiagnosticViewModel
import br.com.tecnomotor.thanos.ui.relatorio.factory.ListaRelatoriosViewModelFactory
import br.com.tecnomotor.thanos.ui.relatorio.viewmodel.ListaRelatoriosViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@DelicateCoroutinesApi
class SelecionaRelatorioFragment : Fragment() {
    private val adapter by lazy {
        context?.let {
            SelectRelatorioAdapter(it)
        } ?: throw IllegalArgumentException("Context Inválido")
    }

    private val listaRelatoriosViewModel by lazy {
        val repository =
            RelatorioAplRepository(ClienteDatabase.getInstance(context).relatoriosAplDao())
        val factory = ListaRelatoriosViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListaRelatoriosViewModel::class.java]
    }

    private val controlador by lazy {
        findNavController()
    }

    private val diagnosticViewModel: DiagnosticViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaRelatorios()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lista_relatorios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.lista_de_relatorios)
        configuraRecyclerView()
    }

    private fun buscaRelatorios() {
        listaRelatoriosViewModel.buscaTodos().observe(this, Observer { resource ->
            resource.dado?.let { adapter.atualiza(it) }
            resource.erro?.let {
                Toast.makeText(
                    requireContext(),
                    "Não foi possível carregar os relatórios",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }


    private fun configuraRecyclerView() {
        val listaRelatoriosView =
            activity?.findViewById<RecyclerView>(R.id.lista_relatorios_recyclerview)
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            view?.context,
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        listaRelatoriosView?.layoutManager = lManager
        listaRelatoriosView?.adapter = adapter

        adapter.itemClicado = this::relatorioSelecionado
    }

    private fun relatorioSelecionado(relatorio: RelatorioAplicacaoEntity) {
        diagnosticViewModel.selecionaRelatorio(relatorio)
        diagnosticViewModel.enviaLeiturasParaRelatorio()
//        snackbarEnviandoRelatorio()
        controlador.navigateUp()
    }

    private fun snackbarEnviandoRelatorio() {
        Snackbar.make(
            requireView(),
            "Enviando para o relatório...",
            Snackbar.LENGTH_LONG
        ).show()
    }
}