package br.com.tecnomotor.thanos.ui.menu.fragments

import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import br.com.tecnomotor.thanos.R

class Directions {
    companion object {
        fun getVeiculos(): NavDirections {
            return MontadorasFragmentDirections.actionMontadorasToVeiculos()
        }
        fun getMotorizacao(fragmentId: Int): NavDirections {
            return when (fragmentId) {
                R.id.fragment_montadoras -> MontadorasFragmentDirections.actionMontadoraToMotorizacao()
                else -> VeiculosFragmentDirections.actionVeiculosToMotorizacao()
            }
        }
        fun getTipoDeSistemas(fragmentId: Int): NavDirections {
            return when (fragmentId) {
                R.id.fragment_montadoras -> MontadorasFragmentDirections.actionMontadorasToTipoSistemas()
                R.id.fragment_veiculos -> VeiculosFragmentDirections.actionVeiculosToTipoSistemas()
                R.id.fragment_motorizacao -> MotorizacaoFragmentDirections.actionMotorizacaoToTipoSistemas()
                else -> MotorizacaoFragmentDirections.actionMotorizacaoToTipoSistemas()
            }
        }
        fun getSistemas(fragmentId: Int): NavDirections {
            return when (fragmentId) {
                R.id.fragment_montadoras -> MontadorasFragmentDirections.actionMontadorasToSistemas()
                R.id.fragment_veiculos -> VeiculosFragmentDirections.actionVeiculosToSistemas()
                R.id.fragment_motorizacao -> MotorizacaoFragmentDirections.actionMotorizacaoToSistemas()
                else -> TipoSistemasFragmentDirections.actionTipoSistemasToSistemas()
            }
        }
        fun getConectores(fragmentId: Int): NavDirections {
            return when (fragmentId) {
                R.id.fragment_montadoras -> MontadorasFragmentDirections.actionMontadorasToConectores()
                R.id.fragment_veiculos -> VeiculosFragmentDirections.actionVeiculosToConectores()
                R.id.fragment_motorizacao -> MotorizacaoFragmentDirections.actionMotorizacaoToConectores()
                R.id.fragment_tipoSistemas -> TipoSistemasFragmentDirections.actionTipoSistemasToConectores()
                else -> SistemasFragmentDirections.actionSistemasToConectores()
            }
        }
        fun getPosConectores(fragmentId: Int): NavDirections {
            return when (fragmentId) {
                R.id.fragment_montadoras -> MontadorasFragmentDirections.actionMontadorasToPosicaoConector()
                R.id.fragment_veiculos -> VeiculosFragmentDirections.actionVeiculosToPosicaoConector()
                R.id.fragment_motorizacao -> MotorizacaoFragmentDirections.actionMotorizacaoToPosicaoConector()
                R.id.fragment_tipoSistemas -> TipoSistemasFragmentDirections.actionTipoSistemasToPosicaoConector()
                R.id.fragment_sistemas -> SistemasFragmentDirections.actionSistemasToPosicaoConector()
                else -> ConectorFragmentDirections.actionConectoresToPosicaoConector()
            }
        }
    }
}