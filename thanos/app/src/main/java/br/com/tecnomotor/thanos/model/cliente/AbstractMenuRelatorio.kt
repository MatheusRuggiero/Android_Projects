package br.com.tecnomotor.thanos.model.cliente

abstract class AbstractMenuRelatorio() {
    var id: Long = 0

    constructor(id: Long) : this() {
        this.id = id
    }

    open fun clear() {
        this.id = 0
    }

    override fun toString(): String {
        return "id=$id"
    }
}