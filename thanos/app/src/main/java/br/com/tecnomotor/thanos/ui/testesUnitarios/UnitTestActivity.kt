package br.com.tecnomotor.thanos.ui.testesUnitarios

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import br.com.tecnomotor.thanos.adapter.testeUnitario.UnitTestAdapter
import br.com.tecnomotor.thanos.databinding.ActivityMainUnitTestBinding
import br.com.tecnomotor.thanos.ui.testesUnitarios.viewmodel.UnitTestViewModel
import com.google.android.material.tabs.TabLayout
/**
@Author Matheus_Ruggiero
 */


class UnitTestActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainUnitTestBinding

    private val viewModel: UnitTestViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityMainUnitTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val sectionsPagerAdapter = UnitTestAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

    }






}







