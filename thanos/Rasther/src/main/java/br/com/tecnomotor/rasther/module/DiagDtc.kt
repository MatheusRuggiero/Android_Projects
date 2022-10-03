package br.com.tecnomotor.rasther.module

import br.com.tecnomotor.rasther.module.item.Item
import org.w3c.dom.NodeList
import java.io.Serializable
import java.util.*

/**
 * DiagDtc
 * @author rogerio
 */
class DiagDtc : Serializable  {
    var itens: ArrayList<DiagDtcItem> = ArrayList()

    /**
     * Clear data
     */
    fun clear() {
        itens = ArrayList()
    }

    /**
     * Carrega os itens de defeitos através de um nó do XML
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
                        val item = DiagDtcItem()
                        item.parser(node)
                        itens.add(item)
                    }
                }
            }
        }
    }

    fun getData(Idioma: String): ArrayList<Item> {
        var value: Item
        val returnData: ArrayList<Item> = ArrayList<Item>()
        for (i in itens.indices) {
            value = Item()
            value.addString(DiagItemMsg, itens[i].msg.toString())
            value.addString(DiagItemDtc, itens[i].name)
            value.addString(DiagItemCond, itens[i].cond.toString())
            value.addString(DiagItemMan, itens[i].man[Idioma].value)
            returnData.add(value)
        }
        return returnData
    }

    override fun toString(): String {
        return "DiagDtc(itens=$itens)"
    }

    companion object {
        const val DiagItemDtc = "DTC"
        const val DiagItemMsg = "Msg"
        const val DiagItemCond = "Cond"
        const val DiagItemStatus = "Status"
        const val DiagItemMan = "Man"
    }

    /**
     * Construtor
     */
    init {
        this.clear()
    }
}