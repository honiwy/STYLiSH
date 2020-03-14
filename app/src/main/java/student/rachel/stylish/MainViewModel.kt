package student.rachel.stylish

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.rachel.stylish.`object`.facebook.SignIn
import student.rachel.stylish.fragment.RetrofitApi

class MainViewModel : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of DataItem
    // with new values
    private val _signIn = MutableLiveData<SignIn>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val signItem: LiveData<SignIn>
        get() = _signIn

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * TODO(08)
     * Gets hots property information from the Marketing Hots API Retrofit service and
     * updates the [Property Object]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     */
     fun postProperty(facebookToken: String) {
        Log.i("Rac1","h")
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var postPropertiesDeferred = RetrofitApi.retrofitService.postLoginInfo(accessToken = facebookToken)
            try {
                // this will run on a thread managed by Retrofit
                val listResult = postPropertiesDeferred.await()
                _signIn.value = listResult.signIn
            } catch (e: Exception) {
                Log.i("MainActivity", "exception=${e.message}")
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}