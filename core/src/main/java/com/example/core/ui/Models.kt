package com.example.core.ui

import androidx.compose.runtime.Stable

/**
 * @author jiangshiyu
 * @date 2023/12/5
 */

@Stable
internal data class MonitorDetailPageViewState(
    val title: String,
    val tabTagList: List<String>
)