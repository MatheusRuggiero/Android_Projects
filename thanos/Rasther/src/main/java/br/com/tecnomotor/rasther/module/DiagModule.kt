package br.com.tecnomotor.rasther.module

import android.content.Context
import br.com.tecnomotor.rasther.security.Cryptography
import br.com.tecnomotor.rasther.utils.AppPaths
import br.com.tecnomotor.rasther.utils.ZlibUtils
import br.com.tecnomotor.rasther.xml.XmlBase
import br.com.tecnomotor.security.Security
import org.w3c.dom.NodeList
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.File
import java.io.FileNotFoundException
import java.util.zip.DataFormatException

class DiagModule(private val context: Context) : XmlBase() {

    private var fileNameModule: String = ""
    private var fileNameDtc: String = ""      // dtc global da montadora
    private var fileNameDtcOBDII: String = "" // dtc global OBD II

    enum class TypeModule {
        Module, Dtc, DtcOBDII
    }

    /**
     * Descriptografa um módulo e retorna o nome do xml contendo o módulo
     * @param module Número do módulo
     * @param type Tipo do módulo
     * @return Nome do arquivo xml contendo o módulo
     * @throws DataFormatException Senha errada ou algum outro erro na descriptografia
     */
    @Throws(DataFormatException::class)
    fun loadModule(module: Int, modulePass: String, type: TypeModule = TypeModule.Module): String {

        if (module > 1000) {
            val key: String = Security().composePassModule(module, modulePass)

            val data: ByteArray = Cryptography.decriptFileToByte(
                File(
                    context.getDir(AppPaths.DIR_ATECS, Context.MODE_PRIVATE),
                    "$module.atec"
                ), key, module
            )
            if (data.isNotEmpty()) {
                val fileTmpName = "tmp$type.xml"
                val retName = ZlibUtils.decompressToInternalFile(context, data, fileTmpName)
                when(type) {
                    TypeModule.Module -> fileNameModule = retName
                    TypeModule.Dtc -> fileNameDtc = retName
                    TypeModule.DtcOBDII -> fileNameDtcOBDII = retName
                }
                return if (fileTmpName == retName)
                        fileTmpName
                    else ""
            }
        }
        return ""
    }

    /**
     * Abre os três módulos: Aplicação, DTC global da montadora e DTC  OBD II
     * @param module Número do módulo
     * @throws DataFormatException Senha errada ou algum outro erro na descriptografia
     */
    @Throws(DataFormatException::class)
    fun loadModules(module: Int, modulePass: String) {
        fileNameModule = loadModule(module, Security().composePassModule(module, modulePass), TypeModule.Module)
        fileNameDtcOBDII = loadModule(1511, Security().composePassModule(module, modulePass), TypeModule.DtcOBDII)
        if (informations != null) {
            val moduleDtcGlobal : String = informations!!.dtcGlobalModule
            fileNameDtc = loadModule(moduleDtcGlobal.toInt(), Security().composePassModule(module, modulePass), TypeModule.Dtc)
        }
    }

    private fun resetModule() {
        fileNameModule = ""
        fileNameDtc = ""
        fileNameDtcOBDII = ""
    }

    val informations: DiagInformations?
        get() {
            val info = DiagInformations()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("Informations")
            if (node != null) info.parser(node)
            return info
        }
    val menu: DiagMenu?
        get() {
            val menu = DiagMenu()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("Menu")
            if (node != null) menu.parser(node)
            return menu
        }
    val screenMan: DiagScreenMan?
        get() {
            val screenMan = DiagScreenMan()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("SCREENMAN")
            if (node != null) screenMan.parser(node)
            return screenMan
        }
    val ecu: DiagEcu?
        get() {
            val ecu = DiagEcu()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("Ecu")
            if (node != null) ecu.parser(node)
            return ecu
        }
    val softwareInit: DiagSoftwareInit?
        get() {
            val softwareInit = DiagSoftwareInit()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("SoftwareInit")
            if (node != null) softwareInit.parser(node)
            return softwareInit
        }
    val softwareDevice: DiagSoftwareDevice?
        get() {
            val softwareDevice = DiagSoftwareDevice()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("SoftwareDevice")
            if (node != null) softwareDevice.parser(node)
            return softwareDevice
        }
    val dtc: DiagDtc?
        get() {
            val dtc = DiagDtc()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("DTC")
            if (node != null) dtc.parser(node)
            return dtc
        }
    val dtcGlobal: DiagDtc?
        get() {
            val dtc = DiagDtc()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameDtc)
            val node: NodeList? = getNodeListByTag("DTC")
            if (node != null) dtc.parser(node)
            return dtc
        }
    val dtcOBDII: DiagDtc?
        get() {
            val dtc = DiagDtc()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameDtcOBDII)
            val node: NodeList? = getNodeListByTag("DTC")
            if (node != null) dtc.parser(node)
            return dtc
        }
    val dtcSymptom: DiagDtcSymptom?
        get() {
            val dtcSymptom = DiagDtcSymptom()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("DTCSymptom")
            if (node != null) dtcSymptom.parser(node)
            return dtcSymptom
        }
    val value: DiagValue?
        get() {
            val value = DiagValue()
            if (fileNameModule == "") return null
            this.openXml(context, fileNameModule)
            val node: NodeList? = getNodeListByTag("Value")
            if (node != null) value.parser(node)
            return value
        }
    val xmlDiagModule: XmlPullParser?
        get() {
            try {
                val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = true
                val xmlPullParser: XmlPullParser = factory.newPullParser()
                xmlPullParser.setInput(context.openFileInput(fileNameModule), null)
                return xmlPullParser
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    val xmlDiagDTC: XmlPullParser?
        get() {
            try {
                val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = true
                val xmlPullParser: XmlPullParser = factory.newPullParser()
                xmlPullParser.setInput(context.openFileInput(fileNameDtc), null)
                return xmlPullParser
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return null
        }
    val xmlDiagDTC_OBDII: XmlPullParser?
        get() {
            try {
                val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
                factory.isNamespaceAware = true
                val xmlPullParser: XmlPullParser = factory.newPullParser()
                xmlPullParser.setInput(context.openFileInput(fileNameDtcOBDII), null)
                return xmlPullParser
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

    /**
     * Verifica se o arquivo de um módulo de tipo específico existe
     * (Parece estar inconsistente dentro do when)
     */
    fun isExist(type: TypeModule): Boolean {
        var file: File? = null
        var res = false
        when (type) {
            TypeModule.Module -> {
                file = File(fileNameModule)
                file = File(fileNameDtc)
                file = File(fileNameDtcOBDII)
            }
            TypeModule.Dtc -> {
                file = File(fileNameDtc)
                file = File(fileNameDtcOBDII)
            }
            TypeModule.DtcOBDII -> file = File(fileNameDtcOBDII)
        }
        res = file.exists()
        return res
    }

    override fun toString(): String {
        return "DiagModule(fileNameModule=$fileNameModule,\n contextLoaded=$context,\n fileNameDtc=$fileNameDtc,\n fileNameDtcOBDII=$fileNameDtcOBDII,\n informations=$informations,\n menu=$menu,\n screenMan=$screenMan,\n ecu=$ecu,\n softwareInit=$softwareInit,\n softwareDevice=$softwareDevice,\n dtc=$dtc,\n dtcGlobal=$dtcGlobal,\n dtcOBDII=$dtcOBDII,\n dtcSymptom=$dtcSymptom,\n value=$value,\n xmlDiagModule=$xmlDiagModule,\n xmlDiagDTC=$xmlDiagDTC,\n xmlDiagDTC_OBDII=$xmlDiagDTC_OBDII)"
    }

    init {
        resetModule()
    }
}