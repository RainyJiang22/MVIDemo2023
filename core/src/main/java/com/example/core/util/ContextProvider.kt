package com.example.core.util

import android.app.Application

/**
 * @author jiangshiyu
 * @date 2023/12/4
 */
internal object ContextProvider {

    lateinit var context: Application
        private set

    fun inject(context: Application) {
        ContextProvider.context = context
    }
}