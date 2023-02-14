package sports.quiz.betcity.hast.fragments.menu

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
import sports.quiz.betcity.hast.databinding.FragmentMenuBinding
import kotlin.properties.Delegates
import kotlin.system.exitProcess

class MenuFragment : Fragment() {

    private var binding: FragmentMenuBinding by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI(activity?.window)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enableButtons()
    }

    private fun enableButtons() {
        binding.playButton.setOnClickListener{
            val actionToGameFragment = MenuFragmentDirections.actionMenuFragmentToGameFragment()

            navigateTo(actionToGameFragment)
        }

        binding.exitButton.setOnClickListener{
            exitProcess(0)
        }
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