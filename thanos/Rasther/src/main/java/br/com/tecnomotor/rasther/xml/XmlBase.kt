package br.com.tecnomotor.rasther.xml

import android.content.Context
import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
import java.io.IOException
import java.util.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

/**
 * Classe responsável pela manipulação de arquivos XML
 * @author Rogerio
 */
open class XmlBase {
    /**
     * Variável de manipulação do xml
     */
    var doc: Document? = null

    /**
     * Abertura do XML
     * @param context Contexto da activity
     * @param fileName Nome do arquivo XML
     */
    fun openXml(context: Context, fileName: String) {
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder: DocumentBuilder
        try {
            dBuilder = dbFactory.newDocumentBuilder()
            doc = dBuilder.parse(context.openFileInput(fileName))
            if (doc != null)
                doc!!.documentElement.normalize()
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
            throw ParserConfigurationException()
        } catch (e: SAXException) {
            e.printStackTrace()
            throw SAXException()
        } catch (e: IOException) {
            e.printStackTrace()
            throw IOException()
        }
    }

    /**
     * Fechando o XML
     */
    fun closeXml() {
        doc = null
    }

    /**
     * Obtem uma lista de tags do XML através de uma tag e a partir da raiz
     * @param tagName Nome da tag
     * @return ArrayList de String com as tags do XML
     */
    fun getTagsList(tagName: String): ArrayList<String> {
        val node: NodeList
        node =
            if (tagName == "") doc!!.firstChild.childNodes else doc!!.getElementsByTagName(tagName)
                .item(0).childNodes
        val counter = node.length
        val value = ArrayList<String>()
        for (i in 0 until counter) {
            val item = node.item(i)
            value.add(item.nodeName)
        }
        return value
    }

    /**
     * Obtem a lista de tags do XML a partir da raiz
     * @return
     */
    val tagsList: ArrayList<String>
        get() {
            return getTagsList("")
        }


    /**
     * Obtém uma lista de Nós pela tag
     * @param tagName Nome da tag
     * @return Lista de nós
      */
    fun getNodeListByTag(tagName: String): NodeList? {
        return if (this.doc == null) null else getNodeListByTag(
            doc!!.firstChild.childNodes,
            tagName
        )
    }

    companion object {
        /**
         * Obtem uma lista de nós através de uma tag
         * @param nodeList Lista de Nós
         * @param tagName Nome da tag
         * @return Lista de nós retornados
         */
        fun getNodeListByTag(nodeList: NodeList?, tagName: String): NodeList? {
            return if (nodeList == null || tagName == "") null else {
                var nodeReturn: NodeList? = null
                val counter = nodeList.length
                for (i in 0 until counter) {
                    val item = nodeList.item(i)
                    if (item.nodeName == tagName) {
                        nodeReturn = item.childNodes
                        break
                    }
                }
                nodeReturn
            }
        }

        /**
         * Obtém um nó a partir da tag
         */
        fun getNodeByTag(nodeList: NodeList?, tagName: String): Node? {
            return if (nodeList == null || tagName == "") null else {
                var nodeReturn: Node? = null
                val counter = nodeList.length
                for (i in 0 until counter) {
                    val item = nodeList.item(i)
                    if (item.nodeName == tagName) {
                        nodeReturn = item.childNodes.item(0)
                        break
                    }
                }
                nodeReturn
            }
        }

        /**
         * Verifica se uma tag existe
         */
        fun isTagExist(nodeList: NodeList?, tagName: String): Boolean {
            return if (nodeList == null || tagName == "") false else {
                val node: Node? = getNodeByTag(nodeList, tagName)
                node != null
            }
        }
    }
}