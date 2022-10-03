package br.com.tecnomotor.thanos.model.menu

abstract class AbstractMenu() {
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