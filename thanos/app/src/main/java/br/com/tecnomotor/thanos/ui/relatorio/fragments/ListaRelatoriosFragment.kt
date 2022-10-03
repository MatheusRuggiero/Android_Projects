package br.com.tecnomotor.thanos.ui.relatorio.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.ListaRelatoriosAdapter
import br.com.tecnomotor.thanos.database.cliente.ClienteDatabase
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioAplicacaoEntity
import br.com.tecnomotor.thanos.repository.cliente.RelatorioAplRepository
import br.com.tecnomotor.thanos.ui.relatorio.factory.ListaRelatoriosViewModelFactory
import br.com.tecnomotor.thanos.ui.relatorio.viewmodel.ListaRelatoriosViewModel

class ListaRelatoriosFragment : Fragment() {

    private val adapter by lazy {
        context?.let {
            ListaRelatoriosAdapter(it)
        } ?: throw IllegalArgumentException("Context Inválido")
    }

    private val viewModel by lazy {
        val repository =
            RelatorioAplRepository(ClienteDatabase.getInstance(context).relatoriosAplDao())
        val factory = ListaRelatoriosViewModelFactory(repository)
        ViewModelProvider(this, factory)[ListaRelatoriosViewModel::class.java]
    }
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        activity?.title = getString(R.string.reports)
        configuraRecyclerView()
    }

    private fun buscaRelatorios() {
        viewModel.buscaTodos().observe(this, Observer { resource ->
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
        adapter.itemClicado = this::ItemClicado
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
    }

    @SuppressLint("SdCardPath")
    fun ItemClicado(relatorio: RelatorioAplicacaoEntity) {

        controlador.navigate(R.id.action_listaRelatoriosFragment_to_webView)
        Log.d(this.javaClass.simpleName, relatorio.toString())

        viewModel.createJson(relatorio)
    }

}