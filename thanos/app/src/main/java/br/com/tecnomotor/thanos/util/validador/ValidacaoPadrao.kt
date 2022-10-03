package br.com.tecnomotor.thanos.util.validador

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidacaoPadrao(private var textInputCampo: TextInputLayout?) : Validador {
    private val CAMPO_OBRIGATORIO = "Campo Obrigatório!"
    private var campo: EditText? = null

    init {
        campo = this.textInputCampo!!.editText
    }

    private fun validaCampoObrigatorio(): Boolean? {
        val texto = campo!!.text.toString()
        if (texto.isEmpty()) {
            textInputCampo!!.error = CAMPO_OBRIGATORIO
            return false
        }
        return true
    }

    private fun removeErro() {
        textInputCampo!!.error = null
        textInputCampo!!.isErrorEnabled = false
    }

    override fun estaValido(): Boolean {
        if (!validaCampoObrigatorio()!!) return false
        removeErro()
        return true
    }
}