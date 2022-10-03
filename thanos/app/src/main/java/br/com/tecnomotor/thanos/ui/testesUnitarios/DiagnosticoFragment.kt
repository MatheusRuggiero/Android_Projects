package br.com.tecnomotor.thanos.ui.testesUnitarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.tecnomotor.thanos.databinding.FragmentDiagnosticoTestesUnitariosBinding
import br.com.tecnomotor.thanos.ui.testesUnitarios.viewmodel.UnitTestViewModel

class DiagnosticoFragment: Fragment() {

    private lateinit var unitTestViewModel: UnitTestViewModel

    private var _binding: FragmentDiagnosticoTestesUnitariosBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        unitTestViewModel = ViewModelProvider(this).get(UnitTestViewModel::class.java).apply {
            setIndex(arguments?.getInt(DiagnosticoFragment.ARG_SECTION_NUMBER) ?: 2)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDiagnosticoTestesUnitariosBinding.inflate(inflater, container, false)


        binding.botaoViewModel = unitTestViewModel

        val root = binding.root

        val textView: TextView = binding.sectionLabel
        unitTestViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
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
        fun newInstance(sectionNumber: Int):  DiagnosticoFragment {
            return  DiagnosticoFragment().apply {
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