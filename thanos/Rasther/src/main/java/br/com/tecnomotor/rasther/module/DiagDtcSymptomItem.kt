package br.com.tecnomotor.rasther.module

import org.w3c.dom.Node

/**
 * DiagDtcSymptomItem
 * @author rogerio
 */
class DiagDtcSymptomItem {
    /**
     * Nome ou código do simtoma
     */
    var name: String = ""

    /**
     * Manual do defeito
     */
    var man: DiagMan = DiagMan()

    /**
     * Clear data
     */
    fun clear() {
        name = ""
        man = DiagMan()
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
                if (item.nodeName == "DTCSymptom") name = item.childNodes.item(0).nodeValue
                if (item.nodeName == "Man") man.parser(item)
            }
        }
    }

    override fun toString(): String {
        return "DiagDtcSymptomItem(name=$name, man=$man)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}