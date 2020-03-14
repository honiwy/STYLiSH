package student.rachel.stylish.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalDatabaseDao {
    @Insert
    fun insert(desireProduct: DesireProduct)
    @Update
    fun update(desireProduct: DesireProduct)

    @Query("SELECT * from customer_desire_checkout_table WHERE product_name = :name AND product_size = :size AND color_code = :color")
    fun get(name: String, size: String, color: String): DesireProduct?

    @Query("DELETE FROM customer_desire_checkout_table")
    fun clear()

    @Query("DELETE FROM customer_desire_checkout_table WHERE id = :key")
    fun delete(key: Long)

    @Query("SELECT * FROM customer_desire_checkout_table ORDER BY id DESC")
    fun getAllProducts(): LiveData<List<DesireProduct>>

    //@Query("SELECT * FROM customer_desire_checkout_table ORDER BY id DESC LIMIT 1")
    //fun getCurrentProduct(): DesireProduct?

}