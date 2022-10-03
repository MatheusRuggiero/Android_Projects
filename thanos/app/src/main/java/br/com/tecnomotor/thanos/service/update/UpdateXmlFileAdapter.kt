package br.com.tecnomotor.thanos.service.update

//import com.eos.rastherandroid.utils.Logger
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

object UpdateXmlFileAdapter {
    @Throws(XmlPullParserException::class, IOException::class)
    fun getXmlFilenameMD5(xmlParser: XmlPullParser): ArrayList<UpdateFile> {
        var eventType = xmlParser.eventType
        var fileName: String? = ""
        var directory: String? = ""
        var md5: String? = ""
        var type = UpdateFileType.NONE
        var priority = 0
        var tagName: String
        var value: String? = null
        val updateFileList = ArrayList<UpdateFile>()
        while (eventType != XmlPullParser.END_DOCUMENT) {
            tagName = xmlParser.name
            when (eventType) {
                XmlPullParser.START_TAG -> {
                }
                XmlPullParser.TEXT -> value = xmlParser.text.replace("\n", "").trim { it <= ' ' }
                XmlPullParser.END_TAG -> if (tagName == "FileName") fileName =
                    value else if (tagName == "Directory") directory =
                    value else if (tagName == "MD5") md5 = value else if (tagName == "Type") type =
                    UpdateFileType.valueOf(
                        value!!
                    ) else if (tagName == "Priority") priority =
                    Integer.valueOf(value) else if (tagName == "File") {
                    val updateFile = UpdateFile(fileName, directory, md5, type, priority)
                    updateFileList.add(updateFile)
                    //Logger.d("UpdateXmlFileAdapter", "updateFileList: $updateFile")
                }
                else -> {
                }
            }
            eventType = xmlParser.next()
        }
        return updateFileList
    }
}