package br.com.tecnomotor.thanos.adapter.menu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.menu.Sistema

class SistemaAdapter(
    private val context: Context,
    private val sistemas: MutableList<Sistema> = mutableListOf(),
    var itemClicado: (sistema: Sistema) -> Unit = {}
) : RecyclerView.Adapter<SistemaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.item,
                parent, false
            )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = sistemas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sistema = sistemas[position]
        holder.vincula(sistema)
    }

    fun atualiza(sistemas: List<Sistema>) {
        this.sistemas.clear()
        this.sistemas.addAll(sistemas)
        this.notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var sistema: Sistema
        private val itemMenu by lazy { itemView.findViewById<LinearLayout>(R.id.llItemMenu) }
        private val nome by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        private val info by lazy { itemView.findViewById<TextView>(R.id.tvInfo) }
        private val image by lazy { itemView.findViewById<ImageView>(R.id.img_next) }

        init {
            itemView.setOnClickListener {
                if (::sistema.isInitialized) {
                    itemClicado(sistema)
                }
            }
        }

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun vincula(sistema: Sistema) {
            this.sistema = sistema
            nome.text = sistema.getNomeAno()

            /* Quando o item estiver desativado */
            if (sistema.versaoOkMontadoraOk()) {
                nome.setTextColor(context.resources.getColor(R.color.text_card_primary, context.theme))
                itemView.setBackgroundResource(R.drawable.card_default)
            } else {
                nome.setTextColor(
                    context.resources.getColor(
                        R.color.text_card_disabled,
                        context.theme
                    )
                )
                itemView.setBackgroundResource(R.drawable.card_disabled)
            }
        }
    }
}