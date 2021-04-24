package com.example.nerdranch

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    val questions = listOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true),
    )
    var currentIndex = 0
    var answerCount = 0

    val currentQuestionAnswer: Boolean
        get() = questions[currentIndex].answer

    val currentQuestionText: Int
        get() = questions[currentIndex].textRedId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questions.size
    }
}