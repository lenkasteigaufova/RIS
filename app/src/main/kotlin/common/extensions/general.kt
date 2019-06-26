package common.extensions

import android.content.ContextWrapper
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.*


inline val Float.formatted get() = String.format("%.2f", this) + "€"

inline val Long.dateFormat get() = SimpleDateFormat("dd.MM.yyyy").format(Date(this))

fun <T> T.log(tag: String = "Log") = Log.e(tag, "$this")

infix fun <T> T.and(elements: Iterable<T>) = (listOf(this) + elements)

val View.activity: AppCompatActivity get() =
    if(context is AppCompatActivity) context as AppCompatActivity
    else (context as ContextWrapper).baseContext as AppCompatActivity

fun EditText.clear() {
    text.clear()
    clearFocus()
}

fun ImageView.tintColor(@ColorRes color: Int) : Unit { colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN) }

