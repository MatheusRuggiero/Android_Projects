package br.com.tecnomotor.rasther.module

import org.w3c.dom.NodeList
import java.util.*

/**
 * DiagMenu
 * @author rogerio
 */
class DiagMenu {
    /**
     * Itens do menu
     */
    var itens: ArrayList<DiagMenuItem> = ArrayList()

    /**
     * Clear data
     */
    fun clear() {
        itens = ArrayList()
    }

    /**
     * Retorna o tamanho do menu
     * @return
     */
    fun count(): Int {
        return itens.size
    }

    /**
     * Carrega o menu através de um nó do XML
     * @param node
     */
    fun parser(nodeList: NodeList?) {
        this.clear()
        if (nodeList != null) {
            val count = nodeList.length
            if (count > 0) {
                for (i in 0 until count) {
                    val item = DiagMenuItem()
                    item.parser(nodeList.item(i))
                    itens.add(item)
                }
            }
        }
    }

    override fun toString(): String {
        var string = ""
        for (i in itens) {
            string += " $i \n"
        }
        return " DiagMenu(itens=$string)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}