package student.rachel.stylish.fragment.product.add2cart

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import student.rachel.stylish.StylishApplication
import student.rachel.stylish.`object`.Color
import student.rachel.stylish.`object`.Product
import student.rachel.stylish.`object`.Variant
import student.rachel.stylish.local.LocalDatabase
import student.rachel.stylish.local.DesireProduct
import student.rachel.stylish.local.formatDesireProducts

class Add2cartViewModel(product: Product) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _addedProduct = MutableLiveData<Product>()
    val addedProduct: LiveData<Product>
        get() = _addedProduct
    private val _choseColor = MutableLiveData<Color>()
    val color: LiveData<Color>
        get() = _choseColor

    fun changeColor(color: Color) {
        _choseColor.value = color
        _choseSize.value = null
        updateSize(color.code)
    }

    private var _variants = MutableLiveData<List<Variant>>()
    val variants: LiveData<List<Variant>>
        get() = _variants

    private fun updateSize(colorCode: String) {
        _variants.value = _addedProduct.value?.variants?.filter {
            it.colorCode == colorCode
        }
    }

    private val _choseSize = MutableLiveData<String>()
    val size: LiveData<String>
        get() = _choseSize

    fun changeSize(size: String) {
        _choseSize.value = size
        updateStock(size)
    }

    private var _stock = MutableLiveData<Int>()
    val stock: LiveData<Int>
        get() = _stock

    private fun updateStock(size: String) {
        var stockCount = MutableLiveData<Int>()
        stockCount.value = 0
        _variants.value?.forEach {
            if (it.size == size) {
                stockCount.value = stockCount.value?.plus(it.stock)
            }
        }
        _stock.value = stockCount.value
    }

    var requireAmount = MutableLiveData<Int>()

    init {
        _addedProduct.value = product
        requireAmount.value = 1
    }

    fun plusOne() {
        _stock.value?.let {
            if (requireAmount.value?.compareTo(it) == -1)
                requireAmount.value = requireAmount.value?.plus(1)
        }
    }

    fun minusOne() {
        if (requireAmount.value?.compareTo(1) == 1)
            requireAmount.value = requireAmount.value?.minus(1)
    }

    fun isOutOfStock(): Boolean {
        var boo = true
        if (requireAmount.value?.compareTo(0) == 1) {
            _stock.value?.let {
                boo = (requireAmount.value?.compareTo(it) == 1)
            }
        }
        return boo
    }

    @InverseMethod("convertIntToString")
    fun convertStringToInt(value: String): Int {
        return try {
            value.toInt()
        } catch (e: NumberFormatException) {
            1
        }
    }

    fun convertIntToString(value: Int): String {
        return value.toString()
    }


    private val products = LocalDatabase.getInstance(
        StylishApplication.appContext
    ).localDatabaseDao.getAllProducts()
    val productsString = Transformations.map(products) { products ->
        formatDesireProducts(products, StylishApplication.appContext.resources)
    }


    private suspend fun insert(desireProduct: DesireProduct) {
        withContext(Dispatchers.IO) {
            LocalDatabase.getInstance(StylishApplication.appContext).localDatabaseDao.insert(
                desireProduct
            )
        }
    }

    private suspend fun get(name: String, size: String, color: String): DesireProduct? {
        return withContext(Dispatchers.IO) {
            LocalDatabase.getInstance(StylishApplication.appContext)
                .localDatabaseDao.get(name, size, color)
        }
    }

    private suspend fun update(desireProduct: DesireProduct) {
        withContext(Dispatchers.IO) {
            LocalDatabase.getInstance(StylishApplication.appContext)
                .localDatabaseDao.update(desireProduct)
        }
    }

    fun onAddToCart() {
        uiScope.launch {
            get(
                _addedProduct.value?.title ?: "",
                _choseSize.value ?: "",
                _choseColor.value?.code ?: ""
            ).let { existedItem ->
                if (existedItem != null) {
                    requireAmount.value?.let {
                        existedItem.qty = existedItem.qty.plus(it)
                        if (existedItem.qty > existedItem.stock)
                            existedItem.qty = existedItem.stock
                        update(existedItem)
                    }
                } else {
                    val newProduct = DesireProduct()
                    requireAmount.value?.let {
                        newProduct.qty = it
                    }
                    _choseSize.value?.let {
                        newProduct.size = it
                    }
                    _choseColor.value?.let {
                        newProduct.color.code = it.code
                    }
                    _addedProduct.value?.let {
                        newProduct.title = it.title
                        newProduct.price = it.price
                        newProduct.mainImage = it.mainImage
                    }
                    _stock.value?.let {
                        newProduct.stock = it
                    }
                    insert(newProduct)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
// adb uninstall student.rachel.stylish
// adb reboot