package student.rachel.stylish.`object`.payment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PurchaseInfo (
    var prime: String,
    var order: Order
) : Parcelable