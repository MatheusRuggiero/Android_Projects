package br.com.tecnomotor.thanos.ui.menu.fragments

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.menu.AbstractAdapter
import br.com.tecnomotor.thanos.config.ConfigDevice
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.cliente.loadHistorico
import br.com.tecnomotor.thanos.model.menu.AbstractMenuBase
import br.com.tecnomotor.thanos.model.menu.Montadora
import br.com.tecnomotor.thanos.model.menu.toMontadora
import br.com.tecnomotor.thanos.ui.menu.viewmodel.MontadoraViewModel
import kotlinx.android.synthetic.main.fragment_montadoras.*
import kotlinx.coroutines.DelicateCoroutinesApi


class MontadorasFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MontadoraViewModel::class.java]
    }

    val adapter: AbstractAdapter by lazy { AbstractAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        buscaMontadoras()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun buscaMontadoras() {
        val selecao = Selecao.getInstance()
        val listIdMontadoraHabilitada = ConfigDevice.getInstance().getListIdMontadoraHabilitada()
        viewModel.getMontadoras(
            selecao.plataforma,
            selecao.versao
        ).observe(this, Observer { montadorasEntity ->
            val montadoras = mutableListOf<Montadora>()
            montadorasEntity.forEach { montadoraEntity ->
                val montadora = montadoraEntity.toMontadora()

                viewModel.getVersaoByMontadora(selecao.plataforma, montadora)
                    .observe(this, Observer { versaoEntity ->
                        montadora.versaoBd = versaoEntity?.id ?: 0
                        montadora.estaNaVersao = (((versaoEntity?.id ?: 0) <= selecao.versao.id))
                        montadora.montHabilitada = listIdMontadoraHabilitada.contains(montadora.id)
                        montaMontadoras(montadoras)
                    })
                montadoras.add(montadora)

            }

        })
    }

    private fun montaMontadoras(montadoras: MutableList<Montadora>) {
        if ((montadoras.size == 1) && (montadoras[0].versaoOkMontadoraOk())) avancoAutomatico(
            montadoras[0]
        )
        else {
            val montadorasHabilitadas = arrayListOf<Montadora>()
            val montadorasDesabilitadas = arrayListOf<Montadora>()
            val listaOrdenadaMontadadoras = arrayListOf<Montadora>()
            montadoras.map { montadora ->
                if (montadora.versaoOkMontadoraOk()) {
                    montadorasHabilitadas.add(montadora)
                } else {
                    montadorasDesabilitadas.add(montadora)
                }
            }

            listaOrdenadaMontadadoras.addAll(montadorasHabilitadas)
            listaOrdenadaMontadadoras.addAll(montadorasDesabilitadas)

            adapter.atualiza(listaOrdenadaMontadadoras)
            loading_spinner.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.montadoras_title)
        return inflater.inflate(R.layout.fragment_montadoras, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adapter.itemCount > 0) loading_spinner.visibility = View.INVISIBLE
        configuraRecyclerView()
    }

    private fun configuraRecyclerView() {
        adapter.itemClicado = this::itemClicado
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            view?.context,
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        rvListMontadoras.layoutManager = lManager
        rvListMontadoras.adapter = adapter
    }

    private fun avancoAutomatico(montadora: Montadora) {
        itemClicado(montadora)
    }

    fun itemClicado(data: AbstractMenuBase) {
        Selecao.getInstance().setMontadora(data as Montadora)
        val direcao = Directions.getVeiculos()
        controlador.navigate(direcao)
    }

    fun ultimoEscolha() {
        val selecao = Selecao.getInstance()
        viewModel.getLasHistory().observe(this) { historico ->
            selecao.loadHistorico(historico)
            val direcao = MontadorasFragmentDirections.actionMontadorasToPosicaoConector()
            Navigation.findNavController(requireView()).navigate(direcao)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.activity_montadoras, menu)
        val ultimoHistorico: MenuItem? = menu.findItem(R.id.menu_icon_ultima_escolha)
        val botaoHistorico: MenuItem? = menu.findItem(R.id.menu_icon_historico)
        val botaoFavorito: MenuItem? = menu.findItem(R.id.menu_diagnostic_favoritos)

        viewModel.qtdHistoricos().observe(this) { qtdHistoricos ->
            if (ultimoHistorico != null) {
                ultimoHistorico.isVisible = qtdHistoricos > 0
            }
            if (botaoHistorico != null) {
                botaoHistorico.isVisible = qtdHistoricos > 0
            }
        }

        viewModel.qtdFavoritos().observe(this) { qtdFavoritos ->
            if (botaoFavorito != null) {
                botaoFavorito.isVisible = qtdFavoritos > 0
            }
        }

        return super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.menu_diagnostic_favoritos -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_montadoras_to_favoritos)
            }
            R.id.menu_icon_historico -> {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_montadoras_to_historico)
            }
            R.id.menu_icon_ultima_escolha -> {
                ultimoEscolha()
            }
        }
        return super.onOptionsItemSelected(item);
    }

}