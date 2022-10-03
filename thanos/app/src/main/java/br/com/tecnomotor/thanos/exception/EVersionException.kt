package br.com.tecnomotor.thanos.exception

open class EVersionException(message: String): Exception(message)
class EVersionIdNotFoundInDatabase(message: String): EVersionException(message)