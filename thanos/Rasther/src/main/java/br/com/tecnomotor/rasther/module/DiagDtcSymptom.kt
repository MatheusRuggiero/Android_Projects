package br.com.tecnomotor.rasther.module

import org.w3c.dom.NodeList
import java.util.*

/**
 * DiagDtcSymptom
 * @author rogerio
 */
class DiagDtcSymptom {
    /**
     * Itens do simtoma
     */
    var itens: ArrayList<DiagDtcSymptomItem> = ArrayList()

    /**
     * Clear data
     */
    fun clear() {
        itens = ArrayList()
    }

    /**
     * Carrega os itens de defeitos através de um nó do XML
     * @param node
     */
    fun parser(nodeList: NodeList?) {
        this.clear()
        if (nodeList != null) {
            val count = nodeList.length
            if (count > 0) {
                for (i in 0 until count) {
                    val node = nodeList.item(i)
                    if (node.nodeName == "Item") {
                        val item = DiagDtcSymptomItem()
                        item.parser(node)
                        itens.add(item)
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return "DiagDtcSymptom(itens=$itens)"
    }

    companion object {
        const val DiagItemSymptomNum = "Symptom Number"
        const val DiagItemSymptomMan = "Symptom Man"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}