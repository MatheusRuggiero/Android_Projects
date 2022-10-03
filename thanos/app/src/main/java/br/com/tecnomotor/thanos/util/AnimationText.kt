package br.com.tecnomotor.thanos.util

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

fun TextView.animateTranslation(translation: String){
    var index = 0
    val mHandler = Handler(Looper.getMainLooper())
    val characterAdder: Runnable = object : Runnable {
        override fun run() {
            text = translation.subSequence(0, index++)
            if (index <= translation.length) {
                mHandler.postDelayed(this, 10)
            }
        }
    }
    text = ""
    mHandler.removeCallbacks(characterAdder)
    mHandler.postDelayed(characterAdder, 10)
}

fun TextInputLayout.animateTranslation(translation: String){
    var index = 0
    val mHandler = Handler(Looper.getMainLooper())
    val characterAdder: Runnable = object : Runnable {
        override fun run() {
            hint = translation.subSequence(0, index++)
            if (index <= translation.length) {
                mHandler.postDelayed(this, 10)
            }
        }
    }
    hint = ""
    mHandler.removeCallbacks(characterAdder)
    mHandler.postDelayed(characterAdder, 10)
}