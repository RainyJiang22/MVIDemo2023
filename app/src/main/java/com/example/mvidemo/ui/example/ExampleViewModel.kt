package com.example.mvidemo.ui.example

import com.example.mvidemo.base.BaseComposeViewModel
import com.example.mvidemo.base.BaseViewModel

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */
class ExampleViewModel : BaseComposeViewModel<ExampleState>() {

    override fun initialState(): ExampleState = ExampleState.Loading
}