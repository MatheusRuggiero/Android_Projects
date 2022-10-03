package br.com.tecnomotor.thanos.ui.home.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import br.com.tecnomotor.thanos.R
import br.com.tecnomotor.thanos.adapter.IntroSliderAdapter
import br.com.tecnomotor.thanos.config.ConfigApp
import br.com.tecnomotor.thanos.util.SlidesPermissoes
import kotlinx.android.synthetic.main.fragment_permissoes.*

class PermissoesFragment : Fragment() {

    companion object {
        val permissoes = arrayOf(
            Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private val controlador by lazy {
        findNavController()
    }

    private val ACCESS_PERMISSIONS = 201
    private var indexPermissao = 0
    private lateinit var slideList: ArrayList<SlidesPermissoes>
    private val configApp = ConfigApp.getInstance()

    private fun getSlideList() = arrayListOf<SlidesPermissoes>(
        SlidesPermissoes(
            0,
            getString(R.string.permissoes_titulo),
            getString(R.string.permissoes_descricao),
            R.drawable.ic_atencao,
            arrayListOf()
        ),
        SlidesPermissoes(
            1,
            getString(R.string.permissoes_politica_titulo),
            getString(R.string.permissoes_politica_descicao),
            R.drawable.ic_info_tec,
            arrayListOf()
        ),
        SlidesPermissoes(
            2,
            getString(R.string.permissoes_localizacao_titulo),
            getString(R.string.permissoes_localizacao_descricao),
            R.drawable.ic_location_image,
            arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION)
        ),
        SlidesPermissoes(
            3,
            getString(R.string.permissoes_armazenamento_titulo),
            getString(R.string.permissoes_armazenamento_descricao),
            R.drawable.ic_local_storage,
            arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_MEDIA_LOCATION)
        ),
        SlidesPermissoes(
            4,
            getString(R.string.permissoes_blutooth_titulo),
            getString(R.string.permissoes_bluetooth_descricao),
            R.drawable.ic_bluetooth_cellphone,
            arrayListOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
        ),
        SlidesPermissoes(
            5,
            getString(R.string.permissoes_finalizando_titulo),
            getString(R.string.permissoes_finalizando_descricao),
            R.drawable.ic_config_vci,
            arrayListOf()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permissoes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        slideList = getSlideList()
        frag_per_screen_viewpager.adapter = IntroSliderAdapter(slideList)
        frag_per_screen_viewpager.isUserInputEnabled = false

        setupIndicators()
        setCurrentIndicator(0)
        setPageSelectedIndicator()

        frag_per_btnLiberarPermissoes.setOnClickListener {
            indexPermissao = 0
            liberarPermissao(getPermissao())
        }
    }

    private fun getIndex():Int = frag_per_screen_viewpager.currentItem

    private fun setPageSelectedIndicator() {
        frag_per_screen_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        buttonNext()
    }

    private fun nextPage() {
        val nextPosition = frag_per_screen_viewpager.currentItem + 1
        if (nextPosition < slideList.size) {
            setPage(nextPosition)
        } else {
            controlador.navigate(PermissoesFragmentDirections.actionPermissoesFragmentToLicencaDeUsoFragment())
        }
    }

    private fun setPage(indexPage: Int) {
        frag_per_screen_viewpager.currentItem = indexPage
        if (slideList[indexPage].permissoes.size > 0)
            exibirBotaoLiberarPermissoes()
        else exibirBotaoProximo()
    }

    private fun buttonNext() {
        frag_per_btn_next.setOnClickListener {
            nextPage()
        }
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(slideList.size)
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
            frag_per_indicators.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = frag_per_indicators.childCount
        for (i in 0 until childCount) {
            val imageView = frag_per_indicators[i] as ImageView
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

    private fun exibirBotaoLiberarPermissoes() {
        val btnAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.button_animation)
        frag_per_btn_next.visibility = View.INVISIBLE
        frag_per_btnLiberarPermissoes.visibility = View.VISIBLE
        frag_per_btnLiberarPermissoes.animation = btnAnim
    }

    private fun exibirBotaoProximo() {
        frag_per_btn_next.visibility = View.VISIBLE
        frag_per_btnLiberarPermissoes.visibility = View.INVISIBLE
    }

    private fun liberarPermissao(permissao: String) {
        val verificaPermissao = ActivityCompat.checkSelfPermission(
            requireContext(),
            permissao
        )

        when (Build.VERSION.SDK_INT) {
            Build.VERSION_CODES.S -> { //SDK = 31
                if (permissao in arrayOf(Manifest.permission.BLUETOOTH_ADMIN)) {
                    liberarProximaPermissao()
                    return
                }
            }
            Build.VERSION_CODES.R, Build.VERSION_CODES.Q -> { //SDK = 30 and SDK = 29
                if (permissao in arrayOf(
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                    )
                ) {
                    liberarProximaPermissao()
                    return
                }
            }
            Build.VERSION_CODES.P -> { //SDK = 28
                if (permissao in arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION)) {
                    liberarProximaPermissao()
                    return
                }
            }
            else -> {
                if (permissao in arrayOf(
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                    )
                ) {
                    liberarProximaPermissao()
                    return
                }
            }
        }
        if (verificaPermissao != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(permissao), ACCESS_PERMISSIONS
            )
        } else liberarProximaPermissao()
    }

    private fun getPermissao():String {
        return if (indexPermissao < slideList[getIndex()].permissoes.size)
            slideList[getIndex()].permissoes[indexPermissao]
        else ""
    }

    private fun liberarProximaPermissao() {
        indexPermissao++
        val permissao = getPermissao()
        if (permissao.isNotEmpty()) liberarPermissao(permissao)
        else nextPage()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACCESS_PERMISSIONS -> {
                permissions.forEachIndexed { index, permission ->
                    if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                        Log.i(this.javaClass.simpleName, "Permissão ${permissions[index]}: Aceita")
                        configApp.setPermissionDeniedCount(permission,0)
                        liberarProximaPermissao()
                    } else {
                        Log.i(this.javaClass.simpleName, "Permissão ${permissions[index]}: Negada")
                        configApp.setPermissionDeniedCount(permission,configApp.getPermissionDeniedCount(permission) + 1)

                        /** Permissão negada muitas vezes **/
                        if (configApp.getPermissionDeniedCount(permissions[index]) >= 2) {
                            Log.e(this.javaClass.simpleName, "Permissão (${permissions[index]}) negada muitas vezes")
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                            intent.setData(uri)
                            startActivity(intent)
                        }
                    }
                }
            }
            else -> {
                Log.i(this.javaClass.simpleName, "Outras")
            }
        }
    }
}