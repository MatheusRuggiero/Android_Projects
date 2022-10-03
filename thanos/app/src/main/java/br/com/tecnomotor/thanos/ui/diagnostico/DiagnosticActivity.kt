package br.com.tecnomotor.thanos.ui.diagnostico

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.ui.diagnostico.fragments.DiagnosticoFragment
import br.com.tecnomotor.thanos.ui.diagnostico.viewmodel.DiagnosticViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@DelicateCoroutinesApi
class DiagnosticActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[DiagnosticViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnostic)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.custom_back_arrow_ripple)
        viewModel.attachContext(this)
    }

    override fun onResume() {
        super.onResume()
        val nav = NavHostFragment.create(R.navigation.nav_graph_diagnostico_menu)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_nav_diagnostico, nav)
            .setPrimaryNavigationFragment(nav)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_activity_nav_diagnostico)

        navHost?.let {
            it.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                println(fragment)
                if (fragment is DiagnosticoFragment) {
                    mostraAlertaSairDiagnostico()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    private fun mostraAlertaSairDiagnostico() = AlertDialog.Builder(this)
        .setTitle(getString(R.string.sair_diagnostico))
        .setMessage(getString(R.string.deseja_finalizar_diagnostico))
        .setPositiveButton(getString(R.string.confirmar)) { _, _ ->
            super.onBackPressed()
        }
        .setNegativeButton(getString(R.string.cancelar)) { _, _ ->
        }
        .show()
}
