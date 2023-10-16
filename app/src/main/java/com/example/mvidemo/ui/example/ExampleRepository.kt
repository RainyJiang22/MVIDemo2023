package com.example.mvidemo.ui.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

/**
 * @author jiangshiyu
 * @date 2023/10/16
 */
object ExampleRepository {

    fun fetchLogin() = flow {
        delay(2500)
        emit(Random.nextInt(30))
    }.flowOn(Dispatchers.IO)
}