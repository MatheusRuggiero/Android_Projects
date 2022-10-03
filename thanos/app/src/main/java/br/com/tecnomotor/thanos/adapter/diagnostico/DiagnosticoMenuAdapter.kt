package br.com.tecnomotor.thanos.adapter.diagnostico

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.diagnostico.DiagnosticoMenu

class DiagnosticoMenuAdapter (
    private val context: Context,
    private var menus: MutableList<DiagnosticoMenu> = mutableListOf(),
    var itemClicado: (menu: DiagnosticoMenu) -> Unit = {}
) : RecyclerView.Adapter<DiagnosticoMenuAdapter.ViewHolder>() {

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

    override fun getItemCount() = menus.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = menus[position]
        holder.vincula(menu)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(menus: List<DiagnosticoMenu>) {
        this.menus.clear()
        this.menus.addAll(menus)
        this.notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var menu: DiagnosticoMenu
        private val nome by lazy { itemView.findViewById<TextView>(R.id.tvNome) }

        init {
            itemView.setOnClickListener {
                if (::menu.isInitialized) {
                    itemClicado(menu)
                }
            }
        }

        fun vincula(menu: DiagnosticoMenu) {
            this.menu = menu
            nome.text = menu.nome
        }
    }
}