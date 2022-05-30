package com.zero.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.zero.mvvm.data.model.DogRandom
import com.zero.mvvm.data.state.SimpleState
import com.zero.mvvm.databinding.ActivityMainBinding
import dmax.dialog.SpotsDialog

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    private val spotsDialog: SpotsDialog by lazy {
        SpotsDialog(this, "Mohon tunggu...")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = MainViewModel()
        setContentView(binding.root)
        initListener()
        with(binding) {
            btnRandom.setOnClickListener { viewModel.getRandomDog() }
        }
    }

    private fun initListener() {
        viewModel.state.observe(this) {
            when (it) {
                is SimpleState.Error -> {
                    spotsDialog.dismiss()
                }
                SimpleState.Loading -> {
                    spotsDialog.show()
                }
                is SimpleState.Result<*> -> {
                    if (it.data is DogRandom) {
                        binding.ivDog.load(it.data.message)
                    }
                    spotsDialog.dismiss()
                }
            }
        }
    }
}