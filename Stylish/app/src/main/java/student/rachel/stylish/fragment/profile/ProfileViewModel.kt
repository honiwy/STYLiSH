package student.rachel.stylish.fragment.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.rachel.stylish.`object`.facebook.User
import student.rachel.stylish.facebook.UserManager
import student.rachel.stylish.fragment.RetrofitApi

class ProfileViewModel : ViewModel() {

    // Internally, we use a MutableLiveData, because we will be updating the List of DataItem
    // with new values
    private val _user = MutableLiveData<User>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val user: LiveData<User>
        get() = _user

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
    fun getUser() {
        Log.i("Rac3","h")
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred =
                RetrofitApi.retrofitService.getUserInfo("Bearer ${UserManager.userTokenValue}")
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _user.value = listResult.user
            } catch (e: Exception) {
                Log.i("Profile", "exception=${e.message}")
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
    /**
     * TODO(09)
     * For ViewModel and LiveData
     * implementation "androidx.lifecycle:lifecycle-extensions:2.0.0"
     */
}