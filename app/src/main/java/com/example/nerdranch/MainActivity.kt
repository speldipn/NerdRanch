package com.example.nerdranch

import android.content.Intent
import android.os.Bundle
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
    private lateinit var cheatButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()

        savedInstanceState?.apply {
            quizViewModel.currentIndex = savedInstanceState.getInt(KEY_INDEX)
        } ?: run {
            print("onCreate: saved Instance ")
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            data?.run {
                quizViewModel.isCheater = true
                val isAnswer = getBooleanExtra(CheatActivity.ANSWER_RESULT, false)
                if (isAnswer) {
                    Log.d(TAG, getString(R.string.true_button))
                } else {
                    Log.d(TAG, getString(R.string.false_button))
                }
            }
        }
    }

    private fun setup() {
        rootView = findViewById(R.id.rootView)
        questionTextView = findViewById(R.id.questionTextView)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        previousButton = findViewById(R.id.previousButton)
        nextButton = findViewById(R.id.nextButton)
        cheatButton = findViewById(R.id.cheatButton)

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

        cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)
            intent.putExtra(ANSWER, quizViewModel.currentQuestionAnswer)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
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

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgement_toast
            userAnswer == correctAnswer -> R.string.answer_pass
            else -> R.string.answer_not_pass

        }

        if (messageResId == R.string.answer_pass) {
            quizViewModel.answerCount += 1
        } else if (messageResId == R.string.judgement_toast) {
            quizViewModel.isCheater = false
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        return messageResId == R.string.answer_pass
    }

    private fun print(msg: String) = Log.d(TAG, msg)

    companion object {
        const val TAG = "MainActivity"
        const val ANSWER = "ANSWER"
        const val REQUEST_CODE_CHEAT = 0
    }
}