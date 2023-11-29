package com.wap.wapp.feature.management.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wap.designsystem.WappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyRegistrationFragment : Fragment() {

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
                SurveyRegistrationScreen(
                    onBackButtonClicked = { navigateToManagement() },
                    registerSurveyForm = {
                        navigateToManagement()
                    }
                )
            }
        }
    }

    private fun navigateToManagement() {
        findNavController().navigate(
            SurveyRegistrationFragmentDirections.actionSurveyRegistrationFragmentToManageFragment(),
        )
    }
}
