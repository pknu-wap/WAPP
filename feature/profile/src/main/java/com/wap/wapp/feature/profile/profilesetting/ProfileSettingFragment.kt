package com.wap.wapp.feature.profile.profilesetting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wap.designsystem.WappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSettingFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val viewModel: ProfileSettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            WappTheme {
                ProfileSettingScreen()
            }
        }
    }
}
