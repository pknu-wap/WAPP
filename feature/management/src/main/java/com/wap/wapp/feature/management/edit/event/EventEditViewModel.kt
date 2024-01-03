package com.wap.wapp.feature.management.edit.event

import androidx.lifecycle.ViewModel
import com.wap.wapp.core.domain.usecase.event.GetEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventEditViewModel @Inject constructor(
    private val getEventUseCase: GetEventUseCase,
) : ViewModel()
