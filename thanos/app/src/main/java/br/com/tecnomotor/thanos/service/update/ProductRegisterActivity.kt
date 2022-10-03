package br.com.tecnomotor.thanos.service.update

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import br.com.tecnomotor.thanos.service.Cliente
import br.com.tecnomotor.thanos.service.soap.KserverRasther
import br.com.tecnomotor.thanos.service.update.toexclude.RastherDefaultActivity
import br.com.tecnomotor.thanos.service.update.toexclude.Utils
import java.util.*

class ProductRegisterActivity : RastherDefaultActivity(), Observer {
    private val COUNTRY_FILE_NAME = "country.xml"
    private var spinnerPais: Spinner? = null
    private var editNome: EditText? = null
    private var editEmail: EditText? = null
    private var textDDI: TextView? = null
    private var editTelefone: EditText? = null
    private var countryList = ArrayList<CountryItem>()
    private var cliente: Cliente? = null
    private val serverRasther: KserverRasther = KserverRasther() // soap
    private var serialNumber: String? = null
    private var md5SerialNumber: String? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Utils.isNetworkAvailable(this)) {
            val intent = Intent()
            intent.putExtra(EXTRA_ERRO_REGISTRO_PRODUTO, UpdateNotifier.Companion.ERRO_SEM_REDE)
            setResult(RESULT_CANCELED, intent)
            finish()
        }
        showDialog()
        serverRasther.addObserver(this)
        serialNumber = sharedPreferences?.getString(PREFERENCES_SERIAL_NUMBER, "")
        md5SerialNumber = ""//TODO Md5Helper.getMD5EncryptedString(serialNumber)
        logger.d(TAG, "serialNumber: $serialNumber, md5: $md5SerialNumber")
        serverRasther.getCliente(serialNumber, md5SerialNumber) // soap
        setContentView(0/*R.layout.activity_product_register*/)
        getActionBar()?.setDisplayHomeAsUpEnabled(false)
        editNome = findViewById(0/*R.id.editTextNomeRP*/) as EditText?
        editEmail = findViewById(0/*R.id.editTextEmailRP*/) as EditText?
        textDDI = findViewById(0/*R.id.textViewDdiRP*/) as TextView?
        spinnerPais = findViewById(0/*R.id.spinnerPaisRP*/) as Spinner?
        editTelefone = findViewById(0/*R.id.editTextTelefoneRP*/) as EditText?
        try {
            // na primeira vez que executa utiliza o arquivo country.xml local
            countryList = arrayListOf() // TODO XmlReader.getCountries(Utils.getXmlParserFromInternalDir(this, COUNTRY_FILE_NAME, null))
        } catch (e: Exception) {
            e.message?.let { logger.w(TAG, it) }
        }
        val language: String? = sharedPreferences?.getString(PREFERENCES_LANGUAGE, "PT")
        var countryNamesList: ArrayList<String> = arrayListOf()// TODO = Utils.getCountryNameList(countryList, language)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, R.layout.simple_spinner_item,
            countryNamesList
        )
        spinnerPais?.setAdapter(adapter)
        val escolha: AdapterView.OnItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                val ddi: String = "+55" // TODO Utils.getPhoneByPosition(countryList, position)
                textDDI?.setText("+$ddi")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinnerPais?.setOnItemSelectedListener(escolha)
    }

    // copiado de DiagnosticActivity
    protected override fun showDialog() {
//        if (communicatingDialog != null) {
//            if (!communicatingDialog.isShowing()) {
//                communicatingDialog = Utils.showDialog(
//                    this, getResources()
//                        .getString(R.string.wait)
//                )
//            }
//        } else {
//            communicatingDialog = Utils.showDialog(
//                this, getResources()
//                    .getString(R.string.wait)
//            )
//        }
    }

    override fun onBackPressed() {} // não permite voltar
    fun onClickBtnOkProductRegister(v: View?) {
        atualizaObjCliente()
        setResult(RESULT_OK)
        finish()
    }

    fun onClickBtnCancelProductRegister(v: View?) {
        val intent = Intent()
        intent.putExtra(
            EXTRA_ERRO_REGISTRO_PRODUTO,
            UpdateNotifier.Companion.ERRO_REGISTRO_CANCELADO
        )
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    /**
     * Atualiza os dados do objeto cliente com os dados provenientes do formulário e do objeto cliente com dados do servidor
     */
    private fun atualizaObjCliente() {
//        // dados atualizados do formulário
//        UpgradeActivity.clienteAtualizado = Cliente()
//        UpgradeActivity.clienteAtualizado =
//            UpgradeActivity.clienteAtualizado.setNome(editNome.getText().toString())
//        UpgradeActivity.clienteAtualizado =
//            UpgradeActivity.clienteAtualizado.setEmail(editEmail.getText().toString())
//        UpgradeActivity.clienteAtualizado = UpgradeActivity.clienteAtualizado.setPais(
//            Utils.get2DigitsNameByPosition(
//                countryList,
//                spinnerPais.getSelectedItemPosition()
//            )
//        )
//        UpgradeActivity.clienteAtualizado = UpgradeActivity.clienteAtualizado.setTel(
//            textDDI.getText().toString() + "-" + editTelefone.getText().toString()
//        )
//        // do objeto cliente provenientes do servidor
//        UpgradeActivity.clienteAtualizado =
//            UpgradeActivity.clienteAtualizado.setNumSerie(cliente.getNumSerie())
//        UpgradeActivity.clienteAtualizado =
//            UpgradeActivity.clienteAtualizado.setEstado(cliente.getEstado())
//        UpgradeActivity.clienteAtualizado =
//            UpgradeActivity.clienteAtualizado.setCidade(cliente.getCidade())
//        UpgradeActivity.clienteAtualizado =
//            UpgradeActivity.clienteAtualizado.setValido(cliente.getValido())
//        UpgradeActivity.clienteAtualizado =
//            UpgradeActivity.clienteAtualizado.setKeySecurity(cliente.getKeySecurity())
    }

    /**
     * Atualiza os dados do formulário com os dados provenientes do objeto cliente
     */
    private fun carregaFormulario() {
        var nome: String
        var email: String
        var pais: String
        val ddi: String
        var telCompleto: String
        val tel: String
        var idxCountry: Int
        val idxCh: Int
        nome = cliente?.nome.toString()
        email = cliente?.email.toString()
        // índice para ser usado no spinner
        idxCountry = 0 // TODO Utils.getCountryPosition(countryList, cliente?.pais)
        logger.i(TAG, "idxCountry: $idxCountry")
        telCompleto = cliente?.tel.toString()
        if (nome == null) nome = ""
        if (email == null) email = ""
        // se não encontrar aquele país então procura índice do Brasil
        if (idxCountry == -1) {
            idxCountry = 0 // TODO Utils.getCountryPosition(countryList, "BR")
            // se não encontrar Brasil então usa a primeira posição do spinner
            if (idxCountry == -1) idxCountry = 0
        }
        if (telCompleto == null) telCompleto = ""

        // separa campo telefone em DDI e telefone
        idxCh = telCompleto.indexOf("-")
        if (idxCh >= 0 && idxCh < telCompleto.length) {
            ddi = telCompleto.substring(0, idxCh)
            tel = telCompleto.substring(idxCh + 1)
        } else {
            ddi = ""
            tel = ""
        }
        editNome?.setText(nome)
        editEmail?.setText(email)
        spinnerPais?.setSelection(idxCountry)
        textDDI?.text = ddi
        editTelefone?.setText(tel)
    }

    override fun update(observable: Observable, data: Any) {
        // Logger.w(TAG, "UPDATE:");
        if (observable == null || data == null) return
        if (observable.javaClass == KserverRasther::class.java) {
            if (data.javaClass == Cliente::class.java) {
                if (cliente != null) {
                    cliente = data as Cliente
//                    cliente =
//                        md5SerialNumber?.let { cliente!!.setKeySecurity(it) } // necessário para licença de hardware, setClient, entre outros
                    carregaFormulario()
                    dismissDialog()
                }
            } else {
                logger.w(TAG, "data.toString(): $data")
            }
        }
    }

    companion object {
        private const val TAG = "ProductRegisterActivity"
    }
}