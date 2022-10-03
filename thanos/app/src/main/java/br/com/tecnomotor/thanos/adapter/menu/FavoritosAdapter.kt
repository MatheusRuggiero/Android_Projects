package br.com.tecnomotor.thanos.adapter.menu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.cliente.Favorito
import java.text.SimpleDateFormat
import java.util.*

class FavoritosAdapter(
    private val context: Context,
    private var favoritoList: MutableList<Favorito> = mutableListOf(),
    var itemClicado: (favorito: Favorito) -> Unit = {}
) : RecyclerView.Adapter<FavoritosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(
                R.layout.item_favoritos,
                parent, false
            )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = favoritoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorito = favoritoList[position]
        holder.vincula(favorito)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(favoritoList: List<Favorito>) {
        this.favoritoList.clear()
        this.favoritoList.addAll(favoritoList)
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var favorito: Favorito
        private val montadora by lazy { itemView.findViewById<TextView>(R.id.tv_favorito_montadora) }
        private val modelo by lazy { itemView.findViewById<TextView>(R.id.tv_favorito_modelo) }
        private val separador by lazy { itemView.findViewById<TextView>(R.id.tv_favorito_separador) }
        private val motorizacao by lazy { itemView.findViewById<TextView>(R.id.tv_favorito_motorizacao) }
        private val sistema by lazy { itemView.findViewById<TextView>(R.id.tv_favorito_sistema) }
        private val ano by lazy {itemView.findViewById<TextView>(R.id.tv_favorito_ano) }
        private val conector by lazy { itemView.findViewById<TextView>(R.id.tv_favorito_conector) }
        private val data by lazy { itemView.findViewById<TextView>(R.id.tv_favorito_data) }

        init {
            itemView.setOnClickListener {
                if (::favorito.isInitialized) {
                    itemClicado(favorito)
                }
            }
        }

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun vincula(favorito: Favorito) {
            this.favorito = favorito
            montadora.text = favorito.montadoraNome
            modelo.text = favorito.veiculoNome
            motorizacao.text = favorito.motorizacaoNome
            separador.visibility = if (favorito.motorizacaoId > 0) View.VISIBLE else View.INVISIBLE
            sistema.text = favorito.sistemaNome
            val anoAtual = Calendar.getInstance().get(Calendar.YEAR)
            val anoInicial = if (favorito.anoInicial > 0) favorito.anoInicial else "*"
            val anoFinal = if (favorito.anoFinal > 0) favorito.anoFinal else anoAtual
            if ((favorito.anoInicial > 0) || favorito.anoFinal > 0)
                ano.text = "($anoInicial - $anoFinal)"
            else ano.text = ""
            conector.text = favorito.conectorNome
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
            data.text = dateFormat.format(favorito.data)

        }
    }

    fun buscaObjeto(position: Int): Favorito {
        return favoritoList[position]
    }

}