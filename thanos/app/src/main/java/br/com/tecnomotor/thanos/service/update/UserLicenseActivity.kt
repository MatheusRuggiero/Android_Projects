package br.com.tecnomotor.thanos.service.update

import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import br.com.tecnomotor.thanos.service.update.toexclude.RastherDefaultActivity
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class UserLicenseActivity : RastherDefaultActivity() {
    private var userLicenseChecked = false
    private var sourceFileName = LICENSE_PT
    private var textoContrato = ""
    private var checkBox: CheckBox? = null
    private var btnOK: Button? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_user_license)
        setCheckBox()
        setBtnOk()
        escolheArquivoLinguagem()
        abrirLicenca()
        exibirLicenca()
    }

    protected override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        // será true quando usuário já aceitou a licença e abre novamente para visualizar
        if (sharedPreferences?.getBoolean(PREFERENCES_USER_LICENSE_CHECKED, false) == true) setResult(
            RESULT_OK
        ) else setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun escolheArquivoLinguagem() {
        val language = sharedPreferences?.getString(PREFERENCES_LANGUAGE, "PT")
        sourceFileName =
            if (language == "PT") LICENSE_PT else if (language == "ES") LICENSE_ES else if (language == "EN") LICENSE_EN else LICENSE_PT
    }

    private fun abrirLicenca() {
        var `in`: InputStream? = null
        val buffer = ByteArrayOutputStream()
        var nRead: Int
        val data = ByteArray(1024)
        val assetManager: AssetManager = this.getAssets()
        try {
            `in` = assetManager.open(sourceFileName)
            while (`in`.read(data, 0, data.size).also { nRead = it } != -1) {
                buffer.write(data, 0, nRead)
            }
            buffer.flush()
            val byteArray = buffer.toByteArray()
            textoContrato = String(byteArray, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun exibirLicenca() {
        val tv: TextView = findViewById(0/*R.id.textView*/) as TextView
        tv.setText(textoContrato)
    }

    private fun listenerCheckBox() {
        checkBox?.setOnClickListener(View.OnClickListener {
            if(checkBox != null) {
                if (checkBox!!.isChecked) {
                    userLicenseChecked = true
                    btnOK!!.isEnabled = true
                } else {
                    userLicenseChecked = false
                    btnOK!!.isEnabled = false
                }
            }
        })
    }

    private fun setCheckBox() {
        checkBox = findViewById(0/*R.id.checkBoxUserLicense*/) as CheckBox?
        userLicenseChecked =
            (sharedPreferences?.getBoolean(PREFERENCES_USER_LICENSE_CHECKED, false) ?: checkBox?.setChecked(userLicenseChecked )) as Boolean
        listenerCheckBox()
        checkBox?.setEnabled(!userLicenseChecked) // se usuário já aceitou, não tem como "desaceitar"
    }

    private fun setBtnOk() {
        btnOK = findViewById(0/*R.id.buttonOkUserLicense*/) as Button?
        btnOK!!.isEnabled = userLicenseChecked
    }

    fun onClickBtnOkUserLicense(v: View?) {
        if (userLicenseChecked) setResult(RESULT_OK) else setResult(RESULT_CANCELED)
        finish()
    }

    companion object {
        private const val TAG = "UserLicenseActivity"
        private const val LICENSE_PT = "license_pt.txt"
        private const val LICENSE_ES = "license_es.txt"
        private const val LICENSE_EN = "license_en.txt"
    }
}