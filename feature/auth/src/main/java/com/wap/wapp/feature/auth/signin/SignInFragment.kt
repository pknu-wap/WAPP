package com.wap.wapp.feature.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.domain.usecase.auth.SignInUseCase
import com.wap.wapp.feature.auth.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    @Inject
    lateinit var signInUseCase: SignInUseCase

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
                SignInBottomSheet(
                    signInUseCase = signInUseCase,
                    navigateToSignUp = { navigateToSignUp() },
                    navigateToNotice = { navigateToNotice() },
                )
            }
        }
    }

    private fun navigateToSignUp() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToSignUpFragment(),
        )
    }

    private fun navigateToNotice() {
        findNavController().navigate(
            "wapp://feature/nav_notice".toUri(),
            NavOptions.Builder()
                .setPopUpTo(R.id.signUpFragment, true)
                .build(),
        )
    }
}
