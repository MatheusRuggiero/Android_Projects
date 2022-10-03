package br.com.tecnomotor.thanos.adapter.diagnostico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.diagnostico.CodigoDefeito

class CodDefeitoAdapter (
    private val context: Context,
    private var codigoDefeitos: MutableList<CodigoDefeito> = mutableListOf(),
    var itemClicado: (dtc: CodigoDefeito) -> Unit = {}
) : RecyclerView.Adapter<CodDefeitoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.item_cod_defeitos,
                parent, false
            )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = codigoDefeitos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dtc = codigoDefeitos[position]
        holder.vincula(dtc)
    }


    fun atualiza(codigoDefeitos: List<CodigoDefeito>) {
        notifyItemRangeChanged(0, this.codigoDefeitos.size)
        this.codigoDefeitos.clear()
        this.codigoDefeitos.addAll(codigoDefeitos)
        notifyItemRangeInserted(0, this.codigoDefeitos.size)
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var dtc: CodigoDefeito
        private val codigo by lazy { itemView.findViewById<TextView>(R.id.tv_codigo) }
        private val sintoma by lazy { itemView.findViewById<TextView>(R.id.tv_sintoma) }
        private val status by lazy { itemView.findViewById<TextView>(R.id.tv_status) }


        init {
            itemView.setOnClickListener {
                if (::dtc.isInitialized) {
                    itemClicado(dtc)
                }
            }
        }


        fun vincula(dtc: CodigoDefeito) {
            this.dtc = dtc
            codigo.text = dtc.nome
            sintoma.text = dtc.sintoma
            status.text = dtc.status
        }

    }
}