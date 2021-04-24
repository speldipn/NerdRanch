package com.example.nerdranch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

class QuizViewModelActivity: AppCompatActivity() {
    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_view_model_activty)
        Log.d("QuizViewModelActivity","QuizViewModelActivity onCreated")
        setupViewModel()
    }

    private fun setupViewModel() {
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        Log.d("QuizViewModelActivity","QuizViewModelActivity Got a QuizViewModel $quizViewModel")
    }
}