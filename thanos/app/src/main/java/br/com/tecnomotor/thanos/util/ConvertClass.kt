package br.com.tecnomotor.thanos.util

import com.google.gson.Gson

class ConvertClass {
    companion object {
        fun convert(value: Any, classType: String): Any {
            val valueJson = classToJson(value)
            return jsonToClass(valueJson, classType)
        }
        fun classToJson(value: Any):String {
            return Gson().toJson(value)
        }
        fun jsonToClass(value: String, classType: String): Any {
            return Gson().fromJson(value, Class.forName(classType))
        }
    }
}