package student.rachel.stylish.fragment.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import student.rachel.stylish.local.LocalDatabaseDao
import student.rachel.stylish.local.DesireProduct

class CartViewModel(
    val database: LocalDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val products = database.getAllProducts()


    private suspend fun delete(key: Long) {
        withContext(Dispatchers.IO) {
            database.delete(key)
        }
    }

    private suspend fun update(desireProduct: DesireProduct) {
        withContext(Dispatchers.IO) {
            database.update(desireProduct)
        }
    }

    fun onIncreaseAmount(desireProduct: DesireProduct) {
        uiScope.launch {
            if (desireProduct.qty < desireProduct.stock) {
                desireProduct.qty += 1
                update(desireProduct)
            }
        }
    }

    fun onDecreaseAmount(desireProduct: DesireProduct) {
        uiScope.launch {
            if (desireProduct.qty > 0) {
                desireProduct.qty -= 1
                update(desireProduct)
            }
        }
    }

    fun onDelete(key: Long) {
        uiScope.launch {
            delete(key)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}