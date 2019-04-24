package com.gavinandre.mvvmdemo.viewmodel

import androidx.databinding.ObservableField
import com.gavinandre.mvvmdemo.model.data.Animal

class AnimalViewModel(val animal: Animal) {
    
    val info = ObservableField<String>("${animal.name} 叫了 ${animal.shoutCount}声..")
    
    fun shout() {
        animal.shoutCount++
        info.set("${animal.name} 叫了 ${animal.shoutCount}声..")
    }
    
}