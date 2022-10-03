package br.com.tecnomotor.thanos.ui.testesUnitarios.downloadFiles

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.tecnomotor.thanos.databinding.CallerdownloadbindingBinding
/**
@Author Matheus_Ruggiero
 */

class DownloadCaller : Fragment() {


    val urlFile = "https://atualize.tecnomotor.com.br/?TDiesel/firmware.bin"
    val BASE_URL = "https://atualize.tecnomotor.com.br/"
    private var _binding: CallerdownloadbindingBinding? = null

    //lateinit var downloadViewModel: DownloadViewModel


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        downloadViewModel = ViewModelProvider(this).get(DownloadViewModel::class.java).apply {
//            viewModelScope.launch {
//
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CallerdownloadbindingBinding.inflate(inflater, container, false)


        return binding.root

        return super.onCreateView(inflater, container, savedInstanceState)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            // findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            //downloadFile(urlFile)

            DownloadUtil.download(urlFile,BASE_URL,object : DownloadListener {
                override fun onStart() {
                    println("Download: iniciei!")

                }

                @SuppressLint("SetTextI18n")
                override fun onProgress(progress: Int) {
                    val prog = progress.toFloat()/100
                    println("Download: progress: $prog")
                    activity?.runOnUiThread{
                        binding.progressBar.setProgress(prog.toInt())
                        binding.txtProgressPercent.setText("Progress $prog%")
                    }


                }

                override fun onFinish(path: String?) {
                    println("Download: finish: $path")
                }

                override fun onFail(errorInfo: String?) {
                    println("Download: on Fail: $errorInfo")
                }
            })


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}