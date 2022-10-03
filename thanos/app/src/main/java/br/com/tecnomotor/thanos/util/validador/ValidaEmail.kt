package br.com.tecnomotor.thanos.util.validador

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidaEmail(private val textInputEmail: TextInputLayout?) : Validador {
    private var campoEmail: EditText? = null
    private var validadorPadrao: ValidacaoPadrao? = null

    init {
        campoEmail = this.textInputEmail!!.editText
        validadorPadrao = ValidacaoPadrao(this.textInputEmail)
    }

    private fun validaPadrao(email: String): Boolean {
        val regex = ".+@.+\\..+".toRegex()
        if (email.matches(regex)) {
            return true
        }
        textInputEmail!!.error = "Email inválido"
        return false
    }

    override fun estaValido(): Boolean {
        if (!validadorPadrao!!.estaValido()) return false
        val email = campoEmail?.text.toString()
        if (!validaPadrao(email)) return false
        return true
    }
}