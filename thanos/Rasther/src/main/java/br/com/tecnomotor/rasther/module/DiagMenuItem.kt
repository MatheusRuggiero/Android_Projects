package br.com.tecnomotor.rasther.module

import org.w3c.dom.Node

/**
 * Item do menu
 * @author rogerio
 */
class DiagMenuItem {
    /**
     * Nome do menu
     */
    var name: String = ""

    /**
     * ID da mensagem no banco de dados de mensagens
     */
    var msg: Int = -1

    /**
     * Manual do menu
     */
    var man: DiagMan = DiagMan()

    /**
     * SubMenu
     */
    var subMenu: DiagMenu = DiagMenu()

    /**
     * Clear data
     */
    fun clear() {
        name = ""
        msg = -1
        man = DiagMan()
        subMenu = DiagMenu()
    }

    /**
     * Carrega o item do menu através de um nó do XML
     * @param node
     */
    fun parser(node: Node?) {
        this.clear()
        if (node != null) {
            name = node.nodeName
            val count = node.childNodes.length
            for (i in 0 until count) {
                val item = node.childNodes.item(i)
                if (item.nodeName == "MSG") msg = Integer.valueOf(item.childNodes.item(0).nodeValue)
                if (item.nodeName == "Man") man.parser(item)
                if (item.nodeName == "Menu") subMenu.parser(item.childNodes)
            }
        }
    }

    override fun toString(): String {
        return "DiagMenuItem(name=$name,\n msg=$msg,\n man=$man,\n subMenu=$subMenu)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}