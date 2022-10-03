package br.com.tecnomotor.rasther.module

import org.w3c.dom.Node

/**
 * DiagDtcItem
 * @author rogerio
 */
class DiagDtcItem {
    /**
     * Nome ou código do defeito
     */
    var name: String = ""

    /**
     * ID da mensagem no banco de dados de mensagens
     */
    var msg: Int = -1
    var cond: Int = 0

    /**
     * Manual do defeito
     */
    var man: DiagMan = DiagMan()

    /**
     * Clear data
     */
    fun clear() {
        name = ""
        msg = -1
        cond = 0
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
                if (item.nodeName == "DTC") name = item.childNodes.item(0).nodeValue
                if (item.nodeName == "MSG") msg = Integer.valueOf(item.childNodes.item(0).nodeValue)
                if (item.nodeName == "COND") cond = Integer.valueOf(item.childNodes.item(0).nodeValue)
                if (item.nodeName == "Man") man.parser(item)
            }
        }
    }

    override fun toString(): String {
        return "DiagDtcItem(name=$name, msg=$msg, cond=$cond, man=$man)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}