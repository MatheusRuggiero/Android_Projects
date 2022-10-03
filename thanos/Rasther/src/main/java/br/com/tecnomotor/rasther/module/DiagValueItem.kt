package br.com.tecnomotor.rasther.module

import org.w3c.dom.Node

/**
 * DiagValueItem
 * @author rogerio
 */
class DiagValueItem {
    var msg: Int = -1
    var special: Boolean = false
    var typeValue: DiagTypeValue = DiagTypeValue()
    var priority: Int = 0
    var man: DiagMan = DiagMan()
    fun clear() {
        msg = -1
        special = false
        typeValue = DiagTypeValue()
        priority = 0
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
                if (item.nodeName == "MSG") msg = Integer.valueOf(item.childNodes.item(0).nodeValue)
                if (item.nodeName == "Special") special = java.lang.Boolean.valueOf(item.childNodes.item(0).nodeValue)
                if (item.nodeName == "TypeValue") typeValue.parser(item)
                if (item.nodeName == "Priority") priority = Integer.valueOf(item.childNodes.item(0).nodeValue)
                if (item.nodeName == "Man") man.parser(item)
            }
        }
    }

    override fun toString(): String {
        return "DiagValueItem(msg=$msg, special=$special, typeValue=$typeValue, priority=$priority, man=$man)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}