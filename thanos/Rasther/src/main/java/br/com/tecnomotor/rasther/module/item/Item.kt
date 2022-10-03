package br.com.tecnomotor.rasther.module.item

/**
 * Define o item a ser adicionado na ListView
 * @author Caio Yassoyama
 */
class Item : Comparable<Item> {
    private var stringsValues: ArrayList<String>
    private var stringsKeys: ArrayList<String>
    private var integersValues: ArrayList<Int>
    private var integersKeys: ArrayList<String>
    private var booleansValues: ArrayList<Boolean>
    private var booleansKeys: ArrayList<String>
    private var compareKey: String

    constructor() {
        stringsValues = ArrayList()
        stringsKeys = ArrayList()
        integersValues = ArrayList()
        integersKeys = ArrayList()
        booleansValues = ArrayList()
        booleansKeys = ArrayList()
        compareKey = ""
    }

    constructor(another: Item) {
        stringsValues = ArrayList()
        stringsKeys = ArrayList()
        integersValues = ArrayList()
        integersKeys = ArrayList()
        booleansValues = ArrayList()
        booleansKeys = ArrayList()
        compareKey = ""
        for (i in 0 until another.getSizeKeys(STRING)) {
            addString(another.getStringKey(i),
                    another.getString(another.getStringKey(i)))
        }
        for (i in 0 until another.getSizeKeys(INTEGER)) {
            addInt(another.getIntKey(i),
                    another.getInt(another.getIntKey(i)))
        }
        for (i in 0 until another.getSizeKeys(BOOLEAN)) {
            addBoolean(another.getBooleanKey(i),
                    another.getBoolean(another.getBooleanKey(i)))
        }
    }

    fun getSizeKeys(option: Int): Int {
        return when (option) {
            STRING -> stringsKeys.size
            INTEGER -> integersKeys.size
            BOOLEAN -> booleansKeys.size
            else -> 0
        }
    }

    fun addString(key: String, value: String) {
        if (stringsKeys.contains(key)) {
            setString(key, value)
        } else {
            stringsKeys.add(key)
            stringsValues.add(value)
        }
    }

    fun getString(key: String): String {
        return if (stringsKeys.contains(key)) {
            stringsValues[stringsKeys.indexOf(key)]
        } else {
            ""
        }
    }

    fun getStringKey(index: Int): String {
        return if (index < stringsKeys.size) {
            stringsKeys[index]
        } else {
            ""
        }
    }

    private fun setString(key: String, value: String) {
        if (stringsKeys.contains(key)) {
            stringsValues[stringsKeys.indexOf(key)] = value
        }
    }

    fun containsString(key: String): Boolean {
        return stringsKeys.contains(key)
    }

    fun addInt(key: String, value: Int) {
        if (integersKeys.contains(key)) {
            setInt(key, value)
        } else {
            integersKeys.add(key)
            integersValues.add(value)
        }
    }

    fun getInt(key: String): Int {
        return if (integersKeys.contains(key)) {
            integersValues[integersKeys.indexOf(key)]
        } else {
            -1 // TODO será o valor mais correto a retornar? antes retornava null
        }
    }

    fun getIntKey(index: Int): String {
        return if (index < integersKeys.size) {
            integersKeys[index]
        } else {
            ""
        }
    }

    private fun setInt(key: String, value: Int) {
        if (integersKeys.contains(key)) {
            integersValues[integersKeys.indexOf(key)] = value
        }
    }

    fun containsInt(key: String): Boolean {
        return integersKeys.contains(key)
    }

    fun addBoolean(key: String, value: Boolean) {
        if (booleansKeys.contains(key)) {
            setBoolean(key, value)
        } else {
            booleansKeys.add(key)
            booleansValues.add(value)
        }
    }

    fun getBoolean(key: String): Boolean {
        return if (booleansKeys.contains(key)) {
            booleansValues[booleansKeys.indexOf(key)]
        } else {
            false // TODO verificar este retorno. Estava null
        }
    }

    fun getBooleanKey(index: Int): String {
        return if (index < booleansKeys.size) {
            booleansKeys[index]
        } else {
            ""
        }
    }

    private fun setBoolean(key: String, value: Boolean) {
        if (booleansKeys.contains(key)) {
            booleansValues[booleansKeys.indexOf(key)] = value
        }
    }

    fun containsBoolean(key: String): Boolean {
        return booleansKeys.contains(key)
    }

    override operator fun compareTo(another: Item): Int {
        return getString(compareKey).compareTo(
                another.getString(compareKey))
    }

    fun setComparationKey(key: String) {
        compareKey = key
    }

    companion object {
        private const val STRING = 0
        private const val INTEGER = 1
        private const val BOOLEAN = 2
    }
}