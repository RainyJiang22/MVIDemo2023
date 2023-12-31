package com.example.mvidemo.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */

@Composable
fun <S : State, E : Effect, V : BaseViewModel<*, S, E>> StateEffectScaffold(
    viewModel: V,
    initialState: S? = null,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    sideEffect: (suspend (V, E) -> Unit)? = null,
    content: @Composable (V, S) -> Unit,
) {
    sideEffect?.let {
        val lambdaEffect by rememberUpdatedState(newValue = sideEffect)
        LaunchedEffect(viewModel.effect, lifecycle, minActiveState) {
            lifecycle.repeatOnLifecycle(minActiveState) {
                if (context == EmptyCoroutineContext) {
                    viewModel.effect.collect { lambdaEffect(viewModel, it) }
                } else withContext(context) {
                    viewModel.effect.collect { lambdaEffect(viewModel, it) }
                }
            }
        }
    }

    val uiState = viewModel.state.collectAsStateWithLifecycle(
        initialValue = viewModel.replayState,
        lifecycle = lifecycle,
        minActiveState = minActiveState,
        context = context
    )
    (uiState.value ?: initialState)?.let { content(viewModel, it) }
}