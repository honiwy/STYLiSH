package student.rachel.stylish.`object`.facebook

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignIn(
    var user: User,
    @Json(name = "access_token")
    var accessToken: String,
    @Json(name = "access_expired")
    var accessExpired: Int
) : Parcelable