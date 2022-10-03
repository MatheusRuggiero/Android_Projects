package br.com.tecnomotor.thanos.ui.menu.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.Selecao
import kotlinx.android.synthetic.main.fragment_posicao.*
import java.io.IOException

class PosicaoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posicao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPosicaoGrid()
    }


    @SuppressLint("SetTextI18n")
    fun loadPosicaoGrid() {
        val selecao = Selecao.getInstance()
        try {
            // Titulo: Posicoes = "D2 / H6"
            if (selecao.conector.posConector.isEmpty()) {
                tvPosicao?.visibility = View.GONE
                imgGridVeiculo?.visibility = View.GONE
            } else {
                tvPosicao?.text =
                    resources.getString(R.string.position) + " " + selecao.conector.posConector

                val gridImgPath = selecao.conector.getGridImagePath()

                // Carrega o bitmap do asset para a view. Imagem da matriz de posicoes
                val gridImgBitmap: Bitmap
                try {
                    gridImgBitmap = BitmapFactory.decodeStream(
                        resources
                            .assets.open(gridImgPath.first)
                    )
                    imgGridVeiculo?.visibility = View.VISIBLE
                    imgGridVeiculo?.setImageBitmap(gridImgBitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                // Imagem de todas as posicao do conector
                val idPositionImg = intArrayOf(
                    R.id.img_position_1,
                    R.id.img_position_2, R.id.img_position_3, R.id.img_position_4,
                    R.id.img_position_5
                )
                val posicoesVetor = selecao.conector.getPosicoesConector()
                for (i in posicoesVetor.indices) {
                    if (i > 5) break
                    val connectorPositionImg = view?.findViewById(idPositionImg[i]) as ImageView
                    var connectorPositionBitmap: Bitmap?

                    try {
                        connectorPositionBitmap = BitmapFactory
                            .decodeStream(
                                resources.assets.open(
                                    "Positions/" + gridImgPath.second + posicoesVetor[i] + ".png"
                                )
                            )

                        connectorPositionImg.visibility = View.VISIBLE
                        connectorPositionImg.setImageBitmap(connectorPositionBitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            println("Exception: $e")
        }
    }
}