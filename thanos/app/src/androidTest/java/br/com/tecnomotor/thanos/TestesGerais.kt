package br.com.tecnomotor.thanos

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.tecnomotor.thanos.model.diagnostico.TipoLeitura
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class TestesGerais {
    @Test

    fun tipoLeituratest(){
        var tipoLeitura : TipoLeitura =  TipoLeitura.valueOf("Digital")
        println("Tipo: $tipoLeitura")
        tipoLeitura  =  TipoLeitura.valueOf("AnalogText")
        println("Tipo: $tipoLeitura")
        tipoLeitura  =  TipoLeitura.valueOf("Analogic")
        println("Tipo: $tipoLeitura")
        tipoLeitura  =  TipoLeitura.valueOf("AnalogicVag")
        println("Tipo: $tipoLeitura")

    }
}