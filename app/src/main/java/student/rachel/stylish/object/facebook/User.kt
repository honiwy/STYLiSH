package student.rachel.stylish.`object`.facebook

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var id: Int,
    val provider: String ="facebook",
    var name:String,
    var email:String,
    var picture:String
    ): Parcelable