package br.com.tecnomotor.thanos.ui.home.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.tecnomotor.thanos.R
import kotlinx.android.synthetic.main.activity_system_requests.*

class SolicitacoesDeSistemasFragment : Fragment() {

    companion object {
        val urlSolicitacoes = "https://app.tecnomotor.com.br/solicitacao/#/rasther?btnHome=false"
    }

    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.development_request)
        return inflater.inflate(R.layout.fragment_solicitacoes_de_sistemas, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            wView.restoreState(savedInstanceState)
        } else {
            val wViewSettings = wView.settings
            wViewSettings.javaScriptEnabled = true
            wView.loadUrl(urlSolicitacoes)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        wView.saveState(outState)
    }
}