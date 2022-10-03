package br.com.tecnomotor.thanos.service.update.toexclude

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

object Utils {
    /**
     * Verifica se a rede de Internet está funcionando
     * @param activity Uma activity
     * @return true, caso positivo. false, caso negativo.
     */
    fun isNetworkAvailable(activity: Activity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager
            .activeNetworkInfo
        return activeNetworkInfo?.isConnected ?: false
    }
}