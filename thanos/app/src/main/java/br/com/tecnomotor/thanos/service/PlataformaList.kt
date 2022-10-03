package br.com.tecnomotor.thanos.service

import org.ksoap2.serialization.SoapObject
import java.io.Serializable
import java.util.*

/**
 * @author rogerio.neo
 */
class PlataformaList(v: Vector<*>) : Vector<Plataforma?>(), Serializable {
    init {
        for (i in v.indices) {
            add(Plataforma(v[i] as SoapObject))
        }
    }
}