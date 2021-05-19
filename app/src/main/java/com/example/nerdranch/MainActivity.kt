package com.example.nerdranch

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

private const val KEY_INDEX = "index"

// Jetpack: Foundation, Architecture, UI, Behavior

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }
    private lateinit var rootView: LinearLayout
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var newButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()

        savedInstanceState?.apply {
            Log.d(TAG, "onCreate ${quizViewModel.currentIndex}")
            quizViewModel.currentIndex = savedInstanceState.getInt(KEY_INDEX)
        } ?: run {
            Log.d(TAG, "onCreate SIS null")
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        Log.d(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")
    }

    private fun setup() {
        rootView = findViewById(R.id.rootView)
        questionTextView = findViewById(R.id.questionTextView)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)
        newButton = findViewById(R.id.newButton)

        trueButton.setOnClickListener {
            checkAnswer(true)
            nextButton.callOnClick()

        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            nextButton.callOnClick()
        }

        previousButton.setOnClickListener {
            quizViewModel.currentIndex = if (quizViewModel.currentIndex == 0) {
                quizViewModel.questions.size - 1
            } else {
                quizViewModel.currentIndex - 1
            }
            updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            if (quizViewModel.currentIndex == 0) {
                showScore()
            }
            updateQuestion()
        }

        newButton.setOnClickListener {
            startActivity(Intent(this, QuizViewModelActivity::class.java))
        }

        updateQuestion()
    }

    private fun showScore() {
        Snackbar.make(rootView, "${quizViewModel.answerCount}개 정답", Snackbar.LENGTH_SHORT).show()
        quizViewModel.answerCount = 0
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean): Boolean {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.answerCount += 1
            R.string.answer_pass
        } else {
            R.string.answer_not_pass
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        return messageResId == R.string.answer_pass
    }

    private fun print(msg: String) = Log.d(TAG, msg)

    companion object {
        const val TAG = "QuizViewModel"
    }
}