package com.example.core.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.core.R
import com.example.core.db.Monitor
import com.example.core.db.MonitorDataBase
import com.example.core.db.MonitorStatus
import kotlinx.coroutines.launch

/**
 * @author jiangshiyu
 * @date 2023/12/5
 */
class MonitorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonitorTheme {
                val queryFlow = MonitorDataBase.instance.monitorDao.queryFlow(limit = 300)
                val monitorList by queryFlow.collectAsState(initial = emptyList())
                MonitorPage(
                    onClickBack = ::onClickBack,
                    onClickClear = ::onClickClear,
                    monitorList = monitorList,
                    onClickMonitorItem = ::onClickMonitorItem
                )
            }
        }
    }

    private fun onClickBack() {
        finish()
    }

    private fun onClickClear() {
        lifecycleScope.launch {
            MonitorDataBase.instance.monitorDao.deleteAll()
        }
    }

    private fun onClickMonitorItem(monitor: Monitor) {
        //todo 详情页
    }
}

@Composable
private fun MonitorPage(
    onClickBack: () -> Unit,
    onClickClear: () -> Unit,
    monitorList: List<Monitor>,
    onClickMonitorItem: (Monitor) -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MonitorTopBar(onClickBack, onClickClear)
        }) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(bottom = 60.dp)
        ) {
            items(
                items = monitorList,
                key = {
                    it.id
                },
                contentType = {
                    "monitor"
                }
            ) {
                MonitorItem(monitor = it, onClick = onClickMonitorItem)
            }
        }
    }
}

@Composable
private fun MonitorItem(monitor: Monitor, onClick: (Monitor) -> Unit) {
    val titleColor: Int
    val subtitleColor: Int
    when (monitor.httpStatus) {
        MonitorStatus.Requesting -> {
            titleColor = R.color.monitor_http_status_requesting
            subtitleColor = R.color.monitor_http_status_requesting_sub
        }

        MonitorStatus.Complete -> {
            titleColor = if (monitor.responseCode == 200) {
                R.color.monitor_http_status_successful
            } else {
                R.color.monitor_http_status_unsuccessful
            }
            subtitleColor = if (monitor.responseCode == 200) {
                R.color.monitor_http_status_successful_sub
            } else {
                R.color.monitor_http_status_unsuccessful_sub
            }
        }

        MonitorStatus.Failed -> {
            titleColor = R.color.monitor_http_status_unsuccessful
            subtitleColor = R.color.monitor_http_status_unsuccessful_sub
        }

    }
    val titleTextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp,
        color = colorResource(id = titleColor),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium
    )
    val subtitleTextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.2.sp,
        color = colorResource(id = subtitleColor),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(monitor)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, top = 10.dp)
        ) {
            Text(
                modifier = Modifier
                    .width(width = 40.dp),
                text = monitor.responseCodeFormat,
                style = titleTextStyle
            )
            Column(
                modifier = Modifier
                    .weight(weight = 1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.weight(weight = 1f),
                        text = monitor.pathWithQuery,
                        style = titleTextStyle
                    )
                    Text(
                        modifier = Modifier,
                        text = monitor.id.toString(),
                        style = titleTextStyle
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(
                            top = 3.dp,
                            bottom = 3.dp
                        ),
                    text = String.format("%s://%s", monitor.scheme, monitor.host),
                    style = subtitleTextStyle
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier,
                        text = monitor.requestDateMDHMS,
                        style = subtitleTextStyle
                    )
                    Text(
                        modifier = Modifier,
                        text = monitor.requestDurationFormat,
                        style = subtitleTextStyle
                    )
                    Text(
                        modifier = Modifier,
                        text = monitor.totalSizeFormat,
                        style = subtitleTextStyle
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MonitorTopBar(
    onClickBack: () -> Unit,
    onClickClear: () -> Unit,
) {
    TopAppBar(modifier = Modifier,
        title = {
            Text(
                modifier = Modifier,
                fontSize = 21.sp,
                text = stringResource(R.string.monitor_monitor)
            )
        },
        navigationIcon = {
            IconButton(
                content = {
                    Icon(
                        modifier = Modifier
                            .size(size = 26.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                },
                onClick = onClickBack
            )
        },

        actions = {
            IconButton(
                content = {
                    Icon(
                        modifier = Modifier
                            .size(size = 26.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null
                    )
                },
                onClick = onClickClear

            )
        })
}