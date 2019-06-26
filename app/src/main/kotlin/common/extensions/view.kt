package common.extensions

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.lenkasteigaufova.restauracny_system_klient.R
import com.squareup.picasso.Picasso
import java.net.URL

fun View.beVisible() { visibility = View.VISIBLE }
fun View.beInvisible()  { visibility = View.INVISIBLE }
fun View.beGone()  { visibility = View.GONE }
fun View.beInvisibleIf(shouldBeInVisible: Boolean) { visibility = if(shouldBeInVisible) View.INVISIBLE else View.VISIBLE }
fun View.beVisibleIf(shouldBeVisible: Boolean) { visibility = if(shouldBeVisible) View.VISIBLE else View.GONE }
fun View.beGoneIf(shouldBeGone: Boolean) = beVisibleIf(!shouldBeGone)

fun View.toast(text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

val View.preferences: SharedPreferences get() = PreferenceManager.getDefaultSharedPreferences(context)

fun View.preferencePutBoolean(key: String, value: Boolean) = preferences.edit().putBoolean(key, value).apply()
fun View.preferenceGetBoolean(key: String) = preferences.getBoolean(key, false)

inline fun <reified A: Activity> View.start() = context.startActivity(Intent(context, A::class.java))
inline fun <reified A: Activity> Activity.start() = this.startActivity(Intent(this, A::class.java))


fun ImageView.load(link: String) = Picasso.with(context).load(link).error(R.drawable.food1).into(this)
fun ImageView.load(link: URL) = Picasso.with(context).load(link.toString()).error(R.drawable.food1).into(this)
