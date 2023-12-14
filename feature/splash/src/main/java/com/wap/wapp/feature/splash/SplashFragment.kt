package com.wap.wapp.feature.splash

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.wap.wapp.core.commmon.base.BaseFragment
import com.wap.wapp.core.commmon.extensions.repeatOnStarted
import com.wap.wapp.feature.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate) {
    override val fragmentViewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = fragmentViewModel.apply {
                repeatOnStarted { eventFlow.collect { handleEvent(it) } }
            }
        }
    }

    private fun handleEvent(event: SplashViewModel.SplashEvent) = when (event) {
        is SplashViewModel.SplashEvent.TimerDone -> navigateToAuth()
    }

    private fun navigateToAuth() {
        findNavController().navigate(
            "wapp://feature/nav_auth".toUri(),
            NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build(),
        )
    }
}