package br.com.tecnomotor.thanos.ui.testesUnitarios.downloadFiles


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
/**
@Author Matheus_Ruggiero
 */

interface DownloadService {
    @Streaming
    @GET
    fun download(@Url url: String?): Call<ResponseBody?>?
}