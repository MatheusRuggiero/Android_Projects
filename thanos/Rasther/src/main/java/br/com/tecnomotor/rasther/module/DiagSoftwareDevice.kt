package br.com.tecnomotor.rasther.module

import org.w3c.dom.NodeList

/**
 * DiagSoftwareDevice
 * @author rogerio
 */
class DiagSoftwareDevice {
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
     * @param node
     */
    fun parser(nodeList: NodeList) {
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
        return "DiagSoftwareDevice(itens=$itens, data=$data)"
    }

    val data: ArrayList<String>
        get() {
            val returnData = ArrayList<String>()
            returnData.addAll(itens)
            return returnData
        }

    /**
     * Construtor
     */
    init {
        this.clear()
    }
}