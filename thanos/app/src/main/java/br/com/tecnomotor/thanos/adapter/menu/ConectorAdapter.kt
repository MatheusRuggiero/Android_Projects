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
import br.com.tecnomotor.thanos.model.menu.Conector

class ConectorAdapter(
    private val context: Context,
    private val conectores: MutableList<Conector> = mutableListOf(),
    var itemClicado: (conector: Conector) -> Unit = {}
) : RecyclerView.Adapter<ConectorAdapter.ViewHolder>() {

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

    override fun getItemCount() = conectores.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conector = conectores[position]
        holder.vincula(conector)
    }

    fun atualiza(conectores: List<Conector>) {
        this.conectores.clear()
        this.conectores.addAll(conectores)
        this.notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var conector: Conector
        private val itemMenu by lazy { itemView.findViewById<LinearLayout>(R.id.llItemMenu) }
        private val nome by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        private val info by lazy { itemView.findViewById<TextView>(R.id.tvInfo) }
        private val image by lazy { itemView.findViewById<ImageView>(R.id.img_next) }

        init {
            itemView.setOnClickListener {
                if (::conector.isInitialized) {
                    itemClicado(conector)
                }
            }
        }

        @SuppressLint("ResourceAsColor")
        fun vincula(conector: Conector) {
            this.conector = conector
            nome.text = conector.nome

            if (conector.versaoOkMontadoraOk()) {
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