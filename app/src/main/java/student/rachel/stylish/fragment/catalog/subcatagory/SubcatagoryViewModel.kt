package student.rachel.stylish.fragment.catalog.subcatagory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.rachel.stylish.fragment.RetrofitApi
import student.rachel.stylish.`object`.Product

class SubcatagoryViewModel(private val inputCatalog: String) : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of DataItem
    // with new values
    private val _products = MutableLiveData<List<Product>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val products: LiveData<List<Product>>
        get() = _products

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var nextToPage: Int? = null

    init {
        getProducts()
    }

    fun getProducts() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred =
                RetrofitApi.retrofitService.getProducts(inputCatalog, nextToPage)
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()

                var data = mutableListOf<Product>()
                _products.value?.let {
                    data.addAll(it)
                }
                listResult.productList?.let {
                    data.addAll(it)
                }
                _products.value = data
                nextToPage = listResult.nextPaging
            } catch (e: Exception) {
                Log.i("Catalog", "exception=${e.message}")
                _products.value = ArrayList()
            }
        }
    }

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProduct = MutableLiveData<Product>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProduct: LiveData<Product>
        get() = _navigateToSelectedProduct

    fun displayProductDetails(product: Product) {
        _navigateToSelectedProduct.value = product
    }

    fun displayProductDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}