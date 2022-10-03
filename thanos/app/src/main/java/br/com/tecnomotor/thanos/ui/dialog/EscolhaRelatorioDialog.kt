package br.com.tecnomotor.thanos.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController


import br.com.tecnomotor.thanos.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class EscolhaRelatorioDialog(
    private val context: Context,
    fragment: Fragment,
) {

    private val controlador by lazy {
        findNavController(fragment)
    }

    @SuppressLint("InflateParams")
    private val viewDialog =
        LayoutInflater.from(context).inflate(R.layout.dialog_selecao_relatorio, null)
    private var radioGroup =
        viewDialog.findViewById<RadioGroup>(R.id.dialog_relatorio_radiogroup)
    private val radioButtonNovo =
        viewDialog.findViewById<RadioButton>(R.id.dialog_relatorio_radiobutton_novo)
    private val radioButtonExistente =
        viewDialog.findViewById<RadioButton>(R.id.dialog_relatorio_radiobutton_existente)
    private val inputTextPlaca =
        viewDialog.findViewById<TextInputLayout>(R.id.dialog_relatorio_input_placa)
    private var checkedItemId = radioGroup.checkedRadioButtonId

    @SuppressLint("ResourceType")
    fun mostra(directions: NavDirections) {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            checkedItemId = checkedId
            inputTextPlaca.isEnabled = checkedId == radioButtonNovo.id
//            Log.i("DialogEscolha", "mostra: $checkedItemId")
        }

        MaterialAlertDialogBuilder(context)
            .setTitle("Selecione uma opção")
            .setMessage("Depois de criar ou selecionar um relatório existente não aparecerá mais esta janela.")
            .setView(viewDialog)
            .setNegativeButton("Cancelar") { _, _ -> }
            .setPositiveButton("Confirmar") { _, _ ->
                if (checkedItemId == radioButtonExistente.id) {
                    // vai para a tela de selecionar relatorio e envia dados para relatorio
                    vaiParaSelecionarRelatorio(directions)
                } else {
                    // criar um novo relatorio e envia dados para o relatorio
//                    enviaLeituraParaRelatorio()
                }
            }
            .show()
    }

    private fun vaiParaSelecionarRelatorio(directions: NavDirections) {
        controlador.navigate(directions)
    }
}