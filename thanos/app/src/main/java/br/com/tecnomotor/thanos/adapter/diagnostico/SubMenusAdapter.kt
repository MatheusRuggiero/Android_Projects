package br.com.tecnomotor.thanos.adapter.diagnostico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.diagnostico.Atuador

class SubMenusAdapter(
    private val context: Context,
    private val atuadores: MutableList<Atuador> = mutableListOf(),
//    var itemClicado: (item: AbstractMenuBase) -> Unit = {}
) : RecyclerView.Adapter<SubMenusAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val atuador = atuadores[position]
        holder.vincula(atuador)
    }

    fun atualiza(atuadores: List<Atuador>) {
        notifyItemRangeRemoved(0, this.atuadores.size)
        this.atuadores.clear()
        this.atuadores.addAll(atuadores)
        notifyItemRangeInserted(0, this.atuadores.size)
    }

    override fun getItemCount() = atuadores.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var atuador: Atuador
        //private val MESGID by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        private val MSG by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        //private val man by lazy { itemView.findViewById<TextView>(R.id.tvNome) }



        fun vincula(atuador: Atuador) {
            this.atuador = atuador
           // MESGID.text = atuador.MESGID.toString()
            MSG.text = atuador.MSG
           // enable.text = atuador.enable.toString()
        }

    }


}
