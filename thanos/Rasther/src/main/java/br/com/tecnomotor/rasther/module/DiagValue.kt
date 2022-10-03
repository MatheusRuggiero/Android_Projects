package br.com.tecnomotor.rasther.module

import org.w3c.dom.NodeList

/**
 * DiagValue
 * @author rogerio
 */
class DiagValue {
    /**
     * Leituras do modulo
     */
    var itens: ArrayList<DiagValueItem> = ArrayList()

    /**
     * Clear data
     */
    fun clear() {
        itens = ArrayList()
    }

    /**
     * Carrega as leituras através de um nó do XML
     * @param nodeList
     */
    fun parser(nodeList: NodeList?) {
        this.clear()
        if (nodeList != null) {
            val count = nodeList.length
            if (count > 0) {
                for (i in 0 until count) {
                    val node = nodeList.item(i)
                    if (node.nodeName == "Item") {
                        val item = DiagValueItem()
                        item.parser(node)
                        itens.add(item)
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return "DiagValue(itens=$itens)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}