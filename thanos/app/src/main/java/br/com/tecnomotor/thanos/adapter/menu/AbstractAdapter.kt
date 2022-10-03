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
import br.com.tecnomotor.thanos.model.menu.AbstractMenuBase

class AbstractAdapter(
    private val context: Context,
    private val dataList: MutableList<AbstractMenuBase> = mutableListOf(),
    var itemClicado: (item: AbstractMenuBase) -> Unit = {}
) : RecyclerView.Adapter<AbstractAdapter.ViewHolder>() {

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

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.vincula(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(dataList: List<AbstractMenuBase>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var data: AbstractMenuBase
        private val itemMenu by lazy { itemView.findViewById<LinearLayout>(R.id.item_card_view) }
        private val nome by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        private val info by lazy { itemView.findViewById<TextView>(R.id.tvInfo) }
        private val image by lazy { itemView.findViewById<ImageView>(R.id.img_next) }

        init {
            itemView.setOnClickListener {
                if (::data.isInitialized) {
                    itemClicado(data)
                }
            }
        }

        @SuppressLint("ResourceAsColor")
        fun vincula(data: AbstractMenuBase) {
            this.data = data
            nome.text = data.nome

            /* Quando o item estiver desativado */
            if (data.versaoOkMontadoraOk()) {
                nome.setTextColor(context.resources.getColor(R.color.text_card_primary, context.theme))
                itemMenu.setBackgroundResource(R.drawable.card_default)
            } else {
                nome.setTextColor(
                    context.resources.getColor(
                        R.color.text_card_disabled,
                        context.theme
                    )
                )
                itemMenu.setBackgroundResource(R.drawable.card_disabled)
            }
        }
    }
}