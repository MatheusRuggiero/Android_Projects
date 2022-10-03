package br.com.tecnomotor.thanos.ui.home.fragments

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.language.BaseFragment
import br.com.tecnomotor.thanos.language.LanguageResource
import br.com.tecnomotor.thanos.language.LanguageResource.AppLanguagesSettings.DEFAULT_ENGLISH
import br.com.tecnomotor.thanos.language.LanguageResource.AppLanguagesSettings.DEFAULT_PORTUGUESE
import br.com.tecnomotor.thanos.language.LanguageResource.AppLanguagesSettings.SPANISH
import br.com.tecnomotor.thanos.util.animateTranslation
import kotlinx.android.synthetic.main.fragment_configuracoes.*

class ConfiguracoesFragment : BaseFragment() {

    private val controlador by lazy {
        findNavController()
    }


    private var loadingSpinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun showActionBarOptionMenu() = true

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_configuracoes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.language_en ->{
                setLanguage(DEFAULT_ENGLISH)
                return true
            }
            R.id.language_es ->{
                setLanguage(SPANISH)
                return true
            }
            R.id.language_pt ->{
                setLanguage(DEFAULT_PORTUGUESE)
                return true
            }
            else -> Unit
        }
        return super.onOptionsItemSelected(item)
    }

    override fun translateUi(resource: LanguageResource) {
        animateTranslationText(resource)
    }

    private fun animateTranslationText(resource: LanguageResource){
        configuracoes_dados_oficina.animateTranslation(resource.getLangString("R.string.dados_da_oficina"))
        configuracoes_razao_social.animateTranslation(resource.getLangString("R.string.razao_social"))
        configuracoes_cnpj.animateTranslation(resource.getLangString("R.string.cnpj"))
        configuracoes_tel.animateTranslation(resource.getLangString("R.string.phone"))
        btnAtualizarDadosOficina.animateTranslation(resource.getLangString("R.string.atualizar"))
        configuracoes_bluetooth.animateTranslation(resource.getLangString("R.string.dispositivo_conectado"))
        btnParearBluetooth.animateTranslation(resource.getLangString("R.string.parear_por_bluetooth"))
        configuracoes_atualizacao.animateTranslation(resource.getLangString("R.string.atualizacao_da_vci"))
        configuracoes_switch_atualizar.animateTranslation(resource.getLangString("R.string.notificar_sobre_atualizacoes"))
        configuracoes_ultima_atualizacao.animateTranslation(resource.getLangString("R.string.ultima_atualizacao"))
        configuracoes_btn_atualizar_agora.animateTranslation(resource.getLangString("R.string.atualizar_agora"))
        configuracoes_relatorio.animateTranslation(resource.getLangString("R.string.relatorio"))
        configuracoes_switch_exibir_dados_empresa.animateTranslation(resource.getLangString("R.string.exibir_dados_empresa"))
        configuracoes_switch_exibir_dados_qtd_pagina.animateTranslation(resource.getLangString("R.string.exibir_numero_pagina"))
        configuracoes_switch_exibir_data_hora_impressao.animateTranslation(resource.getLangString("R.string.exibir_data"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.title = "Configurações"
        return inflater.inflate(R.layout.fragment_configuracoes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configButtons()
    }

//    fun selecionarIdioma(linguagem: String) {
//        val localidade by lazy {
//            Locale(linguagem)
//        }
//        Locale.setDefault(localidade)
//        val resources:
//        val configuration
//    }

    private fun configButtons() {
        btnAtualizarDadosOficina.setOnClickListener {
            val directions =
                ConfiguracoesFragmentDirections.actionConfiguracoesFragmentToFragmentCarWorkshopInformation()
            controlador.navigate(directions)
        }

        btnParearBluetooth.setOnClickListener {
            val directions =
                ConfiguracoesFragmentDirections.actionConfiguracoesFragmentToConnectDeviceFragment()
            controlador.navigate(directions)
        }
    }
}