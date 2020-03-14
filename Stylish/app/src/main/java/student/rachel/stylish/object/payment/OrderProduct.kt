package student.rachel.stylish.`object`.payment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import student.rachel.stylish.`object`.Color

@Parcelize
data class OrderProduct(
    var id: String = "",
    var name: String = "",
    var price: Long = 0L,
    var color: Color,
    var size: String = "",
    var qty: Int = 0
) : Parcelable