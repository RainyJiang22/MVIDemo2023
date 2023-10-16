package com.example.mvidemo.ui.example

import androidx.lifecycle.viewModelScope
import com.example.mvidemo.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */
class ExampleViewModel : BaseViewModel<ExampleAction, ExampleState, ExampleEffects>() {


    private val repo = ExampleRepository
    override fun onAction(action: ExampleAction, currentState: ExampleState?) {
        when (action) {
            ExampleAction.OnBtnClick -> {
                login()
            }
        }
    }

    override fun initialState(): ExampleState = ExampleState.Loading


    private fun login() {
        repo.fetchLogin()
            .onStart {
                emitState(ExampleState.Loading)
            }.catch {
                emitState(ExampleState.Error(it))
            }.onEach {
                emitEffect(ExampleEffects.NavigationToHost(it))
            }.launchIn(viewModelScope)
    }
}