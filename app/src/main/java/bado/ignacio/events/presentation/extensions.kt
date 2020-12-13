package bado.ignacio.events.presentation

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.threeten.bp.LocalDateTime
import java.util.Locale

fun String.toDate(): LocalDateTime {
    return LocalDateTime.parse(this) // ToDo: parse to ISO_INSTANT
}

fun LocalDateTime.pretty(): String {
    val month = month.toString().toLowerCase(Locale.getDefault())
    val monthPretty = Character.toUpperCase(month[0]) + month.substring(1)
    return "$monthPretty $dayOfMonth, $year"
}

/**
 * Turn the first character to uppercase
 */
fun String.firstToUpper(): String {
    return Character.toUpperCase(this[0]) + this.substring(1)
}

fun Fragment.displayHomeAsUp() {
    (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}