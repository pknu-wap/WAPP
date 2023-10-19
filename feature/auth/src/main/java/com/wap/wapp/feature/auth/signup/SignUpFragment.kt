package com.wap.wapp.feature.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.auth.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            WappTheme {
                SignUpScreen(
                    viewModel = hiltViewModel(),
                    navigateToNotice = { navigateToNotice() },
                    navigateToSignIn = { navigateToSignIn() },
                )
            }
        }
    }

    private fun navigateToNotice() {
        findNavController().navigate(
            "wapp://feature/nav_notice".toUri(),
            NavOptions.Builder()
                .setPopUpTo(R.id.signUpFragment, true)
                .build(),
        )
    }

    private fun navigateToSignIn() {
        findNavController().navigate(
            SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(),
        )
    }
}
