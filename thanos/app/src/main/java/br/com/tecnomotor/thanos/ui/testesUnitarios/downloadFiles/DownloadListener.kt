package br.com.tecnomotor.thanos.ui.testesUnitarios.downloadFiles
/**
@Author Matheus_Ruggiero
 */

interface DownloadListener {

    fun onStart() // Download Start

    fun onProgress(progress: Int) // Download progress

    fun onFinish(path: String?) // Download complete

    fun onFail(errorInfo: String?) // Download failed
}