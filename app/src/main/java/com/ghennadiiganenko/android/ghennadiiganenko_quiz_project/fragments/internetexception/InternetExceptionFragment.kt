package com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.fragments.internetexception

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ghennadiiganenko.android.ghennadiiganenko_quiz_project.databinding.FragmentInternetExceptionBinding
import kotlin.properties.Delegates

class InternetExceptionFragment : Fragment() {
    private var binding: FragmentInternetExceptionBinding by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInternetExceptionBinding.inflate(inflater, container, false)

        return binding.root
    }
}