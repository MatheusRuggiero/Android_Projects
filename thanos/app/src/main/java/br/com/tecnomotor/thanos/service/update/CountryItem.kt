package br.com.tecnomotor.thanos.service.update


class CountryItem {
    private var code2Digits: String? = null
    private var code3Digits: String? = null
    private var namePt: String? = null
    private var nameEn: String? = null
    private var nameEs: String? = null
    private var phone: String? = null

    @JvmName("getCode2Digits1")
    fun getCode2Digits(): String? {
        return code2Digits
    }

    fun setCode2Digits(code2Digits: String?) {
        this.code2Digits = code2Digits
    }

    @JvmName("getCode3Digits1")
    fun getCode3Digits(): String? {
        return code3Digits
    }

    @JvmName("setCode3Digits1")
    fun setCode3Digits(code3Digits: String?) {
        this.code3Digits = code3Digits
    }

    @JvmName("getNamePt1")
    fun getNamePt(): String? {
        return namePt
    }

    @JvmName("setNamePt1")
    fun setNamePt(namePt: String?) {
        this.namePt = namePt
    }

    @JvmName("getNameEn1")
    fun getNameEn(): String? {
        return nameEn
    }

    @JvmName("setNameEn1")
    fun setNameEn(nameEn: String?) {
        this.nameEn = nameEn
    }

    @JvmName("getNameEs1")
    fun getNameEs(): String? {
        return nameEs
    }

    @JvmName("setNameEs1")
    fun setNameEs(nameEs: String?) {
        this.nameEs = nameEs
    }

    @JvmName("getPhone1")
    fun getPhone(): String? {
        return phone
    }

    @JvmName("setPhone1")
    fun setPhone(phone: String?) {
        this.phone = phone
    }

    override fun toString(): String {
        return "CountryItem(code2Digits=$code2Digits, code3Digits=$code3Digits, namePt=$namePt, nameEn=$nameEn, nameEs=$nameEs, phone=$phone)"
    }
}