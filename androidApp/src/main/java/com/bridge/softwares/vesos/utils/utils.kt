package com.bridge.softwares.vesos.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.text.Html
import android.text.Html.ImageGetter
import android.util.Base64
import androidx.core.text.HtmlCompat
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
    try {
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
                putExtra(
                    Intent.EXTRA_TEXT, Html.fromHtml(
                        body,
                        HtmlCompat.FROM_HTML_MODE_LEGACY,
                        { source ->
                            val data: ByteArray = Base64.decode(source, Base64.DEFAULT)
                            val bitmap: Bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                            BitmapDrawable(context.resources, bitmap)
                        },
                        null
                    )
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
