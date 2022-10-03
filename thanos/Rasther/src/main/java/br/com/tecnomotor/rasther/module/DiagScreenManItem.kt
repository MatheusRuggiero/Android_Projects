package br.com.tecnomotor.rasther.module

import org.w3c.dom.Node

/**
 * DiagScreenManItem
 * @author rogerio
 */
class DiagScreenManItem {
    /**
     * Manual
     */
    var man: DiagMan = DiagMan()

    /**
     * Clear data
     */
    fun clear() {
        man = DiagMan()
    }

    /**
     * Verifica se o item é válido para não inserir itens vazios
     * @return
     */
    fun check(): Boolean {
        return man.check()
    }

    /**
     * Carrega o item através de um nó do XML
     * @param node
     */
    fun parser(node: Node?) {
        this.clear()
        if (node != null) {
            val count = node.childNodes.length
            for (i in 0 until count) {
                val item = node.childNodes.item(i)
                if (item.nodeName == "Man") man.parser(item)
            }
        }
    }

    override fun toString(): String {
        return "DiagScreenManItem(man=$man)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}