package student.rachel.stylish.`object`.facebook

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignInResponse(
    @Json(name = "data")var signIn: SignIn,
    var error: String = ""
) : Parcelable

