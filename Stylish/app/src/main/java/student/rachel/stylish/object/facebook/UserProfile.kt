package student.rachel.stylish.`object`.facebook

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import student.rachel.stylish.`object`.facebook.User


@Parcelize
data class UserProfile(
    @Json(name = "data")
    var user: User
) : Parcelable
