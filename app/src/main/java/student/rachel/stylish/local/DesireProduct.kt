package student.rachel.stylish.local

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import student.rachel.stylish.`object`.Color

//@Parcelize
//@TypeConverters(Converter::class)
@Entity(tableName = "customer_desire_checkout_table")
data class DesireProduct (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,//	Product id.

    @ColumnInfo(name = "product_id")
    var productId : Long = 0L,//	Main image.

    @ColumnInfo(name = "product_image")
    var mainImage: String = "",//	Main image.

    @ColumnInfo(name = "product_name")
    var title: String = "",//	Product title.

    @Embedded
    var color: Color = Color("", ""), // color choices.

    @ColumnInfo(name = "product_size")
    var size: String = "", //size choices.

    @ColumnInfo(name = "product_price")
    var price: Long = -1,//	Product price.

    @ColumnInfo(name = "desire_amount")
    var qty: Int = 0,

    @ColumnInfo(name = "stock_amount")
    var stock: Int = 0

)
//    : Parcelable {
//    @ColumnInfo(name = "color_code")
//    var colorCode = color.code
//}