package student.rachel.stylish.fragment

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import student.rachel.stylish.`object`.facebook.UserProfile
import student.rachel.stylish.`object`.facebook.SignInResponse
import student.rachel.stylish.`object`.MarketingHotsResult
import student.rachel.stylish.`object`.ProductsResult
import student.rachel.stylish.`object`.payment.Data
import student.rachel.stylish.`object`.payment.PurchaseInfo


/**
 * TODO(01)
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 *

 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//KotlinJsonAdapterFactory把字串轉成資料
/**
 * TODO(02)
 * Build the OkHttpClient object that Retrofit will be using, making sure to add the logging interceptor for
 * check response. Setup level to Level.BODY that we will know all information about http connect.
 *
 * implementation("com.squareup.okhttp3:logging-interceptor:4.0.1")
 */
private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    .build()
//把HttpLoggingInterceptor.Level.BASIC改成HttpLoggingInterceptor.Level.BODY
/**
 * TODO(03)
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 *
 * And using an OkHttpClient with our OkHttpClient object.
 *
 * implementation "com.squareup.retrofit2:retrofit:2.5.0"
 * implementation "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
 */
val BASE_URL = "https://api.appworks-school.tw/api/1.0/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

/**
 * TODO(04)
 * A public interface that exposes the [getMarketingHots] method
 */
interface RetrofitApiService {
    /**
     * TODO(05)
     * Returns a Coroutine [Deferred] [Property Object] which can be fetched with await() if
     * in a Coroutine scope.
     *
     * Make sure our BASE_URL includes api path and api_version.
     *
     * The @GET annotation indicates that the "marketing/hots" endpoint will be requested with the GET
     * HTTP method
     */
    //HOME
    @GET("marketing/hots")
    fun getMarketingHots(): Deferred<MarketingHotsResult>
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result

    //CATALOG
    @GET("products/{catalog}")
    fun getProducts(@Path("catalog") catalog: String, @Query("paging") nextPaging: Int?): Deferred<ProductsResult>

    //PROFILE
    @GET("user/profile")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun getUserInfo(@Header("Authorization") string: String): Deferred<UserProfile>

    @POST("user/signin")
    @FormUrlEncoded//就不需要 @Header("Content-Type") type: String = "application/json",
    fun postLoginInfo(@Field("provider") provider: String = "facebook", @Field("access_token") accessToken: String ): Deferred<SignInResponse>

    @POST("order/checkout")
    @Headers("Content-Type: application/json")
    fun postCheckout( @Header("Authorization") token: String, @Body purchaseInfo: PurchaseInfo): Deferred<Data>

}

/**
 * TODO(06)
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object RetrofitApi {
    val retrofitService: RetrofitApiService by lazy {
        retrofit.create(
            RetrofitApiService::class.java
        )
    }
}
