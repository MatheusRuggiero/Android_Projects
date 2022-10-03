package br.com.tecnomotor.rasther.module

import org.w3c.dom.NodeList
import java.util.*

/**
 * DiagScreenMan
 * @author rogerio
 */
class DiagScreenMan {
    /**
     * Itens do manual
     */
    var itens: ArrayList<DiagScreenManItem> = ArrayList()

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
            val count = nodeList.length
            if (count > 0) {
                for (i in 0 until count) {
                    val item = DiagScreenManItem()
                    item.parser(nodeList.item(i))
                    if (item.check()!!) itens.add(item)
                }
            }
        }
    }

    override fun toString(): String {
        return "DiagScreenMan(itens=$itens)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}