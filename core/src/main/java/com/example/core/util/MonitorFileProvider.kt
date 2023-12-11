package com.example.core.util

import android.app.Application
import androidx.core.content.FileProvider

internal class MonitorFileProvider : FileProvider() {

    override fun onCreate(): Boolean {
        ContextProvider.inject(context = context as Application)
        return super.onCreate()
    }

}