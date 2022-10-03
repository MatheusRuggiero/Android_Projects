package br.com.tecnomotor.thanos.ui.diagnostico.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.diagnostico.SubMenusAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.DiagnosticViewModel
import kotlinx.android.synthetic.main.fragment_diagnostico.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import java.io.FileNotFoundException

@OptIn(InternalCoroutinesApi::class)
class SubMenusFragment : Fragment() {

    private val selecao = Selecao.getInstance()

    private val diagnosticViewModel: DiagnosticViewModel by activityViewModels()

    private val adapter: SubMenusAdapter by lazy {
        context?.let {
            SubMenusAdapter(it)
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
        return inflater.inflate(R.layout.fragment_atuadores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Atuadores"

              val subMenu = diagnosticViewModel.openModulo2(selecao.aplicacao.modulo)
                  subMenu.observe(requireActivity()){
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
        val rvItens = view?.findViewById<RecyclerView>(R.id.lista_atuadores_recyclerview)
        rvItens?.layoutManager = lManager
        rvItens?.adapter = adapter
    }
}