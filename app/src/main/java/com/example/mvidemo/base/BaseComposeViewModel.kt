package com.example.mvidemo.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */
abstract class BaseComposeViewModel<S : State> : ViewModel() {

    abstract fun initialState(): S

    private val _viewState: MutableState<S> by lazy { mutableStateOf(initialState()) }

    val viewState: androidx.compose.runtime.State<S> = _viewState
}