package student.rachel.stylish.`object`.payment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Order(
    var shipping: String = "",
    var payment: String = "",
    var subtotal: Long = 0L,
    var freight: Long = 0L,
    var total: Long = 0L,
    var recipient: Recipient,
    var list: List<OrderProduct>
) : Parcelable