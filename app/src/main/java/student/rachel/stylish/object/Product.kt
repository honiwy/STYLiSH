package student.rachel.stylish.`object`

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var id: Long = -1,//	Product id.
    var title: String = "",//	Product title.
    var description: String = "",//	Product description.
    var price: Long = -1,//	Product price.
    var texture: String = "",//	Product texture.
    var wash: String = "",//	The way we can wash the product.
    var place: String = "",//	Place of production.
    var note: String = "",//	The note of product.
    var story: String = "",//	Product multiline story.
    var colors: List<Color> = emptyList(),//	Possible color choices.
    var sizes: List<String> = emptyList(),//	Possible size choices.
    var variants: List<Variant> = emptyList(),//	Possible variants, including stock records.
    @Json(name = "main_image") var mainImage: String = "",//	Main image.
    var images: List<String> = emptyList()//	Other images.
): Parcelable


