package br.com.tecnomotor.thanos.adapter.testeUnitario

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.tecnomotor.thanos.ui.testesUnitarios.DiagnosticoFragment
import br.com.tecnomotor.thanos.ui.testesUnitarios.FuncoesBasicasFragment
import br.com.tecnomotor.thanos.ui.testesUnitarios.PlaceholderFragment


private val TAB_TITLES = arrayOf(
   "Funcoes Basicas",
    "Diagnostico",
    "Aba 3",
    "Aba 4",
)
/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

class UnitTestAdapter (private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        return when (position) {
            0 -> FuncoesBasicasFragment()
            1 -> DiagnosticoFragment()
            else -> PlaceholderFragment()
        }


       // return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.size
    }
}