package br.com.tecnomotor.rasther.module

import org.w3c.dom.NodeList
import java.util.*

/**
 * DiagEcu
 * @author rogerio
 */
class DiagEcu {
    /**
     * Itens da ECU
     */
    var itens: ArrayList<Int> = ArrayList()

    /**
     * Clear data
     */
    fun clear() {
        itens = ArrayList()
    }

    /**
     * Carrega os itens através de um nó do XML
     * @param node
     */
    fun parser(nodeList: NodeList?) {
        this.clear()
        if (nodeList != null) {
            val countI = nodeList.length
            if (countI > 0) {
                for (i in 0 until countI) {
                    val node = nodeList.item(i)
                    if (node.nodeName == "Item") {
                        val countJ = node.childNodes.length
                        if (countJ > 0) {
                            for (j in 0 until countJ) {
                                val item = node.childNodes.item(j)
                                if (item.nodeName == "MSG") itens.add(Integer.valueOf(item.childNodes.item(0).nodeValue))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return "DiagEcu(itens=$itens)"
    }

    /**
     * Construtor
     */
    init {
        this.clear()
    }
}