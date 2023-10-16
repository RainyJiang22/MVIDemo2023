package com.example.mvidemo.ui.example

import com.example.mvidemo.base.State

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */
sealed class ExampleState : State {
    object onLoginHub: ExampleState()
    object Loading: ExampleState()
    data class Error(val ex: Throwable) : ExampleState()
}