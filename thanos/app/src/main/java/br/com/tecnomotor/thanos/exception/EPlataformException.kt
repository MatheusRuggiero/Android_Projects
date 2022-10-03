package br.com.tecnomotor.thanos.exception

open class EPlatformException(message: String): Exception(message)
class EPlatformIdNotFoundInDatabase(message: String): EPlatformException(message)