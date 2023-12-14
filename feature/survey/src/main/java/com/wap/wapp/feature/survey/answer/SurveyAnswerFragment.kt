/*
package com.wap.wapp.feature.survey.answer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wap.designsystem.WappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyAnswerFragment : Fragment() {
    private lateinit var composeView: ComposeView

   */
/* private val args: SurveyAnswerFragmentArgs by navArgs()*//*


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
                SurveyAnswerScreen(
                    onBackButtonClicked = { navigateToSurvey() },
                    onSubmitButtonClicked = { navigateToSurvey() },
                    eventId = args.eventId,
                )
            }
        }
    }

   */
/* private fun navigateToSurvey() = findNavController().navigate(
        SurveyAnswerFragmentDirections.actionSurveyAnswerFragmentToSurveyFragment(),
    )*//*

}
*/
