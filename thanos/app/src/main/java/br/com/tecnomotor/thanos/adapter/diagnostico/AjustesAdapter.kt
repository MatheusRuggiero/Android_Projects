package br.com.tecnomotor.thanos.adapter.diagnostico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.diagnostico.Ajuste

class AjustesAdapter (
    private val context: Context,
    private val ajustes: MutableList<Ajuste> = mutableListOf(),
//    var itemClicado: (item: AbstractMenuBase) -> Unit = {}
) : RecyclerView.Adapter<AjustesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ajuste = ajustes[position]
        holder.vincula(ajuste)
    }

    fun atualiza(ajustes: List<Ajuste>) {
        notifyItemRangeRemoved(0, this.ajustes.size)
        this.ajustes.clear()
        this.ajustes.addAll(ajustes)
        notifyItemRangeInserted(0, this.ajustes.size)
    }

    override fun getItemCount() = ajustes.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var ajuste: Ajuste
        //private val MESGID by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        private val MSG by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        //private val man by lazy { itemView.findViewById<TextView>(R.id.tvNome) }



        fun vincula(ajuste: Ajuste) {
            this.ajuste = ajuste
            // MESGID.text = atuador.MESGID.toString()
            MSG.text = ajuste.MSG
            // enable.text = atuador.enable.toString()
        }

    }


}