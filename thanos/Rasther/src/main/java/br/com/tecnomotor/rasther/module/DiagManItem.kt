package br.com.tecnomotor.rasther.module

import org.w3c.dom.Node

/**
 * Manual: pode ser (pt, es, en,...)
 * @author rogerio
 */
class DiagManItem {
    /**
     * Idioma do manual
     */
    var idioma: String = ""

    /**
     * Texto do manual no idioma
     */
    var value: String = ""

    /**
     * Clear data
     */
    fun clear() {
        idioma = ""
        value = ""
    }

    /**
     * Carrega os dados através de um nó do XML
     * @param node
     */
    fun parser(node: Node?) {
        this.clear()
        if (node != null) {
            idioma = node.nodeName
            if (node.childNodes.item(0) != null) value = node.childNodes.item(0).nodeValue
        }
    }

    override fun toString(): String {
        return "DiagManItem(idioma=$idioma, value=$value)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}