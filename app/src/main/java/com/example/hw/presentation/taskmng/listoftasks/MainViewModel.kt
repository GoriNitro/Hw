package com.example.hw.presentation.taskmng.listoftasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw.data.local.model.Data
import com.example.hw.data.local.sharedpreferences.Pref

class MainViewModel : ViewModel() {
    private var view : TaskView? = null

    private val _dataList = MutableLiveData<ArrayList<Data>>()
    val dataList: LiveData<ArrayList<Data>> get() = _dataList

    fun getList(pref : Pref) {
        val data = pref.getObjects()
        data.sortBy { it.name }
        _dataList.value = data
        if (_dataList.value!!.isEmpty()){
            view?.toast("list is empty")
        }
    }
    fun saveList(list: ArrayList<Data>, pref: Pref){
        pref.saveData(list)
    }
}
