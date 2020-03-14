package student.rachel.stylish.local

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import student.rachel.stylish.R

fun formatDesireProducts(nights: List<DesireProduct>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.product_name))
            append("\t${it.title}<br>")
                append(resources.getString(R.string.product_color))
                append("\t${it.color.code}<br>")
                append(resources.getString(R.string.product_size))
                append("\t${it.size}<br>")
                append(resources.getString(R.string.product_amount))
                append("\t${it.qty}<br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}