package br.com.tecnomotor.thanos.service.soap.base

import org.ksoap2.serialization.PropertyInfo


/**
 * @author rogerio.neo
 */
class KserverProperty(
    name: String?,
    value: Any?
) : PropertyInfo() {
    init {
        this.name = name
        this.value = value
    }
}