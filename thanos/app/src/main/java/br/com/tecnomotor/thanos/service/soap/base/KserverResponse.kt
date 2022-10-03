package br.com.tecnomotor.thanos.service.soap.base

/**
 * @author rogerio.neo
 */
class KserverResponse(var methodName: String = "") {
    var obj: Any? = null
    var response = false
}