package com.example.mvidemo.ui.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */

@Composable
fun ExampleScreen() {
    val scaffoldState = rememberScaffoldState()
    StateEffectScaffold(viewModel = viewModel<ExampleViewModel>(),
        initialState = ExampleState.Loading,
        sideEffect = { viewmodel, sideEffect ->
            when (sideEffect) {
                is ExampleEffects.NavigationToHost -> {
                    scaffoldState.snackbarHostState.showSnackbar("navigate")
                }
            }
        }) { viewmodel, state ->
        Scaffold(scaffoldState = scaffoldState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), contentAlignment = Alignment.Center
            ) {
                when (state) {
                    ExampleState.Loading -> CircularProgressIndicator()
                    ExampleState.onLoginHub -> Button(onClick = {
                        viewmodel.sendAction(ExampleAction.OnBtnClick)
                    }) {
                        Text(text = "login")
                    }
                    else -> {

                    }
                }
            }
        }
    }
}