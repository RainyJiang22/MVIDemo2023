package com.example.core.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import com.example.core.R

/**
 * @author jiangshiyu
 * @date 2023/12/4
 */
internal object Utils {

    /**
     * 复制剪切板
     */
    fun copyText(context: Context, text: String) {
        val monitor = context.getString(R.string.monitor_monitor)
        val clipBoardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(monitor, text)
        clipBoardManager.setPrimaryClip(clipData)
    }

    /**
     * 分享文字
     */
    fun shareText(context: Context, text: String) {
        val monitor = context.getString(R.string.monitor_monitor)
        val shareIntent = ShareCompat.IntentBuilder(context)
            .setText(text)
            .setType("text/plain")
            .setChooserTitle(monitor)
            .setSubject(monitor)
            .createChooserIntent()
        if (context !is Activity) {
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(shareIntent)
    }

    /**
     * 分享文件
     */
    fun shareFile(context: Context, uri: Uri) {
        val monitor = context.getString(R.string.monitor_monitor)
        val shareIntent = ShareCompat.IntentBuilder(context)
            .setStream(uri)
            .setType(context.contentResolver.getType(uri))
            .setChooserTitle(monitor)
            .setSubject(monitor)
            .intent
        shareIntent.apply {
            clipData = ClipData.newRawUri(monitor, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooserIntent = Intent.createChooser(shareIntent, monitor)
        if (context !is Activity) {
            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooserIntent)
    }
}