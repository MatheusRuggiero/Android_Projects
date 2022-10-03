package br.com.tecnomotor.thanos.ui.home.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.config.ConfigApp
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.licenca_de_uso.*

class LicencaDeUsoFragment : Fragment() {

    private lateinit var concordo: CheckBox
    private lateinit var btnOk: Button

    private val configApp = ConfigApp.getInstance()

    private val controlador by lazy {
        findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_licenca_de_uso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //title = "Licença de uso"
        licenca_de_uso_termos.movementMethod = ScrollingMovementMethod()

        btnOk = licenca_de_uso_btn_ok
        concordo = licenca_de_uso_checkboxbtn_concordo

        concordo.setOnClickListener{
            configApp.setLicenseToUse(concordo.isChecked)
        }

        btnOk.setOnClickListener {
            if (!concordo.isChecked) mostraAlertaEscolherOpcao()
            else controlador.navigate(LicencaDeUsoFragmentDirections.actionLicencaDeUsoFragmentToConexaoFragment())
        }
    }

    private fun mostraAlertaEscolherOpcao() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Você concorda?")
            .setMessage("Para continuar é necessário aceitar os termos!")
            .setPositiveButton("Ok", null)
            .setCancelable(false)
            .show()
    }
}