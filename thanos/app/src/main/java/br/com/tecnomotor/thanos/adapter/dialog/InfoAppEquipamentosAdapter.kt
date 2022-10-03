package br.com.tecnomotor.thanos.adapter.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.menu.Montadora

class InfoAppEquipamentosAdapter(
    private val context: Context,
    private val montadoras: MutableList<Montadora>,
) : RecyclerView.Adapter<InfoAppEquipamentosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.item_app_equipamento,
                parent, false
            )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = montadoras.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = montadoras[position]
        holder.vincula(data)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(dataList: List<Montadora>) {
        this.montadoras.clear()
        this.montadoras.addAll(dataList)
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var montadora: Montadora
        private val nomeMontadora by lazy { itemView.findViewById<TextView>(R.id.item_app_equipamento_nome) }

        fun vincula(data: Montadora) {
            this.montadora = data
            nomeMontadora.text = data.nome
        }
    }
}