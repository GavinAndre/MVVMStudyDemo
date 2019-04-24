package com.gavinandre.mvvmdemo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gavinandre.mvvmdemo.R
import com.gavinandre.mvvmdemo.databinding.ActivityAnimalBinding
import com.gavinandre.mvvmdemo.model.data.Animal
import com.gavinandre.mvvmdemo.viewmodel.AnimalViewModel

class AnimalActivity : AppCompatActivity() {
    
    lateinit var mBinding: ActivityAnimalBinding
    lateinit var mViewMode: AnimalViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_animal)
        val animal = Animal("dog", 0)
        mViewMode = AnimalViewModel(animal)
        mBinding.animalViewModel = mViewMode
    }
}
