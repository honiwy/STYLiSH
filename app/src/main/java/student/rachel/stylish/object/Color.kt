package student.rachel.stylish.`object`

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Color(
    @ColumnInfo(name = "color_name")
    var name: String,//	Color's name.
    @ColumnInfo(name = "color_code")
    var code: String    //Color's hex code.
): Parcelable