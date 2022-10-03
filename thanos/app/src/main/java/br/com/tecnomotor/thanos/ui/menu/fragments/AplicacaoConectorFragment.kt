package br.com.tecnomotor.thanos.ui.menu.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.menu.PosicaoConectorAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.menu.toAplicacao
import br.com.tecnomotor.thanos.ui.diagnostico.DiagnosticActivity
import br.com.tecnomotor.thanos.ui.menu.viewmodel.AplicacaoConectorViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_exibir_posicao_conectores.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class AplicacaoConectorFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[AplicacaoConectorViewModel::class.java]
    }

    private val controlador by lazy {
        findNavController()
    }

    private val selecao = Selecao.getInstance()

    private lateinit var tablayout: TabLayout
    private lateinit var viewpager: ViewPager2
    private var tabTitle = arrayOf("Posição", "Conector", "Posição Conector")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaPosicaoConector()
    }

    private fun buscaPosicaoConector() {
        val selecao = Selecao.getInstance()
        viewModel.getAplicacao(
            selecao.plataforma, selecao.versao, selecao.montadora, selecao.veiculo,
            selecao.motorizacao, selecao.tipoDeSistema, selecao.sistema, selecao.conector
        ).observe(this) { aplicacao ->
            selecao.setAplicacao(aplicacao.toAplicacao())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = getString(R.string.posicao_conector_title)
        val view = inflater.inflate(R.layout.fragment_exibir_posicao_conectores, container, false)
        inicializaTablayout(view)
        return view
    }

    private fun inicializaTablayout(view: View) {
        viewpager = view.findViewById(R.id.viewpager_posicao)
        tablayout = view.findViewById(R.id.tablayout_posicao)
        viewpager.adapter = PosicaoConectorAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraCabecalho()
        loadButton()
    }

    /**
     * Carrega as informacoes no titulo e as imagens da posicao e do conector
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun loadButton() {
        val selecao = Selecao.getInstance()
        btnStartDiagnostic.setOnClickListener {
            if (selecao.conector.montHabilitada && selecao.conector.estaNaVersao) {
                val intent = Intent(context, DiagnosticActivity::class.java)
                startActivity(intent)
            } else {
                val direcao = AplicacaoConectorFragmentDirections.actionPosicaoConectorToCompra()
                controlador.navigate(direcao)
            }
        }
    }

    private fun configuraCabecalho() {
        tv_montadora.text = selecao.montadora.nome
        tv_modelo.text = selecao.veiculo.nome
        tv_motorizacao.text = selecao.motorizacao.nome
        tv_sistema.text = selecao.sistema.getNomeAno()
    }

}
