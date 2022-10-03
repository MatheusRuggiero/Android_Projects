package br.com.tecnomotor.thanos.ui.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.Selecao
import kotlinx.android.synthetic.main.fragment_posicao_conector.*

class PosicaoConectorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posicao_conector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPosicaoConector()
    }

    private fun loadPosicaoConector() {
        val selecao = Selecao.getInstance()
        try {
            //Exibindo Imagem do conector no veículo
            exibirImagemConectorVeiculo(selecao)
        } catch (e: Exception) {
            println("Exception: $e")
        }
    }

    private fun exibirImagemConectorVeiculo(selecao: Selecao) {
        if (selecao.conector.imgConVeiculo > 0) {
            imgConectorVeiculo.visibility = View.VISIBLE
            imgConectorVeiculo.setImageBitmap(
                selecao.conector.getBitmapConectorNoVeiculo(
                    resources
                )
            )
        } else {
            tvConectorVeiculo.visibility = View.INVISIBLE
            imgConectorVeiculo.visibility = View.INVISIBLE
        }
    }
}
