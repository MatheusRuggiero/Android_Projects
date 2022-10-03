package br.com.tecnomotor.thanos.model.cliente

class Oficina {
    private var razaoSocial : String = ""
    private var CNPJ : String = ""
    private var site : String = ""
    private var email : String = ""
    private var telefone : String = ""
    private var endereco : String = ""

    fun getRazaoSocial() : String {
        return this.razaoSocial
    }

    fun getCNPJ() : String {
        return this.CNPJ
    }

    fun getSite() : String {
        return this.site
    }

    fun getEmail() : String {
        return this.email
    }

    fun getTelefone() : String{
        return this.telefone
    }

    fun getEndereco() : String{
        return this.endereco
    }
}