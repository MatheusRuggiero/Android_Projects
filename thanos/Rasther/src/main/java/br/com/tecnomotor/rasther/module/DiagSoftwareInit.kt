package br.com.tecnomotor.rasther.module

import org.w3c.dom.NodeList
import java.util.*

/**
 * DiagSoftwareInit
 * @author rogerio
 */
class DiagSoftwareInit {
    /**
     * Itens do software
     */
    var itens: ArrayList<String> = ArrayList()

    /**
     * Clear data
     */
    fun clear() {
        itens = ArrayList()
    }

    /**
     * Carrega os itens através de um nó do XML
     * @param nodeList
     */
    fun parser(nodeList: NodeList?) {
        this.clear()
        if (nodeList != null) {
            val countI = nodeList.length
            if (countI > 0) {
                for (i in 0 until countI) {
                    val node = nodeList.item(i)
                    if (node.nodeName == "Item") {
                        itens.add(node.childNodes.item(0).nodeValue)
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return "DiagSoftwareInit(itens=$itens)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}