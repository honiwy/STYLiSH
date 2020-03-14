package student.rachel.stylish.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hots(
    var title: String,//	Title of the hots section.
    var products: List<Product>//	Products in the hots section.
): Parcelable