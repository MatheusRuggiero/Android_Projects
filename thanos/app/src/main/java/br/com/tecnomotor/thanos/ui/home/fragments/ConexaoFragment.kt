package br.com.tecnomotor.thanos.ui.home.fragments

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import br.com.tecnomotor.thanos.NavGraphHomeDirections
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.IntroSliderAdapter
import br.com.tecnomotor.thanos.ui.home.viewmodel.HomeViewModel
import br.com.tecnomotor.thanos.util.Slides
import kotlinx.android.synthetic.main.fragment_conexao.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class ConexaoFragment : Fragment() {

    private val REQUEST_ENABLE_BT = 100

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private val controlador by lazy {
        findNavController()
    }

    private lateinit var introSliderAdapter: IntroSliderAdapter

    fun getIntroSliderAdapter() = IntroSliderAdapter(
        listOf(
            Slides(
                0,
                "Bluetooth",
                "O seu bluetooth não está ativo, é através dele que o aplicativo se conecta com a VCI. Clique no botão para ativar!",
                R.drawable.ic_bluetooth_cellphone
            ),
            Slides(
                1,
                "Encontrar um VCI",
                "Antes de conectar precisamos localizar a sua VCI, conecte sua VCI em um conector OBDII ou utilize um cabo USB-C conectado a uma fonte de alimentação. Clique no botão para encontra-la",
                R.drawable.ic_vci_android
            ),
            Slides(
                2,
                "Tudo pronto!",
                "Parabéns!!! Sua VCI já está conectada!",
                R.drawable.ic_pronto_vci
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conexao, container, false)
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        introSliderAdapter = getIntroSliderAdapter()
        conexao_viewpager.adapter = introSliderAdapter
        conexao_viewpager.isUserInputEnabled = false

        setupIndicators()
        setCurrentIndicator(0)
        setPageSelectedIndicator()
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun onResume() {
        super.onResume()

        if (((conexao_viewpager.currentItem == 0) && (viewModel.isBTEnabled())) ||
            (viewModel.isConnected())) {
            val handle = Handler(Looper.getMainLooper())
            handle.postDelayed({nextPage()}, 1)
        } else setPage(conexao_viewpager.currentItem)
    }

    private fun setPageSelectedIndicator() {
        conexao_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        buttonNext()
    }

    private fun nextPage() {
        val nextPosition = conexao_viewpager.currentItem + 1
        if (nextPosition < introSliderAdapter.itemCount) {
            setPage(nextPosition)
        }
    }

    private fun setPage(indexPage: Int) {
        conexao_viewpager.currentItem = indexPage
        when (indexPage) {
            1 -> {
                conexao_btn_acao.text = getString(R.string.localizar)
            }
            2 -> {
                conexao_btn_acao.text = getString(R.string.proximo)
            }
        }
    }

    private fun buttonNext() {
        conexao_btn_acao.setOnClickListener {
            when(conexao_viewpager.currentItem) {
                0 -> { //Ativar bluetooth
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
                }
                1 -> { //Localizar uma VCI
                    controlador.navigate(ConexaoFragmentDirections.actionConexaoFragmentToConnectDeviceFragment())
                }
                2 -> { //Finalizar
                    controlador.popBackStack()
                    controlador.navigate(NavGraphHomeDirections.actionGlobalHomeFragment())
                }
            }
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            conexao_indicators.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = conexao_indicators.childCount
        for (i in 0 until childCount) {
            val imageView = conexao_indicators[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(), R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(this.javaClass.simpleName, "requestCode: $requestCode, resultCode: $resultCode")
        if (requestCode == REQUEST_ENABLE_BT) {
           if (viewModel.isBTEnabled()) nextPage()
        }
    }
}