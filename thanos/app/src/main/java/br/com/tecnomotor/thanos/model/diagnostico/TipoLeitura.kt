package br.com.tecnomotor.thanos.model.diagnostico

enum class TipoLeitura() {
    None,
    Analogic,
    AnalogText,
    Digital,
    AnalogicVAG;

    /**
     * value = value de resposta da comunicação
     */

    companion object {
        fun valueOf(value: String): TipoLeitura {
            var res = TipoLeitura.None
            TipoLeitura.values().forEach {
                    res = it
            }
            return res
        }
    }

    fun tipoLeituras(){
        var tipoLeitura : TipoLeitura =  TipoLeitura.valueOf("Digital")
        println("Tipo: $tipoLeitura")
        tipoLeitura  =  TipoLeitura.valueOf("AnalogText")
        println("Tipo: $tipoLeitura")
        tipoLeitura  =  TipoLeitura.valueOf("Analogic")
        println("Tipo: $tipoLeitura")
        tipoLeitura  =  TipoLeitura.valueOf("AnalogicVag")
        println("Tipo: $tipoLeitura")

    }


}




















