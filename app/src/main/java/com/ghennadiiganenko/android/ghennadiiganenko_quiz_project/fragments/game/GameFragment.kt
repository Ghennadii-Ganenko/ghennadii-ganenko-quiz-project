package com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.fragments.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.data.SportQuizQuestions
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.databinding.FragmentGameBinding
import kotlin.properties.Delegates

const val NUMBER_OF_QUESTIONS = 10
private const val NUMBER_OF_CHOICES = 4

class GameFragment : Fragment() {

    private var binding: FragmentGameBinding by Delegates.notNull()
    private val sportQuizQuestions: SportQuizQuestions = SportQuizQuestions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI(activity?.window)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var questionNumber = 0
        var numberOfCorrectAnswers = 0

        val randomQuestionsNumbers = (0 until NUMBER_OF_QUESTIONS).toList().shuffled()

        // Inflating first question
        inflateNewQuestion(randomQuestionsNumbers[questionNumber])

        // Setting onClick listener on RadioButtons:
        // while questionNumber != NUMBER_OF_QUESTIONS
        // check if clicked answer is correct and load another question
        for (buttonIndex in 0 until NUMBER_OF_CHOICES) {
            binding.answerButtons[buttonIndex].setOnClickListener {
                // Check for clicked choice
                if (isAnswerCorrect(
                    (binding.answerButtons[buttonIndex] as RadioButton).text.toString(),
                    sportQuizQuestions.answers[randomQuestionsNumbers[questionNumber]]
                )) {
                    numberOfCorrectAnswers++
                }

                // Check if game should be ended
                if (questionNumber == NUMBER_OF_QUESTIONS - 1) {
                    gameOver(numberOfCorrectAnswers)
                } else {
                    // Inflating new question
                    questionNumber++
                    inflateNewQuestion(randomQuestionsNumbers[questionNumber])
                }
            }
        }
    }

    private fun isAnswerCorrect(choice: String, answer: String) : Boolean = choice==answer

    private fun inflateNewQuestion(questionNumber: Int) {
        val randomChoicesNumbers = (0 until NUMBER_OF_CHOICES).toList().shuffled()

        binding.questionsText.text = sportQuizQuestions.questions[questionNumber]

        for (buttonIndex in 0 until NUMBER_OF_CHOICES) {
            (binding.answerButtons[buttonIndex] as RadioButton).text = inflateChoices(randomChoicesNumbers[buttonIndex], questionNumber)
        }
    }

    private fun inflateChoices(index: Int, questionNumber: Int)  = sportQuizQuestions.choices[questionNumber][index]

    private fun gameOver(numberOfCorrectAnswers: Int) {
        val actionToGameFragment = GameFragmentDirections.actionGameFragmentToScoreFragment(numberOfCorrectAnswers)

        navigateTo(actionToGameFragment)
    }

    private fun navigateTo(action: NavDirections) =
        findNavController().navigate(action)

    private fun hideSystemUI(window: Window?) =
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
}