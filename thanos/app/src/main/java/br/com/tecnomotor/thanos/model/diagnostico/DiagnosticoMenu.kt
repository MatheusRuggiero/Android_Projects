package br.com.tecnomotor.thanos.model.diagnostico

import br.com.tecnomotor.thanos.database.messages.entity.MenuEntity

class DiagnosticoMenu(val id:Long, val nome: String)

fun MenuEntity.toDiagnosticoMenu(): DiagnosticoMenu {
    return DiagnosticoMenu(this.id ?: 0, this.valuePtLong ?: "")
}