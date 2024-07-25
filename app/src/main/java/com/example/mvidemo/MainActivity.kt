package com.example.mvidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mvidemo.ui.MainTask1
import com.example.mvidemo.ui.MainTask2
import com.example.mvidemo.ui.MainTask3
import com.example.mvidemo.ui.example.ExampleScreen
import com.example.mvidemo.ui.theme.MVIDemo2023Theme
import com.simple.bfslauncher.TaskGraph
import github.leavesczy.monitor.MonitorInterceptor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val graph = TaskGraph(this)
        val task1 = MainTask1()
        val task2 = MainTask2()
        val task3 = MainTask3()
        graph.addDependence(task2, task3)
        graph.addDependence(task1, task2)
        graph.startAll()


        setContent {
            MVIDemo2023Theme {
                // A surface container using the 'background' color from the theme
                ExampleScreen()
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MVIDemo2023Theme {
        Greeting("Android")
    }
}