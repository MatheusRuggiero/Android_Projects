package br.com.tecnomotor.rasther.module

import br.com.tecnomotor.rasther.xml.XmlBase
import org.w3c.dom.NodeList

class DiagInformations {
    var tips: String = ""
    var moduleDemoMode: Boolean = false
    var module: String = ""
    var versionModule: String = ""
    var dateModule: String = ""
    var dtcGlobalModule: String = ""
    var protocolModule: String = ""
    var initTimeout: Int = 0

    /**
     * Clear data
     */
    fun clear() {
        tips = ""
        moduleDemoMode = false
        module = ""
        versionModule = ""
        dateModule = ""
        dtcGlobalModule = ""
        protocolModule = ""
        initTimeout = 0
    }

    /**
     * Carrega o menu através de um nó do XML
     * @param nodeList
     */
    fun parser(nodeList: NodeList?) {
        this.clear()
        if (nodeList != null) {
            try {
                tips = XmlBase.getNodeByTag(nodeList, "TIPS")?.nodeValue.toString()
                moduleDemoMode = java.lang.Boolean.valueOf(XmlBase.getNodeByTag(nodeList, "ModuleDemoMode")?.nodeValue)
                module = XmlBase.getNodeByTag(nodeList, "Module")!!.nodeValue
                versionModule = XmlBase.getNodeByTag(nodeList, "VersionModule")!!.nodeValue
                dateModule = XmlBase.getNodeByTag(nodeList, "DateModule")!!.nodeValue
                dtcGlobalModule = XmlBase.getNodeByTag(nodeList, "DtcGlobalModule")?.nodeValue.toString()
                protocolModule = XmlBase.getNodeByTag(nodeList, "ProtocolModule")!!.nodeValue
                initTimeout = Integer.valueOf(XmlBase.getNodeByTag(nodeList, "InitTimeout")?.nodeValue!!) * 1000
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun toString(): String {
        return "DiagInformations(tips='$tips', moduleDemoMode=$moduleDemoMode, module='$module', versionModule='$versionModule', dateModule='$dateModule', dtcGlobalModule='$dtcGlobalModule', protocolModule='$protocolModule', initTimeout=$initTimeout)"
    }

    init {
        // TODO Auto-generated constructor stub
        this.clear()
    }
}