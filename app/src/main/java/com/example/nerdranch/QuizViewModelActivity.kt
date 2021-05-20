package com.example.nerdranch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class QuizViewModelActivity: AppCompatActivity() {
    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_view_model_activty)
        setupViewModel()
    }

    private fun setupViewModel() {
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
    }

}