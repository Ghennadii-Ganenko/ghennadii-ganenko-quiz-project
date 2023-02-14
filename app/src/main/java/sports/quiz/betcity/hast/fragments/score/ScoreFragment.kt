package sports.quiz.betcity.hast.fragments.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import sports.quiz.betcity.hast.R
import sports.quiz.betcity.hast.databinding.FragmentScoreBinding
import sports.quiz.betcity.hast.fragments.game.NUMBER_OF_QUESTIONS
import kotlin.properties.Delegates

class ScoreFragment : Fragment() {

    private var binding: FragmentScoreBinding by Delegates.notNull()
    private val args: ScoreFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideSystemUI(activity?.window)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            if (args.score > 6) {
                Picasso
                    .get()
                    .load(R.drawable.win)
                    .into(wonOrLostImage)
            } else {
                Picasso
                    .get()
                    .load(R.drawable.lose)
                    .into(wonOrLostImage)
            }

            numberOfCorrectAnswersText.text = getString(R.string.answers_count, args.score, NUMBER_OF_QUESTIONS)

            playAgainButton.setOnClickListener{
                toGame()
            }

            mainMenuButton.setOnClickListener {
                toMenu()
            }

        }
    }

    private fun toGame() {
        val actionToGameFragment = ScoreFragmentDirections.actionScoreFragmentToGameFragment()

        navigateTo(actionToGameFragment)
    }

    private fun toMenu() {
        val actionToMenuFragment = ScoreFragmentDirections.actionScoreFragmentToMenuFragment()

        navigateTo(actionToMenuFragment)
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