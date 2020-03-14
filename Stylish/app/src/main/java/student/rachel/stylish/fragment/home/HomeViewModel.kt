package student.rachel.stylish.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.rachel.stylish.fragment.RetrofitApi

/**
 * TODO(07)
 * The [ViewModel] that is attached to the [DemoFragment].
 *
 * implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.0"
 * implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.0"
 */
class HomeViewModel : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of DataItem
    // with new values
    private val _dataItems = MutableLiveData<List<DataItem>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val dataItems: LiveData<List<DataItem>>
        get() = _dataItems

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getProperties() on init so we can display status immediately.
     */
    init {
        getProperties()
    }

    /**
     * TODO(08)
     * Gets hots property information from the Marketing Hots API Retrofit service and
     * updates the [Property Object]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     */
     private fun getProperties() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred = RetrofitApi.retrofitService.getMarketingHots()
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _dataItems.value = listResult.toDataItems()
            } catch (e: Exception) {
                Log.i("Home", "exception=${e.message}")
                _dataItems.value = ArrayList()
            }
        }
    }

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedHot = MutableLiveData<DataItem>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedHot: LiveData<DataItem>
        get() = _navigateToSelectedHot

    fun displayHotDetails(dataItem: DataItem) {
        _navigateToSelectedHot.value = dataItem
    }

    fun displayHotDetailsComplete() {
        _navigateToSelectedHot.value = null
    }


    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    /**
     * TODO(09)
     * For ViewModel and LiveData
     * implementation "androidx.lifecycle:lifecycle-extensions:2.0.0"
     */
}
