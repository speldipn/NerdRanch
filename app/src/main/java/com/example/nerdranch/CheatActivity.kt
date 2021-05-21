package com.example.nerdranch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.nerdranch.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    lateinit var binding: ActivityCheatBinding

    /*private val isAnswer by lazy {
        intent?.getBooleanExtra(MainActivity.ANSWER, false) ?: false
    }*/
    private var isAnswer: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cheat)
        isAnswer = intent?.getBooleanExtra(MainActivity.ANSWER, false) ?: false
        setup()
    }

    private fun setup() {
        binding.showAnswerButton.setOnClickListener {
            if (isAnswer) {
                Toast.makeText(this, getString(R.string.true_button), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.false_button), Toast.LENGTH_LONG).show()
            }

            val data = Intent().apply { putExtra(ANSWER_RESULT, isAnswer) }
            setResult(RESULT_OK, data)
            finish()
        }
    }

    companion object {
        const val ANSWER_RESULT = "ANSWER_RESULT"
    }
}