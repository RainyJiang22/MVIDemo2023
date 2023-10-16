package com.example.mvidemo.ui.example

import com.example.mvidemo.base.Action

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */
sealed class ExampleAction : Action {

    object OnBtnClick : ExampleAction()
}