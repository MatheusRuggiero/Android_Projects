package br.com.tecnomotor.thanos.adapter.diagnostico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.diagnostico.Programacao

class ProgramacoesAdapter  (
    private val context: Context,
    private val programacoes: MutableList<Programacao> = mutableListOf(),
//    var itemClicado: (item: AbstractMenuBase) -> Unit = {}
) : RecyclerView.Adapter<ProgramacoesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val programacao = programacoes[position]
        holder.vincula(programacao)
    }

    fun atualiza(programacoes: List<Programacao>) {
        notifyItemRangeRemoved(0, this.programacoes.size)
        this.programacoes.clear()
        this.programacoes.addAll(programacoes)
        notifyItemRangeInserted(0, this.programacoes.size)
    }

    override fun getItemCount() = programacoes.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var programacoes: Programacao
        //private val MESGID by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        private val MSG by lazy { itemView.findViewById<TextView>(R.id.tvNome) }
        //private val man by lazy { itemView.findViewById<TextView>(R.id.tvNome) }



        fun vincula(programacoes: Programacao) {
            this.programacoes = programacoes
            // MESGID.text = atuador.MESGID.toString()
             MSG.text = programacoes.MSG
            // enable.text = atuador.enable.toString()
        }

    }


}