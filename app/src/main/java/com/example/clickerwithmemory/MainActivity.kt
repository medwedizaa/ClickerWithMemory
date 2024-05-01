package com.example.clickerwithmemory

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clickerwithmemory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    private lateinit var binding: ActivityMainBinding
    private var clickCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getPreferences(Context.MODE_PRIVATE)

        // Получение сохраненного (ранее) значения
        val savedClickCounterValue = sharedPref.getInt(KEY_COUNTER, 0)
        clickCount = savedClickCounterValue
        binding.textView.text = getString(R.string.description, clickCount)

        binding.buttonView.setOnClickListener {
            clickCount++
            binding.textView.text = getString(R.string.description, clickCount)
            saveClickCount(clickCount)
        }

        binding.buttonView.setOnLongClickListener {
            saveClickCount(0)
            clickCount = 0
            binding.textView.text = getString(R.string.description, clickCount)
            return@setOnLongClickListener true
        }
    }

    private fun saveClickCount(value: Int) {
        // Редактирование или сохранение в файл наших значений
        with (sharedPref.edit()) {
            putInt(KEY_COUNTER, value)
            commit()
        }

        // 1 вариант записи значения в sharedPref
//        sharedPref.edit().putInt(KEY_COUNTER, value).apply()

        // 2й вариант
//        val editor = sharedPref.edit()
//        editor.putInt(KEY_COUNTER, value)
//        editor.apply()

        // 3й вариант
//        val myEditor = sharedPref.edit()
//        with (myEditor) {
//            putInt(KEY_COUNTER, value)
//            apply()
//        }
    }

    companion object {
        const val KEY_COUNTER = "KEY_COUNTER"
    }
}