package com.example.mvidemo.ui.example

import com.example.mvidemo.base.Effect

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */
sealed class ExampleEffects : Effect {
    data class NavigationToHost(val response: Int) : ExampleEffects()
}