package com.example.hw.data.local.sharedpreferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.hw.data.local.model.Data
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Pref(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(MY_PREF, MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val gson = Gson()

    fun getObjects(): ArrayList<Data> = gson.fromJson(
        sharedPreferences.getString(COURSES, "[]"),
        object : TypeToken<ArrayList<Data>>() {}.type
    ) ?: ArrayList()


    fun saveData(list: ArrayList<Data>) {
        val json = gson.toJson(list)
        editor.putString(COURSES, json).apply()

    }

    companion object{
        private const val MY_PREF = "MyPrefs"
        private const val COURSES = "courses"
    }
}