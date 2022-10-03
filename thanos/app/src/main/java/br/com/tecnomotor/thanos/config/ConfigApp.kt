package br.com.tecnomotor.thanos.config

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

open class ConfigApp(context: Context, fileName: String = "ConfigApp"){

    private val cTestViews = "TestViews"
    private val preferenceFirstExecution = "First Execution"
    companion object {
        private var instance: ConfigApp? = null

        fun getInstance(context: Context? = null):ConfigApp {
            if (instance == null)
                if (context == null)
                    throw  Exception("O contexto deve ser enviado pelo menos na primeira vez que o método getInstance(contexto) for chamado")
                else
                    instance = ConfigApp(context.applicationContext)
            return instance!!
        }
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(fileName,
        AppCompatActivity.MODE_PRIVATE)

    fun edit():SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    fun setTestViews(value: Boolean) {
        edit().putBoolean(cTestViews, value).apply()
    }

    fun isTestViews(): Boolean {
        return sharedPreferences.getBoolean(cTestViews, false)
    }

    /**
     * Define se a licença de uso foi aceita ou não
     */
    fun setLicenseToUse(value: Boolean) {
        edit().putBoolean("LicenseToUse", value).apply()
    }

    /**
     * Retorna se a licença de uso foi aceita
     */
    fun getLicenseToUse(): Boolean {
        return sharedPreferences.getBoolean("LicenseToUse", false)
    }

    /**
     * Indica se o app foi executado pela 1a vez
     */
    fun setFirstExecution(firstExecution: Boolean) {
        edit().putBoolean(preferenceFirstExecution, firstExecution).apply()
    }

    /**
     * Retorna se o app foi executado pela 1a vez
     */
    fun firstExecution(): Boolean {
        return sharedPreferences.getBoolean(preferenceFirstExecution, true)
    }

    /**
     * Define se alguma permissão foi negada e quantas vezes ela foi negada
     */
    fun setPermissionDeniedCount(permission: String, value: Int) {
        edit().putInt("permission:$permission",value).apply()
    }

    /**
     * Retorna a quantidade de vezes que uma permissão foi negada
     */
    fun getPermissionDeniedCount(permission: String):Int {
        return sharedPreferences.getInt("permission:$permission", 0)
    }
}