package student.rachel.stylish.`object`

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductsResult(
val error: String? = null,
@Json(name = "data") val productList: List<Product>? = null,
@Json(name = "next_paging") val nextPaging: Int? = null
) : Parcelable