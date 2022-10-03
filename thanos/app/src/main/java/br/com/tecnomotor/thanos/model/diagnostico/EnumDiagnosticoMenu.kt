package br.com.tecnomotor.thanos.model.diagnostico

enum class EnumDiagnosticoMenu(val id: Long) {
    APAGA_MEMORIA(6),
    IDENTIFICACAO_ECU(7),
    CODIGO_DEFEITO(8),
    LEITURAS(9),
    ANALISE_GRAFICA(12),
    ATUADORES(13),
    AJUSTES(14),
    PROGRAMACAO(15);
}