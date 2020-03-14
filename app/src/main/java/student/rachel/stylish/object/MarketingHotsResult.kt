package student.rachel.stylish.`object`

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import student.rachel.stylish.fragment.home.DataItem

/**
 * Created by Wayne Chen in Jul. 2019.
 */
@Parcelize
data class MarketingHotsResult(
    val error: String? = null,
    @Json(name = "data") val hotsList: List<Hots>? = null
) : Parcelable {

   fun toDataItems(): List<DataItem> {
        val items = mutableListOf<DataItem>()

        hotsList?.let {
            for ((title, products) in it) {
                items.add(
                    DataItem.HeaderMainPage(
                        title
                    )
                )
                for ((index, product) in products.withIndex()) {
                    when (index % 2) {
                        0 -> items.add(
                            DataItem.SingleImgItem(
                                product
                            )
                        )
                        1 -> items.add(
                            DataItem.MultipleImgItem(
                                product
                            )
                        )
                    }
                }
            }
        }
        return items
    }
}