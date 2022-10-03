package br.com.tecnomotor.thanos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.database.cliente.entity.RelatorioAplicacaoEntity
import br.com.tecnomotor.thanos.model.diagnostico.DiagnosticoMenu
import kotlinx.android.synthetic.main.item_relatorio.view.*
import java.text.SimpleDateFormat

class ListaRelatoriosAdapter(
    private val context: Context,
    private val relatorios: MutableList<RelatorioAplicacaoEntity> = mutableListOf(),
    var itemClicado: (relatorio: RelatorioAplicacaoEntity) -> Unit = {}

) : RecyclerView.Adapter<ListaRelatoriosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada =
            LayoutInflater.from(context).inflate(R.layout.item_relatorio, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val relatorio = relatorios[position]
        holder.vincula(relatorio)
    }

    override fun getItemCount(): Int = relatorios.size

    fun atualiza(relatorios: List<RelatorioAplicacaoEntity>) {
        notifyItemRangeRemoved(0, this.relatorios.size)
        this.relatorios.clear()
        this.relatorios.addAll(relatorios)
        notifyItemRangeInserted(0, this.relatorios.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var relatorio: RelatorioAplicacaoEntity



        init {
            itemView.setOnClickListener {
                if (::relatorio.isInitialized) {
                    itemClicado(relatorio)
                }
            }
        }




        fun vincula(relatorio: RelatorioAplicacaoEntity) {
            this.relatorio = relatorio
            itemView.item_relatorio_placa.text = relatorio.placa
            itemView.item_relatorio_montadora.text = relatorio.montadoraNome
            itemView.item_relatorio_veiculo.text = relatorio.veiculoNome
            itemView.item_relatorio_motorizacao.text = relatorio.motorizacaoNome
            itemView.item_relatorio_sistema.text = relatorio.sistemaNome
            val dateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
            val formatdataHora = dateFormat.format(relatorio.dataHora.time)
            itemView.item_relatorio_data.text = formatdataHora
        }
    }
}