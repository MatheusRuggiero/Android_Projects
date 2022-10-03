package br.com.tecnomotor.thanos.ui.testesUnitarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.tecnomotor.thanos.adapter.ConnectDeviceAdapter
import br.com.tecnomotor.thanos.databinding.FragmentFuncoesBasicasBinding
import br.com.tecnomotor.thanos.ui.testesUnitarios.viewmodel.UnitTestViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


/**
 * Author Matheus_Ruggiero
 */
@OptIn(InternalCoroutinesApi::class)
class FuncoesBasicasFragment : Fragment() {

    private lateinit var unitTestViewModel: UnitTestViewModel

    private var _binding: FragmentFuncoesBasicasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        unitTestViewModel = ViewModelProvider(this).get(UnitTestViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*
            12/02/2022
            Rogério: Deu problema aqui depois do merge
         */
//        _binding = FragmentFuncoesBasicasBinding.inflate(inflater, container, false)
//        binding.botaoViewModel = unitTestViewModel
//        val root = binding.root
//        val textView: TextView = binding.sectionLabel
//        unitTestViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root


        return super.onCreateView(inflater, container, savedInstanceState)
        
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int):  FuncoesBasicasFragment {
            return  FuncoesBasicasFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

}