package br.com.tecnomotor.thanos.adapter.diagnostico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.diagnostico.IdentificacaoEcu
import com.google.android.material.card.MaterialCardView
import java.util.ArrayList

class IdentificacaoEcuAdapter(
    private val context: Context,
    private val listaEcu: MutableList<IdentificacaoEcu> = mutableListOf(),
    var itemClicado: (identificacaoEcuEntity: IdentificacaoEcu) -> Unit = {}

) : RecyclerView.Adapter<IdentificacaoEcuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.item_ecu,
                parent, false
            )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = listaEcu.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val identificacaoECU = listaEcu[position]
        holder.vincula(identificacaoECU)
    }

    fun atualiza(ecuList: ArrayList<IdentificacaoEcu>) {
        notifyItemRangeRemoved(0, this.listaEcu.size)
        this.listaEcu.clear()
        this.listaEcu.addAll(ecuList)
        notifyItemRangeInserted(0, this.listaEcu.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private lateinit var identificacaoEcuEntity: IdentificacaoEcu
        private val cardEcu by lazy { itemView.findViewById<MaterialCardView>(R.id.item_ecu_card_view) }
        private val codigo by lazy { itemView.findViewById<TextView>(R.id.item_ecu_tv_codigo) }
        private val sintoma by lazy { itemView.findViewById<TextView>(R.id.item_ecu_tv_sintoma) }


        init {
            itemView.setOnClickListener {
                if (::identificacaoEcuEntity.isInitialized) {
                    itemClicado(identificacaoEcuEntity)
                }
            }
        }

        fun vincula(identificacaoEcu: IdentificacaoEcu) {
            this.identificacaoEcuEntity = identificacaoEcu
            codigo.text = identificacaoEcu.nome
            sintoma.text = identificacaoEcu.valor

            //  Quando o item estiver desativado
//            if (() && ())
//                itemMenu.setBackgroundColor(android.R.drawable.btn_default)
//            else
//                itemMenu.setBackgroundColor(R.color.button_grey_light)
        }

    }
}