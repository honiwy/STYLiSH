package student.rachel.stylish.`object`.payment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Recipient(
    var name: String = "",
    var phone: String = "",
    var email: String = "",
    var address: String = "",
    var time: String = ""
) : Parcelable