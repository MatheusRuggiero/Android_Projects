package br.com.tecnomotor.thanos.adapter.diagnostico

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.diagnostico.Leitura
import com.google.android.material.card.MaterialCardView

class LeiturasAdapter(
    private val context: Context,
    private var leituras: MutableList<Leitura> = mutableListOf(),
    var itemClicado: (leitura: Leitura) -> Unit = {},
) : RecyclerView.Adapter<LeiturasAdapter.ViewHolder>() {

    private lateinit var leiturasliveData: MediatorLiveData<List<Leitura>>
    private var leiturasSelecionadas: MutableList<Leitura> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.item_leituras,
                parent, false
            )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = leituras.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val leitura = leituras[position]
        holder.vincula(leitura)
    }


    fun atualiza(leiturasLiveData: MediatorLiveData<List<Leitura>>) {
        notifyItemRangeRemoved(0, this.leituras.size)
        this.leiturasliveData = leiturasLiveData
        this.leituras.clear()
        this.leituras.addAll(leiturasLiveData.value!!)
        notifyItemRangeInserted(0, this.leituras.size)
    }

    fun getLeiturasSelecionadas(): MutableList<Leitura> {
        return leiturasSelecionadas
    }

    fun selectTodasLeituras() {
        leiturasSelecionadas.clear()
        leiturasSelecionadas.addAll(leituras)
        checkAllView(true)
        atualizaObservers()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun descartarSelecao() {
        if (leiturasSelecionadas.size > 0) {
            leiturasSelecionadas.clear()
            checkAllView(false)
            atualizaObservers()
            notifyDataSetChanged()
        }
    }

    private fun checkAllView(valor: Boolean) {
        for (l in leituras) {
            l.checked = valor
        }
    }

    fun atualizaObservers(): Boolean {
        leiturasliveData.postValue(leiturasliveData.value)
        return true
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val defaultValue = "---";
        private lateinit var leitura: Leitura
        private val cardLeitura by lazy { itemView.findViewById<MaterialCardView>(R.id.leituras_card_view) }
        private val nome by lazy { itemView.findViewById<TextView>(R.id.tv_leituras) }
        private val valor by lazy { itemView.findViewById<TextView>(R.id.tv_valor) }
        private val unidade by lazy { itemView.findViewById<TextView>(R.id.tv_unidade) }

        init {
            itemView.setOnClickListener {
                if (::leitura.isInitialized && leiturasSelecionadas.size > 0) {
//                    itemClicado(leitura)
                    checkItem()
                }
            }

            cardLeitura.setOnLongClickListener {
                checkItem()
            }
        }

        private fun checkItem(): Boolean {
            cardLeitura.isChecked = !cardLeitura.isChecked
            if (leiturasSelecionadas.contains(leituras[adapterPosition])) {
                leitura.checked = false
                leiturasSelecionadas.remove(leituras[adapterPosition])
            } else {
                leitura.checked = true
                leiturasSelecionadas.add(leituras[adapterPosition])
            }
            itemClicado(leitura)
            return true
        }


        fun vincula(leitura: Leitura) {
            this.leitura = leitura
            nome.text = leitura.nome
            valor.text =
                if (leitura.valor == Float.MIN_VALUE) defaultValue else leitura.valor.toString()
            unidade.text = leitura.unidade.nome
            cardLeitura.isChecked = leitura.checked
        }
    }
}