package com.example.mvidemo.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.simple.bfslauncher.ITask

/**
 * @author jiangshiyu
 * @date 2024/7/25
 */
class MainTask1 : ITask {
    override fun handleTask(context: Context) {
        Toast.makeText(context, "Task1", Toast.LENGTH_SHORT).show()
        Log.e("hello","task1 run")
    }
}