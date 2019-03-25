package test.accenture

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Satyam
 */
object AppPreference {
    private const val NAME = "Album"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var saveData: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString("offlineData","")

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString("offlineData", value)
        }
}