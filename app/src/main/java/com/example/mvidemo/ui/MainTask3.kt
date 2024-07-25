package com.example.mvidemo.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.simple.bfslauncher.ITask

/**
 * @author jiangshiyu
 * @date 2024/7/25
 */
class MainTask3 : ITask {
    override fun handleTask(context: Context) {
        Log.e("hello","task3 run")
        Toast.makeText(context, "Task3", Toast.LENGTH_SHORT).show()
    }
}