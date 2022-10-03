package br.com.tecnomotor.thanos.util

/** Created Slides by Andreo **/

open class Slides(
    open val index: Int,
    open val titulo: String,
    open val descricao: String,
    open val icone : Int
)

/** Created by Rogério **/

class SlidesPermissoes(
    override val index: Int,
    override val titulo: String,
    override val descricao: String,
    override val icone : Int,
    val permissoes: ArrayList<String>
): Slides(index, titulo, descricao, icone)