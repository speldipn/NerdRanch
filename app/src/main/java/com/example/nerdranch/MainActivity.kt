package com.example.nerdranch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
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
    }

    private fun setup() {
        questionTextView = findViewById(R.id.questionTextView)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        nextButton = findViewById(R.id.nextButton)

        trueButton.setOnClickListener {
            if(questions[currentIndex].answer) {
                showToastAnswer()
            } else {
                showToastNotAnswer()
            }
        }

        falseButton.setOnClickListener {
            if(!questions[currentIndex].answer) {
                showToastAnswer()
            } else {
                showToastNotAnswer()
            }
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questions.size
            setQuestion(currentIndex)
        }
        setQuestion(currentIndex)
    }

    private fun showToastAnswer() = Toast.makeText(this, R.string.answer_pass, Toast.LENGTH_SHORT).show()

    private fun showToastNotAnswer() = Toast.makeText(this, R.string.answer_not_pass, Toast.LENGTH_SHORT).show()

    private fun setQuestion(index: Int) {
        val questionTextResId = questions[index].textRedId
        questionTextView.setText(questionTextResId)
    }

    private fun print(msg: String) = Log.d(TAG, msg)

    companion object {
        const val TAG = "speldipn"
    }
}