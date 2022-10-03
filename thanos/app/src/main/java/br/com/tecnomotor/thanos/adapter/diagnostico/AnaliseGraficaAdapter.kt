package br.com.tecnomotor.thanos.adapter.diagnostico

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.diagnostico.AnaliseGrafica
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.roundToInt

class AnaliseGraficaAdapter(
    private val context: Context,
    private var analisesGraficas: MutableList<AnaliseGrafica> = mutableListOf(),
    var itemClicado: (analiseGrafica: AnaliseGrafica) -> Unit = {}

): RecyclerView.Adapter<AnaliseGraficaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.item_analise_grafica,
                parent, false
            )
        return ViewHolder(viewCriada)

    }

    override fun getItemCount() = analisesGraficas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val analiseGrafica = analisesGraficas[position]
        holder.vincula(analiseGrafica)
    }

    fun atualiza(analisesGraficas: List<AnaliseGrafica>) {
        notifyItemRangeChanged(0, this.analisesGraficas.size)
        this.analisesGraficas.clear()
        this.analisesGraficas.addAll(analisesGraficas)
        notifyItemRangeInserted(0, this.analisesGraficas.size)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var analiseGrafica: AnaliseGrafica
        private val defaultValue = "---";
        private val leitura by lazy { itemView.findViewById<TextView>(R.id.tv_leituras) }
        private val valor by lazy { itemView.findViewById<TextView>(R.id.tv_valor) }
        private val unidades by lazy { itemView.findViewById<TextView>(R.id.tv_unidade) }
        private val valorMin by lazy { itemView.findViewById<TextView>(R.id.tv_unidade1) }
        private val valorMax by lazy { itemView.findViewById<TextView>(R.id.tv_unidade2) }
        private val progressBar by lazy { itemView.findViewById<ProgressBar>(R.id.progressBar) }

        private val decimalFormat = DecimalFormat()
        private val otherSymbols =  DecimalFormatSymbols()


        init {

            otherSymbols.decimalSeparator = ','
            otherSymbols.groupingSeparator = '.'
            decimalFormat.decimalFormatSymbols = otherSymbols
            decimalFormat.isGroupingUsed =  false

            itemView.setOnClickListener {
                if (::analiseGrafica.isInitialized) {
                    itemClicado(analiseGrafica)
                }
            }
        }

        fun vincula(analiseGrafica: AnaliseGrafica) {

            val decimalFormat = DecimalFormat("0.00")

            decimalFormat.maximumFractionDigits = analiseGrafica.dp
            decimalFormat.minimumFractionDigits = analiseGrafica.dp

            this.analiseGrafica = analiseGrafica
            leitura.text = analiseGrafica.nomeAnalise
            valor.text = decimalFormat.format(analiseGrafica.valor)
            valorMin.text = decimalFormat.format(analiseGrafica.valorMin)
           valorMax.text =decimalFormat.format(analiseGrafica.valorMax)
            unidades.text = analiseGrafica.unidade.nome

            var difGraph: Double = (analiseGrafica.valorMax -analiseGrafica.valorMin) / 2
            val minGraph: Double = analiseGrafica.valorMin - difGraph
            val maxGraph: Double = analiseGrafica.valorMax + difGraph

            difGraph = maxGraph - minGraph


            val valorBarGraph = (((analiseGrafica.valor.toDouble() - minGraph) * 100) / difGraph).roundToInt()

            Log.d(this.javaClass.simpleName, "min / Valor / Max: $minGraph / ${analiseGrafica.valor} / $maxGraph")

            progressBar.progress = valorBarGraph

//        @SuppressLint("ResourceAsColor")
//        fun vincula() {
//
//            /* Quando o item estiver desativado */
//            if (() && ())
//                itemMenu.setBackgroundColor(android.R.drawable.btn_default)
//            else
//                itemMenu.setBackgroundColor(R.color.button_grey_light)
//
//        }
        }
    }
}