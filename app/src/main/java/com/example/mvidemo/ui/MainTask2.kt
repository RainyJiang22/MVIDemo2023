package com.example.mvidemo.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.simple.bfslauncher.ITask

/**
 * @author jiangshiyu
 * @date 2024/7/25
 */
class MainTask2 : ITask {
    override fun handleTask(context: Context) {
        Toast.makeText(context, "Task2", Toast.LENGTH_SHORT).show()
        Log.e("hello","task2 run")
    }
}