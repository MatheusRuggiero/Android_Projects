package br.com.tecnomotor.rasther.module

import org.w3c.dom.Node
import java.util.*

/**
 * Lista de manuais
 * @author rogerio
 */
class DiagMan {
    enum class Idioma {
        pt, es, en
    }

    /**
     * Items dos manuais em vários idiomas
     */
    var itens: ArrayList<DiagManItem> = ArrayList()

    /**
     * Clear data
     */
    fun clear() {
        itens = ArrayList()
    }

    /**
     * Se existe o manual em qualquer idioma
     * @return
     */
    val isExist: Boolean
        get() = this.check()

    /**
     * Se existe o manual em um idioma especifico
     * @param idioma
     * @return
     */
    fun isExist(idioma: String): Boolean {
        var res = false
        for (i in itens.indices) {
            if (itens[i].idioma == idioma
                    && itens[i].value != "") {
                res = true
                break
            }
        }
        return res
    }

    /**
     * Se existe o manual em um idioma especifico
     * @param idioma
     * @return
     */
    fun isExist(idioma: Idioma): Boolean {
        return this.isExist(idioma.toString())
    }

    /**
     * Retorna o manual em um idioma
     * @param idioma
     * @return
     */
    operator fun get(idioma: String): DiagManItem {
        var resItem = DiagManItem()
        for (i in itens.indices) {
            if (itens[i].idioma == idioma) {
                resItem = itens[i]
                break
            }
        }
        return resItem
    }

    operator fun get(idioma: Idioma): DiagManItem {
        return this[idioma.toString()]
    }

    /**
     * Verifica se os itens são válidos para não inserir manuais vazios
     * @return
     */
    fun check(): Boolean {
        var res = false
        for (i in itens.indices) {
            if (itens[i].value != "") {
                res = true
                break
            }
        }
        return res
    }

    /**
     * Carrega os dados através de um nó do XML
     * @param node
     */
    fun parser(node: Node?) {
        this.clear()
        if (node != null) {
            val count = node.childNodes.length
            if (count > 0) {
                for (i in 0 until count) {
                    val item = node.childNodes.item(i)
                    val manItem = DiagManItem()
                    manItem.parser(item)
                    itens.add(manItem)
                }
            }
        }
    }

    override fun toString(): String {
        return "DiagMan(itens=$itens, isExist=$isExist)"
    }

    /**
     * Construtor
     */
    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}