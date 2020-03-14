package student.rachel.stylish.facebook

import android.content.SharedPreferences
import student.rachel.stylish.StylishApplication

val USER_TOKEN_KEY: String = "facebook_token"

object UserManager {

    private const val PRIVATE_MODE = 0
    private const val PREF_NAME = "facebook-login-request"
    private val sharedPreferences: SharedPreferences =
        StylishApplication.appContext.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
    var userTokenValue: String?
        get() {
            return sharedPreferences.getString(
                USER_TOKEN_KEY, null)
        }
        set(userTokenValue) {
            sharedPreferences.edit()
                .putString(USER_TOKEN_KEY, userTokenValue)
                .apply()
        }
}