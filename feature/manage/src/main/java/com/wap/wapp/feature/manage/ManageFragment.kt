package com.wap.wapp.feature.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.showToast
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.manage.code.ManageCodeDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManageFragment : Fragment() {

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
            var isShowDialog by rememberSaveable { mutableStateOf(false) }

            WappTheme {
                ManageScreen(
                    showManageCodeDialog = { isShowDialog = true },
                )

                if (isShowDialog) {
                    ManageCodeDialog(
                        onDismissRequest = { isShowDialog = false },
                        showToast = { throwable ->
                            requireContext().showToast(
                                throwable.toSupportingText(),
                            )
                        },
                    )
                }
            }
        }
    }
}
