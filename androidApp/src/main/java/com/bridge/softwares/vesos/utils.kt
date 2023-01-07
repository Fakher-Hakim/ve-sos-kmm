package com.bridge.softwares.vesos

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File
import java.io.IOException


fun shareEmail(
    context: Context,
    subject: String,
    addresses: Array<String>,
    cc: Array<String>? = null,
    body: String,
    attachmentPath: String,
    onError: ((Exception) -> Unit)? = null
) {
    val file = File(attachmentPath)
    if (!file.exists() || !file.canRead()) {
        onError?.invoke(IOException())
    }
    val signature = Uri.fromFile(file)

    val shareIntent = Intent.createChooser(
        Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_CC, cc)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            putExtra(Intent.EXTRA_STREAM, signature)
        }, subject
    )

    try {
        context.startActivity(shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    } catch (ex: ActivityNotFoundException) {
        // No email apps installed in the phone
        onError?.invoke(ex)
    } catch (ex: RuntimeException) {
        onError?.invoke(ex)
    }
}
