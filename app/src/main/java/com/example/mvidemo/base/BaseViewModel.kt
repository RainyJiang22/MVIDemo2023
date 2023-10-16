package com.example.mvidemo.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */
abstract class BaseViewModel<A : Action, S : State, E : Effect> : ViewModel() {

    private val _action = Channel<A>()

    init {
        viewModelScope.launch {
            _action.consumeAsFlow().collect {
                /*replayState：很多时候我们需要通过上个state的数据来处理这次数据，所以我们要获取当前状态传递*/
                onAction(it, initialState())
            }
        }
    }

    val action: SendChannel<A> by lazy { _action }

    fun sendAction(action: A) = viewModelScope.launch {
        _action.send(action)
    }

    /** 订阅事件的传入 onAction()分发处理事件 */
    protected abstract fun onAction(action: A, currentState: S?)

    //state默认值
    abstract fun initialState(): S

    private val _state by lazy {
        MutableSharedFlow<S>(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    val state: Flow<S> by lazy { _state.distinctUntilChanged() }

    private val _effect = MutableSharedFlow<E>()

    val effect: SharedFlow<E> by lazy { _effect.asSharedFlow() }

    protected fun emitState(builder: suspend () -> S?) = viewModelScope.launch {
        builder()?.let { _state.emit(it) }
    }

    protected suspend fun emitState(state: S) = _state.emit(state)


    protected fun emitEffect(builder: suspend () -> E?) = viewModelScope.launch {
        builder()?.let {
            _effect.emit(it)
        }
    }

    protected suspend fun emitEffect(effect: E) = _effect.emit(effect)


}