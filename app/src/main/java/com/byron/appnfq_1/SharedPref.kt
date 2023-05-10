import android.content.Context
import android.content.SharedPreferences
import com.byron.appnfq_1.login.model.DataToken
import com.google.gson.GsonBuilder

class SharedPref(context: Context) {

    val preferences: SharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun put(dataToken: DataToken, key: String) {
        val jsonString = GsonBuilder().create().toJson(dataToken)
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        val value = preferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun removeToken() {
        val editor = preferences.edit()
        editor.remove("token")
        editor.apply()
    }

    fun saveBaseUrl(baseUrl: String, key: String) {
        preferences.edit().putString(key, baseUrl).apply()
    }
    fun getBaseUrl(key: String): String? {
        return preferences.getString(key, null)
    }
}
