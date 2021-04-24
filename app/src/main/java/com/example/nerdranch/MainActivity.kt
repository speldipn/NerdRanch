package com.example.nerdranch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var rootView: LinearLayout
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var newButton: Button
    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setupViewModel() {
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        print("Got a QuizViewModel: $quizViewModel")
    }

    private fun setup() {
        setupViewModel()

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
            quizViewModel.currentIndex = (quizViewModel.currentIndex + 1) % quizViewModel.questions.size
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
        val questionTextResId = quizViewModel.questions[quizViewModel.currentIndex].textRedId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean): Boolean {
        val correctAnswer = quizViewModel.questions[quizViewModel.currentIndex].answer

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

    override fun onRestart() {
        super.onRestart()
        print("onRestart")
    }

    override fun onStart() {
        super.onStart()
        print("onStart")
    }

    override fun onResume() {
        super.onResume()
        print("onResume")
    }

    override fun onPause() {
        super.onPause()
        print("onPause")
    }

    override fun onStop() {
        super.onStop()
        print("onStop")
    }

    override fun onDestroy() {
        print("onDestroy inFinishing: ${isFinishing}")
        super.onDestroy()
        print("onDestroy inFinishing: ${isFinishing}")
    }

    companion object {
        const val TAG = "QuizViewModel"
    }
}