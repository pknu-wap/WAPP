package com.wap.wapp.feature.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wap.designsystem.WappTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoticeFragment : Fragment() {

    private lateinit var composeView: ComposeView
    private val viewModel: NoticeViewModel by viewModels()

    @Inject
    lateinit var calendar: Calendar

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
            val events by viewModel.events.collectAsState()
            Log.d("test", "NoticeFragment : $events")

            val dateAndDayOfWeek = calendar.generateNowDateAndDay()
            WappTheme {
                NoticeScreen(events, dateAndDayOfWeek)
            }
        }
    }
}
