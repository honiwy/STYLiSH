package student.rachel.stylish.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import student.rachel.stylish.`object`.Color

class Converter {
    /**
     * Convert [Color] to Json
     */
    @TypeConverter
    fun convertColorToJson(color: Color?): String? {
        color?.let {
            return Moshi.Builder().build().adapter<Color>(List::class.java).toJson(color)
        }
        return null
    }
    /**
     * Convert Json to [Color]
     */
    @TypeConverter
    fun convertJsonToColor(json: String?): Color? {
        json?.let {
            val type = Types.newParameterizedType(Color::class.java)
            val adapter: JsonAdapter<Color> = Moshi.Builder().build().adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }
}