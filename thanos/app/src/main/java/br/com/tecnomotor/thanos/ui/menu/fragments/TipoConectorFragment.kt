package br.com.tecnomotor.thanos.ui.menu.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.Selecao
import kotlinx.android.synthetic.main.fragment_tipo_conector.*

class TipoConectorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tipo_conector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadConector()
    }

    private fun loadConector() {
        val selecao = Selecao.getInstance()
        try {
            exibirTipoConector(selecao)
            exibirImagemConector(selecao)
        } catch (e: Exception) {
            println("Exception: $e")
        }
    }

    private fun exibirImagemConector(selecao: Selecao) {
        // Imagem da ilustracao do conector
        imgConector?.visibility = View.VISIBLE
        imgConector?.setImageBitmap(selecao.conector.getBitmapConector(resources))
    }

    @SuppressLint("SetTextI18n")
    private fun exibirTipoConector(selecao: Selecao) {
        //Exibindo o Titulo do Conector
        tvConector?.text = resources.getString(R.string.connector) + " " + selecao.conector.nome
    }
}