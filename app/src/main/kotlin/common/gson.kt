package common

import android.content.SharedPreferences
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Response
import java.lang.reflect.Type


val gson = Gson()

fun Any.toJson(): String = gson.toJson(this)
inline fun <reified T: Any> String.jsonTo(): T = gson.fromJson(this, T::class.java)
inline fun <reified T: Any> Bundle.getFromJson(key: String): T = gson.fromJson(this.getString(key), T::class.java)
inline fun <reified T: Any> SharedPreferences.getFromJson(key: String): T = gson.fromJson(this.getString(key, ""), T::class.java)