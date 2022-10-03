package br.com.tecnomotor.thanos.adapter.menu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.tecnomotor.thanos.model.Selecao
import br.com.tecnomotor.thanos.ui.menu.fragments.PosicaoConectorFragment
import br.com.tecnomotor.thanos.ui.menu.fragments.PosicaoFragment
import br.com.tecnomotor.thanos.ui.menu.fragments.TipoConectorFragment

class PosicaoConectorAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val posicaoConectorFragment = PosicaoConectorFragment()
    private val tipoConectorFragment = TipoConectorFragment()
    private val selecao = Selecao.getInstance()

    val fragments by lazy {
        val listaFragments = arrayListOf<Fragment>(PosicaoFragment())

        if (selecao.conector.nome != "") {
            listaFragments.add(tipoConectorFragment)
        }

        if (selecao.conector.imgConVeiculo > 0) {
            listaFragments.add(posicaoConectorFragment)
        }
        listaFragments
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}