package br.com.tecnomotor.rasther

import android.content.Context
import br.com.tecnomotor.rasther.xml.BootIdConfig
import br.com.tecnomotor.rasther.xml.ConectorConfig
import br.com.tecnomotor.rasther.xml.XmlBase
import org.w3c.dom.NodeList

class EquipamentoConfig(private val context: Context) : XmlBase() {

    fun getBootID(id: Int): BootIdConfig? {
        var bootIdConfig: BootIdConfig? = null
        openXml(context, "config.xml")
        val nodeList: NodeList? = getNodeListByTag("BootId")
        val tagList = getTagsList("BootId")
        tagList.forEach {
            //println(it)
        }
        if (nodeList != null) {
            val nodeListId = getNodeListByTag(nodeList, "ID$id")

            val nodeNome = getNodeByTag(nodeListId,"Nome")?.nodeValue
            val nodeTipo = getNodeByTag(nodeListId,"Tipo")?.nodeValue?.toInt()
            val nodeAtua = getNodeByTag(nodeListId,"Atua")?.nodeValue?.toInt()

            bootIdConfig = BootIdConfig(id,nodeNome!!,nodeTipo!!,nodeAtua!!)
        }
        return bootIdConfig
    }

    fun getConectorID(id: Int): ConectorConfig? {
        var conectorConfig: ConectorConfig? = null
        openXml(context, "config.xml")
        val nodeList: NodeList? = getNodeListByTag("Conector")
        val tagList = getTagsList("Conector")
        tagList.forEach {
            //println(it)
        }
        if (nodeList != null) {
            val nodeListId = getNodeListByTag(nodeList, "ID$id")

            val nodeNome = getNodeByTag(nodeListId,"Name")?.nodeValue.toString()
            val nodeBat =  getNodeByTag(nodeListId,"BAT")?.nodeValue?.toString() ?: ""
            val nodeGnd =  getNodeByTag(nodeListId,"GND")?.nodeValue?.toString() ?: ""
            val nodePIN1 =  getNodeByTag(nodeListId,"PIN1")?.nodeValue?.toString() ?: "D0"
            val nodePIN2  =  getNodeByTag(nodeListId,"PIN2")?.nodeValue?.toString() ?: "D0"
            val nodePIN3  =  getNodeByTag(nodeListId,"PIN3")?.nodeValue?.toString() ?: ""

            val nodePIN6  =  getNodeByTag(nodeListId,"PIN6")?.nodeValue?.toString() ?: ""
            val nodePIN7  =  getNodeByTag(nodeListId,"PIN7")?.nodeValue?.toString() ?: ""
            val nodePIN8  =  getNodeByTag(nodeListId,"PIN8")?.nodeValue?.toString() ?: ""
            val nodePIN9  =  getNodeByTag(nodeListId,"PIN9")?.nodeValue?.toString() ?: ""
            val nodePIN10  =  getNodeByTag(nodeListId,"PIN10")?.nodeValue?.toString() ?: ""
            val nodePIN11  =  getNodeByTag(nodeListId,"PIN11")?.nodeValue?.toString() ?: ""
            val nodePIN12  =  getNodeByTag(nodeListId,"PIN12")?.nodeValue?.toString() ?: ""
            val nodePIN13  =  getNodeByTag(nodeListId,"PIN13")?.nodeValue?.toString() ?: ""
            val nodePIN14  =  getNodeByTag(nodeListId,"PIN14")?.nodeValue?.toString() ?: ""
            val nodePIN15  =  getNodeByTag(nodeListId,"PIN15")?.nodeValue?.toString() ?: ""

            val pinList: Array<String> = arrayOf(nodePIN1,nodePIN2,nodePIN3,nodePIN6,nodePIN7,nodePIN8,nodePIN9,nodePIN10,nodePIN11,nodePIN12,nodePIN13,nodePIN14,nodePIN15)

            println(pinList.contentToString())

            conectorConfig = ConectorConfig(id,nodeNome,nodeBat,nodeGnd,pinList)
            println(conectorConfig.toString())

        }
        return conectorConfig
    }

    fun getProtocol(id: Int): String {
        var protocolo = ""
        openXml(context, "config.xml")
        val nodeList: NodeList? = getNodeListByTag("Protocol")
        val tagList = getTagsList("Protocol")
        tagList.forEach {
           // println(it)
        }
            if (nodeList != null) {
                     protocolo = getNodeByTag(nodeList,"ID$id")?.nodeValue.toString()
            }
        return protocolo
    }
}