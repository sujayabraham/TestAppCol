package com.colmind.testapp

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.colmind.testapp.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val myFilter = InputFilter { source, start, end, dest, dstart, dend ->
            try {
                val c = source[0]
                if (Character.isLetter(c) || Character.isDigit(c)) {
                    return@InputFilter "" + c
                } else if (source?.contains(" ") == true) {
                    return@InputFilter ""
                } else {
                    //error("error!")
                    return@InputFilter ""
                }
            } catch (e: Exception) {
                Log.e("", "error!")
            }
            null
        }

        binding.editText.filters = arrayOf(myFilter)

        //binding.editText.filters(myFilter)
        binding.lengthButton.setOnClickListener {
            binding.textView.text = "Text Length :${binding.editText.text?.length}"
        }

        binding.reverseButton.setOnClickListener {
            binding.textView.text = "Reversed Text :${binding.editText.text?.reversed()}"
        }

        binding.appendButton.setOnClickListener {
            binding.textView.text = "Hello ${binding.editText.text}"
        }

        binding.numericButton.setOnClickListener {
            if(!binding.editText.text.isNullOrEmpty()) {
                if (checkForDigits()) {
                    binding.textView.text = "Yes"
                } else {
                    binding.textView.text = "No"
                }
            } else {
                binding.textView.text = "No"
            }
        }

        binding.clearButton.setOnClickListener {
            binding.textView.text = ""
            binding.editText.text?.clear()
        }

        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun checkForDigits() : Boolean {
        return binding.editText.text?.isDigitsOnly() == true
    }
}