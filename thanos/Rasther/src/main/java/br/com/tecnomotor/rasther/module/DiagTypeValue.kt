package br.com.tecnomotor.rasther.module

import org.w3c.dom.Node

/**
 * DiagTypeValue
 * @author rogerio
 */
class DiagTypeValue {
    inner class AnalisisGraph {
        var enabled: Boolean = false
        var min = 0.0
        var max = 0.0
        fun clear() {
            enabled = false
            min = 0.0
            max = 0.0
        }

        fun parser(node: Node?) {
            this.clear()
            if (node != null) {
                val count = node.childNodes.length
                if (count > 0) {
                    for (i in 0 until count) {
                        val item = node.childNodes.item(i)
                        if (item.nodeName == "Enabled") enabled = java.lang.Boolean.valueOf(item.childNodes.item(0).nodeValue)
                        if (item.nodeName == "Min") min = item.childNodes.item(0).nodeValue.replace(",".toRegex(), ".").toDouble()
                        if (item.nodeName == "Max") max = item.childNodes.item(0).nodeValue.replace(",".toRegex(), ".").toDouble()
                    }
                }
            }
        }

        override fun toString(): String {
            return "AnalisisGraph(enabled=$enabled)"
        }

        init {
            this.clear()
        }
    }

    inner class Range {
        var enabled: Boolean = false
        var min = 0.0
        var max = 0.0
        fun clear() {
            enabled = false
            min = 0.0
            max = 0.0
        }

        fun parser(node: Node?) {
            this.clear()
            if (node != null) {
                val count = node.childNodes.length
                if (count > 0) {
                    for (i in 0 until count) {
                        val item = node.childNodes.item(i)
                        if (item.nodeName == "Enabled") enabled = java.lang.Boolean.valueOf(item.childNodes.item(0).nodeValue)
                        if (item.nodeName == "Min") min = item.childNodes.item(0).nodeValue.replace(",".toRegex(), ".").toDouble()
                        if (item.nodeName == "Max") max = item.childNodes.item(0).nodeValue.replace(",".toRegex(), ".").toDouble()
                    }
                }
            }
        }

        override fun toString(): String {
            return "Range(enabled=$enabled, min=$min, max=$max)"
        }

        init {
            this.clear()
        }
    }

    var typeName: String = ""
    var unit: Int = -1
    var dp: Int = -1
    var analisisGraph: AnalisisGraph = AnalisisGraph()
    var range: Range = Range()

    /**
     * Clear data
     */
    fun clear() {
        typeName = ""
        unit = -1
        dp = -1
        analisisGraph = AnalisisGraph()
        range = Range()
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
                    if (item.nodeName == "TypeName") typeName = item.childNodes.item(0).nodeValue
                    if (item.nodeName == "Unit") unit = Integer.valueOf(item.childNodes.item(0).nodeValue)
                    if (item.nodeName == "DP") dp = Integer.valueOf(item.childNodes.item(0).nodeValue)
                    if (item.nodeName == "AnalisisGraph") analisisGraph.parser(item)
                    if (item.nodeName == "Range") range.parser(item)
                }
            }
        }
    }

    override fun toString(): String {
        return "DiagTypeValue(typeName=$typeName, unit=$unit, dp=$dp, analisisGraph=$analisisGraph, range=$range)"
    }

    /**
     * Construtor
     */
    init {
        this.clear()
    }
}