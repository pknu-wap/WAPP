package com.wap.wapp.feature.management.registration.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.wap.designsystem.WappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventRegistrationFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val viewModel: EventRegistrationViewModel by viewModels()

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
            val currentRegistrationState
                by viewModel.currentRegistrationState.collectAsStateWithLifecycle()
            val eventTitle by viewModel.eventTitle.collectAsStateWithLifecycle()
            val eventContent by viewModel.eventContent.collectAsStateWithLifecycle()
            val onTitleChanged = viewModel::setEventTitle
            val onContentChanged = viewModel::setEventContent
            val onNextButtonClicked =
                viewModel::setEventRegistrationState

            WappTheme {
                EventRegistrationScreen(
                    currentRegistrationState = currentRegistrationState,
                    eventTitle = eventTitle,
                    eventContent = eventContent,
                    onTitleChanged = onTitleChanged,
                    onContentChanged = onContentChanged,
                    onNextButtonClicked = onNextButtonClicked,
                    onBackButtonClicked = { navigateToManagement() },
                )
            }
        }
    }

    private fun navigateToManagement() = findNavController().navigate(
        EventRegistrationFragmentDirections.actionEventRegistrationFragmentToManageFragment(),
    )
}
