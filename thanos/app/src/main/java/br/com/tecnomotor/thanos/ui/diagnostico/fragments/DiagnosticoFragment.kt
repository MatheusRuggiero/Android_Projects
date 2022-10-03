package br.com.tecnomotor.thanos.ui.diagnostico.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.diagnostico.DiagnosticoMenuAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.cliente.Favorito
import br.com.tecnomotor.thanos.model.cliente.Historico
import br.com.tecnomotor.thanos.model.cliente.toFavorito
import br.com.tecnomotor.thanos.model.diagnostico.DiagnosticoMenu
import br.com.tecnomotor.thanos.model.diagnostico.EnumDiagnosticoMenu
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.DiagnosticViewModel
import br.com.tecnomotor.thanos.ui.menu.MenuActivity
import kotlinx.android.synthetic.main.fragment_diagnostico.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.coroutines.InternalCoroutinesApi
import java.io.FileNotFoundException

@InternalCoroutinesApi
class DiagnosticoFragment : Fragment() {

    private val selecao = Selecao.getInstance()
    private var favorito: Favorito? = null

    private val viewModel: DiagnosticViewModel by activityViewModels()

    val adapter: DiagnosticoMenuAdapter by lazy { DiagnosticoMenuAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getFavoritoByAplicacao(selecao.aplicacao).observe(this, Observer {
            this.favorito = it?.toFavorito()
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val favoritoIcon = menu.findItem(R.id.menu_diagnostic_favoritos)
        viewModel.favoritoExist(Favorito(selecao)).observe(this, Observer {
            if (it) favoritoIcon.icon = resources.getDrawable(R.drawable.ic_favorites)
            else favoritoIcon.icon = resources.getDrawable(R.drawable.ic_favorites_white_24)
        })
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_diagnostic, menu)
        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_diagnostic_favoritos -> {
                if (favorito != null) removerFavorito(favorito!!)
                else inserirFavorito(Favorito(selecao))
            }
            R.id.menu_diagnostic_new -> {
                Selecao.getInstance().clear()
                val intent = Intent(context, MenuActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Diagnóstico"
        return inflater.inflate(R.layout.fragment_diagnostico, container, false)
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.menuLiveData.observe(viewLifecycleOwner) { menu ->
            menu?.let { menuEntity ->
                if (menu.isNotEmpty()) loading_spinner.visibility = View.INVISIBLE
                adapter.atualiza(menu)
            }
        }
        configuraRecyclerView()
        configuraCabecalho()
        try {
            viewModel.openModulo(selecao.aplicacao.modulo)
            viewModel.insereHistorico(Historico(selecao))
        } catch (e: FileNotFoundException) {
            //Módulo não encontrado
            e.printStackTrace()
            Toast.makeText(
                requireContext(),
                "(${selecao.aplicacao.modulo}) Módulo não encontrado!", Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun configuraCabecalho() {
        tv_montadora.text = selecao.montadora.nome
        tv_modelo.text = selecao.veiculo.nome
        tv_motorizacao.text = selecao.motorizacao.nome
        tv_sistema.text = selecao.sistema.getNomeAno()
    }

    private fun configuraRecyclerView() {
        adapter.itemClicado = this::ItemClicado
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            requireContext(),
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        val rvItens = view?.findViewById<RecyclerView>(R.id.rvdiagnostic)
        rvItens?.layoutManager = lManager
        rvItens?.adapter = adapter
    }

    fun ItemClicado(menu: DiagnosticoMenu) {
        when (menu.id) {
            EnumDiagnosticoMenu.CODIGO_DEFEITO.id -> controlador.navigate(R.id.action_diagnostico_to_codDefeito)
            EnumDiagnosticoMenu.LEITURAS.id -> controlador.navigate(R.id.action_diagnostico_to_leituras)
            EnumDiagnosticoMenu.ANALISE_GRAFICA.id -> controlador.navigate(R.id.action_diagnostico_to_analiseGrafica)
            EnumDiagnosticoMenu.ATUADORES.id -> controlador.navigate(R.id.action_diagnostico_to_atuadores)
            EnumDiagnosticoMenu.IDENTIFICACAO_ECU.id -> controlador.navigate(R.id.action_diagnostico_to_identificacaoEcu)
            EnumDiagnosticoMenu.AJUSTES.id -> controlador.navigate(R.id.action_diagnostico_to_ajustes)
            EnumDiagnosticoMenu.PROGRAMACAO.id -> controlador.navigate(R.id.action_diagnostico_to_programacoes)
        }
    }

    private fun inserirFavorito(favorito: Favorito) {
        viewModel.inserirFavorito(favorito)
        Toast.makeText(requireContext(), "Sistema inserido no favoritos", Toast.LENGTH_SHORT).show()
    }

    private fun removerFavorito(favorito: Favorito) {
        if ((favorito.id > 0)) {
            viewModel.deletarFavorito(favorito)
            Toast.makeText(
                requireContext(),
                "Sistema removido do favoritos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}