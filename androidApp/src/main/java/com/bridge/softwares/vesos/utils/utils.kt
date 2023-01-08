package com.bridge.softwares.vesos.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri


fun shareEmail(
    context: Context,
    subject: String,
    addresses: Array<String>,
    cc: Array<String>? = null,
    body: String,
    onError: ((Exception) -> Unit)? = null,
) {
    try {
        val shareIntent = Intent.createChooser(
            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_CC, cc)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(
                    Intent.EXTRA_TEXT,
                    body
                    //Html.fromHtml(body, HtmlCompat.FROM_HTML_MODE_LEGACY)
                )
            },
            subject
        )

        context.startActivity(shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    } catch (ex: ActivityNotFoundException) {
        // No email apps installed in the phone
        onError?.invoke(ex)
    } catch (ex: RuntimeException) {
        onError?.invoke(ex)
    }
}
