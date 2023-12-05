package com.example.core.db

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author jiangshiyu
 * @date 2023/12/4
 */


@Stable
internal data class MonitorPair(val name: String, val value: String)

@Stable
internal enum class MonitorStatus {
    Requesting,
    Complete,
    Failed
}

@Stable
@Entity(tableName = MonitorDataBase.MonitorTableName)
internal data class Monitor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "scheme")
    val scheme: String,
    @ColumnInfo(name = "host")
    val host: String,
    @ColumnInfo(name = "path")
    val path: String,
    @ColumnInfo(name = "query")
    val query: String,
    @ColumnInfo(name = "protocol")
    val protocol: String,
    @ColumnInfo(name = "method")
    val method: String,
    @ColumnInfo(name = "requestHeaders")
    val requestHeaders: List<MonitorPair>,
    @ColumnInfo(name = "requestBody")
    val requestBody: String?,
    @ColumnInfo(name = "requestContentType")
    val requestContentType: String,
    @ColumnInfo(name = "requestContentLength")
    val requestContentLength: Long,
    @ColumnInfo(name = "requestDate")
    val requestDate: Long,
    @ColumnInfo(name = "responseHeaders")
    val responseHeaders: List<MonitorPair>,
    @ColumnInfo(name = "responseBody")
    val responseBody: String?,
    @ColumnInfo(name = "responseContentType")
    val responseContentType: String,
    @ColumnInfo(name = "responseContentLength")
    val responseContentLength: Long,
    @ColumnInfo(name = "responseDate")
    val responseDate: Long,
    @ColumnInfo(name = "responseTlsVersion")
    val responseTlsVersion: String,
    @ColumnInfo(name = "responseCipherSuite")
    val responseCipherSuite: String,
    @ColumnInfo(name = "responseCode")
    val responseCode: Int = DEFAULT_RESPONSE_CODE,
    @ColumnInfo(name = "responseMessage")
    val responseMessage: String,
    @ColumnInfo(name = "error")
    val error: String?,
) {

    companion object {
        private const val DEFAULT_RESPONSE_CODE = -1024
    }

    val httpStatus: MonitorStatus
        get() = when {
            error != null -> {
                MonitorStatus.Failed
            }

            responseCode == DEFAULT_RESPONSE_CODE -> {
                MonitorStatus.Requesting
            }

            else -> {
                MonitorStatus.Complete
            }
        }

}