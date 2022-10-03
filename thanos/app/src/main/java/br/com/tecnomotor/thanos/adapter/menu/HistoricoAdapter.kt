package br.com.tecnomotor.thanos.adapter.menu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.cliente.Historico
import java.text.SimpleDateFormat
import java.util.*

class HistoricoAdapter (
    private val context: Context,
    private var historicoList: MutableList<Historico> = mutableListOf(),
    var itemClicado: (historico: Historico) -> Unit = {}
) : RecyclerView.Adapter<HistoricoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.item_historico,
                parent, false
            )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = historicoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historico = historicoList[position]
        holder.vincula(historico)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(historicoList: List<Historico>) {
        this.historicoList.clear()
        this.historicoList.addAll(historicoList)
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var historico: Historico
        private val montadora by lazy { itemView.findViewById<TextView>(R.id.tv_historico_montadora) }
        private val modelo by lazy { itemView.findViewById<TextView>(R.id.tv_historico_modelo) }
        private val separador by lazy { itemView.findViewById<TextView>(R.id.tv_historico_separador) }
        private val motorizacao by lazy { itemView.findViewById<TextView>(R.id.tv_historico_motorizacao) }
        private val sistema by lazy { itemView.findViewById<TextView>(R.id.tv_historico_sistema) }
        private val ano by lazy { itemView.findViewById<TextView>(R.id.tv_historico_ano) }
        private val conector by lazy { itemView.findViewById<TextView>(R.id.tv_historico_conector) }
        private val data by lazy { itemView.findViewById<TextView>(R.id.tv_historico_data) }

        init {
            itemView.setOnClickListener {
                if (::historico.isInitialized) {
                    itemClicado(historico)
                }
            }
        }

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun vincula(historico: Historico) {
            this.historico = historico
            montadora.text = historico.montadoraNome
            modelo.text = historico.veiculoNome
            motorizacao.text = historico.motorizacaoNome
            separador.visibility = if (historico.motorizacaoId > 0) View.VISIBLE else View.INVISIBLE
            sistema.text = historico.sistemaNome
            val anoAtual = Calendar.getInstance().get(Calendar.YEAR)
            val anoInicial = if (historico.anoInicial > 0) historico.anoInicial else "*"
            val anoFinal = if (historico.anoFinal > 0) historico.anoFinal else anoAtual
            if ((historico.anoInicial > 0) || historico.anoFinal > 0)
                ano.text = "($anoInicial - $anoFinal)"
            else ano.text = ""
            conector.text = historico.conectorNome
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
            data.text = dateFormat.format(historico.data)
        }
    }

    fun buscaObjeto(position : Int) : Historico{
        return historicoList[position]
    }
}