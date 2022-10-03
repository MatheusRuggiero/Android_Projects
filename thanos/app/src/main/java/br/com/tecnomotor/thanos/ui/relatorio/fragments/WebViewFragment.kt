package br.com.tecnomotor.thanos.ui.relatorio.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.ui.relatorio.viewmodel.ListaRelatoriosViewModel
import com.google.gson.JsonDeserializer

class WebViewFragment : Fragment() {


    val listaRelatoriosViewModel: ListaRelatoriosViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    @SuppressLint("SdCardPath", "SetJavaScriptEnabled", "JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        val webView = view.findViewById(R.id.webViewHTML) as WebView
        val uri = "file:///android_asset/tointernal/Config/index.html"
        //"/data/data/br.com.tecnomotor.thanos/files/index.html"
        webView.loadUrl(uri)

        webView.getSettings().setJavaScriptEnabled(true)
        webView.addJavascriptInterface(this, "Android");

        //webView.postUrl()


        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE // mostra a progress
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.INVISIBLE // esconde a progress
            }
        }

    }

    @JavascriptInterface
    fun jsShowToast(toast: String?): String? {

        listaRelatoriosViewModel.gsonObjto
       return toast

        //JsonReader(argsJson)
    }

    @JavascriptInterface
    fun test(data: String): String {
        Log.d("TEST", "data = $data")
        return "this is just a test"
    }



}