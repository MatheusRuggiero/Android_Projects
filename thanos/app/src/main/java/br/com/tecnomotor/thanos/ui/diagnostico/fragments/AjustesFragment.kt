package br.com.tecnomotor.thanos.ui.diagnostico.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.diagnostico.AjustesAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.DiagnosticViewModel
import kotlinx.android.synthetic.main.fragment_diagnostico.*
import kotlinx.coroutines.InternalCoroutinesApi

class AjustesFragment : Fragment() {

    private val selecao = Selecao.getInstance()

    @OptIn(InternalCoroutinesApi::class)
    private val diagnosticViewModel: DiagnosticViewModel by activityViewModels()

    private val adapter: AjustesAdapter by lazy {
        context?.let {
            AjustesAdapter(it)
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ajustes, container, false)
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Ajustes"

        val subMenu = diagnosticViewModel.openModuloAjuste(selecao.aplicacao.modulo)
        subMenu.observe(requireActivity()) {
            if (it.isNotEmpty()) loading_spinner.visibility = View.INVISIBLE
            adapter.atualiza(it)
        }
        configuraRecyclerView()
    }


    private fun configuraRecyclerView() {
        //adapter.itemClicado = this::itemClicado
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            requireContext(),
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        val rvItens = view?.findViewById<RecyclerView>(R.id.lista_ajustes_recyclerview)
        rvItens?.layoutManager = lManager
        rvItens?.adapter = adapter
    }
}