package student.rachel.stylish.fragment.cart.checkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.android.synthetic.main.layout_receiver_info.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import student.rachel.stylish.R
import student.rachel.stylish.`object`.payment.*
import student.rachel.stylish.facebook.UserManager
import student.rachel.stylish.fragment.RetrofitApi
import student.rachel.stylish.local.DesireProduct
import student.rachel.stylish.local.LocalDatabaseDao

class CheckoutViewModel(
    database: LocalDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val checkoutProducts = database.getAllProducts()
    var totalPrice: Long = 0L
    val shipmentFee: Long = 120L
    var totalPriceWithShipment: Long = 0L
    var accumulatedProductAmount : Int = 0

    fun countTotal() {
        checkoutProducts.value?.let{
            it.forEach { desireProduct ->
                accumulatedProductAmount += 1
                totalPrice += desireProduct.qty * desireProduct.price
            }
            totalPriceWithShipment = totalPrice + shipmentFee
        }
    }

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var deliverTime = MutableLiveData<String>()

    fun updateDeliverTime(selectedId : Int){
        when(selectedId){
            R.id.radiobtn_morning -> {
                deliverTime.value = "morning"
            }
            R.id.radiobtn_afternoon -> {
                deliverTime.value = "afternoon"
            }
            else -> {
                deliverTime.value = "anytime"
            }
        }
    }

    var uploadProducts : MutableList<OrderProduct> = arrayListOf()

    private fun transformProductList(products:List<DesireProduct>){
        products.forEach {
            var orderProduct = OrderProduct(
                it.productId.toString(),
                it.title,
                it.price,
                it.color,
                it.size,
                it.qty
            )
            uploadProducts.add(orderProduct)
        }
    }

    private fun checkAllInfoFilled():Boolean{
        return(name.value!=null && email.value!=null && phone.value!= null && address.value != null && deliverTime.value != null)
    }

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _data = MutableLiveData<Data>()

    val data: LiveData<Data>
        get() = _data

    var checkoutSuccess = MutableLiveData<Boolean>()
    fun postPurchase(prime: String) {
        coroutineScope.launch {
            if (checkAllInfoFilled()) {
                transformProductList(checkoutProducts.value!!)

                var body = PurchaseInfo(prime,
                    Order( "delivery", "credit_card", totalPrice, shipmentFee, totalPriceWithShipment,
                        Recipient( name.value!!, phone.value!!, email.value!!, address.value!!, deliverTime.value!! ), uploadProducts ) )

                // Get the Deferred object for our Retrofit request
                var postCheckoutDeferred = RetrofitApi.retrofitService.postCheckout(
                    token = "Bearer ${UserManager.userTokenValue}", purchaseInfo = body )
                try {
                    // this will run on a thread managed by Retrofit
                    val listResult = postCheckoutDeferred.await()
                    _data.value = listResult
                    checkoutSuccess.value = true
                } catch (e: Exception) {
                    Log.i("Something Wrong", "exception=${e.message}")
                }
            }
        }
    }

    fun onSuccessNavigated() {
        checkoutSuccess.value = null
    }
}