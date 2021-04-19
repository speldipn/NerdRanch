package com.example.nerdranch

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var rootView: LinearLayout
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
    private var answerCount = 0
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
        print("onCreate called")
    }

    private fun setup() {
        rootView = findViewById(R.id.rootView)
        questionTextView = findViewById(R.id.questionTextView)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)

        trueButton.setOnClickListener {
            checkAnswer(true)
            nextButton.callOnClick()

        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            nextButton.callOnClick()
        }

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
            if(currentIndex == 0) { showScore() }
            updateQuestion()
        }

        updateQuestion()
    }

    private fun showScore() {
        Snackbar.make(rootView, "${answerCount}개 정답", Snackbar.LENGTH_SHORT).show()
        answerCount = 0
    }

    private fun updateQuestion() {
        val questionTextResId = questions[currentIndex].textRedId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean): Boolean {
        val correctAnswer = questions[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.answer_pass
            answerCount += 1
        } else {
            R.string.answer_not_pass
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        return messageResId == R.string.answer_pass
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