package student.rachel.stylish.`object`

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Variant(
    @Json(name = "color_code") var colorCode: String,//	Hex Color Code.
    var size: String,//	Size.
    var stock: Int//	Stock.
): Parcelable