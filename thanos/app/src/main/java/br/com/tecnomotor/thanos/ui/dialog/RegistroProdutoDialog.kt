package br.com.tecnomotor.thanos.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import br.com.tecnomotor.rasther.xml.XmlBase
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.service.Cliente
import br.com.tecnomotor.thanos.service.soap.KserverRasther
import br.com.tecnomotor.thanos.service.update.CountryItem
import br.com.tecnomotor.thanos.service.update.UpdateDownload.logger
import br.com.tecnomotor.thanos.util.validador.ValidaEmail
import br.com.tecnomotor.thanos.util.validador.ValidacaoPadrao
import br.com.tecnomotor.thanos.util.validador.Validador
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import java.util.*

@SuppressLint("InflateParams")
class RegistroProdutoDialog(
    private val context: Context
) : Observer, XmlBase() {

    private val paises: ArrayList<String> = arrayListOf("")
    private val validadores: ArrayList<Validador> = ArrayList();
    private val serverRasther: KserverRasther = KserverRasther() // soap
    private var cliente: Cliente? = null
    private var serialNumber: String? = null
    private var md5SerialNumber: String? = null
    private lateinit var viewDialog: View
    private lateinit var spinner: ProgressBar
    private lateinit var nome: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var selecionarPais: TextInputLayout
    private lateinit var paisSelecionado: AutoCompleteTextView
    private lateinit var ddiTelefone: TextInputLayout
    private lateinit var nTelefone: TextInputLayout
    private lateinit var botaoAtualizar: Button
    private var listaPaises: ArrayList<CountryItem> = arrayListOf()
    private val COUNTRY_FILE_NAME = "country.xml"

    fun mostra() {
        inicializaView()
        inicializaSpinner()
        configuraDialog()
        carregaPaises()
        inicializaCampos()
        carregaValoresRasther()
        configuraBotaoAtualizar()
    }

    private fun inicializaView() {
        viewDialog =
            LayoutInflater.from(context).inflate(R.layout.dialog_registro_produto, null)
    }

    private fun carregaPaises() {
        openXml(context, COUNTRY_FILE_NAME)
        listaPaises.clear()
        tagsList.filter { it != "#text" }.forEach {
            val countryItem = CountryItem()
            val nodeList1 = getNodeListByTag(it)
            countryItem.setCode2Digits(it)
            countryItem.setCode3Digits(XmlBase.getNodeByTag(nodeList1, "CODE")?.nodeValue)
            countryItem.setNameEn(XmlBase.getNodeByTag(nodeList1, "NAMEEN")?.nodeValue)
            countryItem.setNameEs(XmlBase.getNodeByTag(nodeList1, "NAMEES")?.nodeValue)
            countryItem.setNamePt(XmlBase.getNodeByTag(nodeList1, "NAMEPT")?.nodeValue)
            countryItem.setPhone(XmlBase.getNodeByTag(nodeList1, "PHONE")?.nodeValue)
            listaPaises.add(countryItem)
        }
    }

    private fun inicializaCampos() {
        configuraCampoNome()
        configuraCampoEmail()
        configuraCampoPais()
        configuraCampoDdiTelefone()
        configuraCampoTelefone()
    }

    private fun inicializaSpinner() {
        spinner = viewDialog.findViewById(R.id.registro_produto_loading_spinner)
    }

    private fun configuraBotaoAtualizar() {
        botaoAtualizar = viewDialog.findViewById(R.id.registro_produto_botao_atualizar)
        botaoAtualizar.setOnClickListener {
            enviaDadosAtualizados()
        }
    }

    private fun configuraCampoTelefone() {
        nTelefone = viewDialog.findViewById(R.id.registro_produto_telefone)
        adicionaValidacaoPadrao(nTelefone)
    }

    private fun configuraCampoDdiTelefone() {
        ddiTelefone = viewDialog.findViewById(R.id.registro_produto_telefone_ddi)
        adicionaValidacaoPadrao(ddiTelefone)
    }

    private fun configuraCampoPais() {
        selecionarPais = viewDialog.findViewById(R.id.registro_produto_pais)
        paisSelecionado = viewDialog.findViewById(R.id.registro_produto_autocomplete)
        adicionaValidacaoPadrao(selecionarPais)
    }

    private fun configuraCampoEmail() {
        email = viewDialog.findViewById(R.id.registro_produto_email)
        val campoEmail = email.editText
        val validador = ValidaEmail(email)
        validadores.add(validador)
        campoEmail?.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validador.estaValido()
            }
        }
    }

    private fun configuraCampoNome() {
        nome = viewDialog.findViewById(R.id.registro_produto_nome)
        adicionaValidacaoPadrao(nome)
    }

    private fun adicionaValidacaoPadrao(textInputLayout: TextInputLayout) {
        val campo = textInputLayout.editText
        val validador = ValidacaoPadrao(textInputLayout)
        validadores.add(validador)
        campo?.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validador.estaValido()
            }
        }
    }

    private fun enviaDadosAtualizados() {
        spinner.isVisible = true
        Toast.makeText(context, "Atualizando Registro...", Toast.LENGTH_LONG).show()
        atualizaObjCliente()

//        Log.i(
//            TAG, "objCliente: ${cliente} " +
//                    "nome: ${nome.text} " +
//                    "email: ${email.text} " +
//                    "pais: ${paisSelecionado.text} " +
//                    "telefone: ${nTelefone.text} "
//        )

//        serverRasther.setCliente(cliente) // TODO analisar envio dos dados
        spinner.isVisible = false
    }

    private fun atualizaObjCliente() {
//        cliente?.setProperty(1, nome.text.toString())
//        cliente?.setNome(nome.text.toString())
//        cliente?.setNome(nome.text.toString())
//        cliente?.setEmail(email.text.toString())
//        cliente?.setPais(paisSelecionado.text.toString())
//        cliente?.setTel(nTelefone.text.toString())
    }

    private fun configuraDialog() {
        MaterialAlertDialogBuilder(context)
            .setView(viewDialog)
            .setCancelable(false)
            .setPositiveButton("Fechar") { _, _ -> }
            .show()
    }

    private fun carregaValoresRasther() {
        serverRasther.addObserver(this)
//        ConfigDevice.getInstance(context).
        serialNumber = "900025"
        md5SerialNumber =
            "f5eb271bcac63e94ba481f3b765e07e3"//TODO Md5Helper.getMD5EncryptedString(serialNumber)
        serverRasther.getCliente(serialNumber, md5SerialNumber) // soap
    }

    private fun carregaValoresCampos() {
        val (telSemDdi: String, ddi: String) = separandoCampoTelefoneEDdi()
        nome.editText?.setText(cliente?.nome)
        email.editText?.setText(cliente?.email)
        paisSelecionado.setText(cliente?.pais)
        nTelefone.editText?.setText(telSemDdi)
        ddiTelefone.editText?.setText(ddi)
        carregaListaDePaises()
        carregaPaisSelecionadoComDDI()
        spinner.isVisible = false
    }

    private fun separandoCampoTelefoneEDdi(): Pair<String, String> {
        val telCompleto: String = cliente?.tel.toString()
        var telSemDdi: String
        var ddi: String
        // separa campo telefone em DDI e telefone
        val idxCh: Int = telCompleto.indexOf("-")
        if (idxCh >= 0 && idxCh < telCompleto.length) {
            ddi = telCompleto.substring(0, idxCh)
            telSemDdi = telCompleto.substring(idxCh + 1)
        } else {
            ddi = ""
            telSemDdi = ""
        }
        return Pair(telSemDdi, ddi)
    }

    private fun carregaPaisSelecionadoComDDI() {
        paisSelecionado.setOnItemClickListener { _, _, position, _ ->
            if (position != 0) {
                ddiTelefone.editText?.setText("+" + listaPaises[position - 1].getPhone())
            } else {
                ddiTelefone.editText?.setText("")
            }
        }
    }

    private fun carregaListaDePaises() {
        listaPaises.forEach {
            paises.add(it.getCode2Digits().toString())
        }
        val adapterSelectPais = ArrayAdapter(context, R.layout.select_list_item, paises)
        (selecionarPais.editText as? AutoCompleteTextView)?.setAdapter(adapterSelectPais)
    }

    override fun update(observable: Observable?, data: Any?) {
//        println("UPDATE REGISTRO")
//        println("UPDATE REGISTRO: ${data.toString()}")
        if (observable != null) {
            if (observable.javaClass == KserverRasther::class.java) {
                if (data != null) {
                    if (data.javaClass == Cliente::class.java) {
                        cliente = data as Cliente
                        carregaValoresCampos()
                    } else {
                        logger.w(RegistroProdutoDialog.TAG, "data.toString(): $data")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "RegistroProdutoDialog"
    }

}