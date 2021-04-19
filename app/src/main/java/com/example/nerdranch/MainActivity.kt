package com.example.nerdranch

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questions = listOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true),
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
        print("onCreate called")
    }

    private fun setup() {
        questionTextView = findViewById(R.id.questionTextView)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)

        trueButton.setOnClickListener { checkAnswer(true) }

        falseButton.setOnClickListener { checkAnswer(false) }

        previousButton.setOnClickListener {
            currentIndex = if (currentIndex == 0) {
                questions.size - 1
            } else {
                currentIndex - 1
            }
            updateQuestion()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questions.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questions[currentIndex].textRedId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questions[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.answer_pass
        } else {
            R.string.answer_not_pass
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun print(msg: String) = Log.d(TAG, msg)

    override fun onStart() {
        super.onStart()
        print("onStart called")
    }

    override fun onResume() {
        super.onResume()
        print("onResume called")
    }

    override fun onPause() {
        super.onPause()
        print("onPause called")
    }

    override fun onStop() {
        super.onStop()
        print("onStop called")
    }

    override fun onRestart() {
        super.onRestart()
        print("onRestart called")
    }

    override fun onDestroy() {
        super.onDestroy()
        print("onDestroy called")
    }

    companion object {
        const val TAG = "speldipn"
    }
}