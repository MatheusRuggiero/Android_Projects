package br.com.tecnomotor.thanos.ui.menu.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.res.Configuration
import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.menu.HistoricoAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.model.cliente.Historico
import br.com.tecnomotor.thanos.model.cliente.loadHistorico
import br.com.tecnomotor.thanos.model.cliente.toHistorico
import br.com.tecnomotor.thanos.ui.menu.viewmodel.HistoricoViewModel
import kotlinx.android.synthetic.main.fragment_historico.*
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class HistoricoFragment : Fragment() {

    var historicoListNotFilter: MutableList<Historico> = ArrayList()
    private val viewModel by lazy {
        ViewModelProvider(this)[HistoricoViewModel::class.java]
    }

    val adapter: HistoricoAdapter by lazy { HistoricoAdapter(requireContext()) }
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        buscaHistorico()
    }

    private fun configuraRemoveHistorico() {

        val swipeHandler: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(
                0,  //Para mover para cima ou para baixo        (ItemTouchHelper.UP | ItemTouchHelper.DOWN)
                ItemTouchHelper.RIGHT
            ) {
                //Para mover para a esquerda ou direita     (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean {
                    return false
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val history = adapter.buscaObjeto(viewHolder.adapterPosition)
                    AlertDialog.Builder(context)
                        .setTitle("Excluir o item")
                        .setMessage("Tem certeza que deseja excluir este item?")
                        .setCancelable(false)
                        .setPositiveButton("CONFIRMAR") { dialog, which ->
                            Toast.makeText(
                                context,
                                " ${history.montadoraNome} " +
                                        "${history.veiculoNome} " +
                                        "${history.anoInicial} " +
                                        "${history.anoFinal} " +
                                        "${history.sistemaNome} " +
                                        "${history.conectorNome} " +
                                        "${history.motorizacaoNome} " +
                                        "${resources.getText(R.string.excluido)}",
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.deletaHistorico(history)
                            buscaHistorico()

                        }
                        .setNegativeButton("CANCELAR") { dialog, which ->
                            rvItens.recycledViewPool.clear()
                            adapter.notifyDataSetChanged()
                            dialog.dismiss()
                        }
                        .show()
                }

                @SuppressLint("ResourceAsColor")
                override fun onChildDraw(
                    c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                    dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    val itemHeight = itemView.bottom - itemView.top
                    val space = (itemHeight / 6)
                    val width = (space * 4)
                    val icon: Bitmap =
                        BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_delete)
                    val paint = Paint()
                    paint.setColor(resources.getColor(R.color.background_red))
                    val background = RectF(
                        itemView.left.toFloat(),
                        itemView.top.toFloat(),
                        dX,
                        itemView.bottom.toFloat()
                    )
                    c.drawRect(background, paint)
                    val icon_dest = RectF(
                        itemView.left.toFloat() + space, itemView.top.toFloat() + space,
                        itemView.left.toFloat() + space + width, itemView.bottom.toFloat() - space
                    );
                    c.drawBitmap(icon, null, icon_dest, null)
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvItens)
    }

    private fun buscaHistorico() {
        val historicos = mutableListOf<Historico>()
        viewModel.getHistoricos().observe(this) { historicoListEntity ->
            historicoListEntity.forEach { historicoEntity ->
                val historico: Historico = historicoEntity.toHistorico()
                historicos.add(historico)
            }
            GlobalScope.launch(Dispatchers.Main) {
                while (historicos.size < historicoListEntity.size) {
                    delay(500)
                }
                loading_spinner.visibility = View.INVISIBLE
                historicoListNotFilter = historicos
                adapter.atualiza(historicos)
            }
        }
    }

    fun searchFilter(filter: String): MutableList<Historico> {
        val historicoListFilter: MutableList<Historico> = ArrayList()
        if (!historicoListNotFilter.isNullOrEmpty()) {
            historicoListNotFilter.forEach { historico ->
                if (historico.montadoraNome.uppercase().contains(filter.uppercase()) ||
                    historico.veiculoNome.uppercase().contains(filter.uppercase()) ||
                    historico.motorizacaoNome.uppercase().contains(filter.uppercase()) ||
                    historico.tipoSistemaNome.uppercase().contains(filter.uppercase()) ||
                    historico.sistemaNome.uppercase().contains(filter.uppercase()) ||
                    historico.conectorNome.uppercase().contains(filter.uppercase())
                )
                    historicoListFilter.add(historico)
            }
        } else {
            Toast.makeText(context, context?.getString(R.string.not_found_item), Toast.LENGTH_SHORT)
                .show()
        }
        return historicoListFilter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Históricos"
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historico, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_historico, menu)
        val menuItemFilterHistory = menu.findItem(R.id.menu_search_history)

        val searchView: SearchView = menuItemFilterHistory?.actionView as SearchView
        searchView.queryHint = context?.getString(R.string.type_to_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!historicoListNotFilter.isNullOrEmpty()) {
                    if (!newText.isNullOrBlank() || !newText.isNullOrEmpty())
                        adapter.atualiza(searchFilter(newText))
                    else adapter.atualiza(historicoListNotFilter)
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    private fun configuraRecyclerView() {
        adapter.itemClicado = this::ItemClicado
        val orientation = this.resources.configuration.orientation
        val lManager = GridLayoutManager(
            view?.context,
            if (orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2
        )
        lManager.orientation = LinearLayoutManager.VERTICAL
        rvItens.layoutManager = lManager
        rvItens.adapter = adapter
    }

    fun ItemClicado(historico: Historico) {
        val selecao = Selecao.getInstance()
        selecao.loadHistorico(historico)
        val direcao = HistoricoFragmentDirections.actionHistoricoToPosicaoConector()
        Navigation.findNavController(requireView()).navigate(direcao)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adapter.itemCount > 0) loading_spinner.visibility = View.INVISIBLE
        configuraRecyclerView()
        configuraRemoveHistorico()
    }
}