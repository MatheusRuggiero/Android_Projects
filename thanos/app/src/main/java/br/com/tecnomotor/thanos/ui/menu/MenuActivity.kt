package br.com.tecnomotor.thanos.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.model.Selecao


class MenuActivity : AppCompatActivity() {

    private val controlador by lazy {
        findNavController(R.id.main_activity_nav_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.custom_back_arrow_ripple)
        //Monitora a alterações de fragment
        controlador.addOnDestinationChangedListener {
                controller, destination, arguments ->

        }
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean{
        if (controlador.currentDestination?.id != R.id.fragment_montadoras) {
            menuInflater.inflate(R.menu.activity_menu, menu)
        }
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
            }
            R.id.menu_diagnostic_new -> {
                Selecao.getInstance().clear()
                val intent = Intent(this, MenuActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}