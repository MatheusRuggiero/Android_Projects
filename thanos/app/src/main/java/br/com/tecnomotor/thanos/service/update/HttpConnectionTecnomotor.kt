package br.com.tecnomotor.thanos.service.update

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Classe responsável por fazer uma chamada ao servidor para inserir dispositivos em beta
 * @author diogo
 * código baseado em https://stackoverflow.com/questions/8654876/http-get-using-android-httpurlconnection
 */
class HttpConnectionTecnomotor : AsyncTask<String?, Void?, String?>() {
    /**
     * Obtém o valor retornado pelo servidor
     * @return Uma string
     */
    var response: String? = null
    protected override fun doInBackground(vararg strings: String?): String? {
        val url: URL
        var urlConnection: HttpURLConnection? = null
        try {
            url = URL(strings[0])
            urlConnection = url.openConnection() as HttpURLConnection
            val responseCode = urlConnection!!.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = readStream(urlConnection.inputStream)
                Log.v("CatalogClient", response!!)
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(s: String?) {
        super.onPostExecute(s)

        //Logger.d("HttpConnectionTecnomotor", "Resposta: " + server_response);
    }

    /**
     * Converte uma InputStream em uma string
     * @param in
     * @return
     */
    private fun readStream(`in`: InputStream): String {
        var reader: BufferedReader? = null
        val response = StringBuffer()
        try {
            reader = BufferedReader(InputStreamReader(`in`))
            var line: String? = ""
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return response.toString()
    }
}