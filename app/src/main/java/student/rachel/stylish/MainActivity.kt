package student.rachel.stylish

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import student.rachel.stylish.facebook.UserManager
import student.rachel.stylish.databinding.ActivityMainBinding
import java.util.*


enum class NavigationButton { Home, Catalog, Cart,  Profile }

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }//要用到的時候再創建才不會浪費記憶體資源

    lateinit var binding: ActivityMainBinding
    val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginDialog = BottomSheetDialog(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profileFragment -> {
                    if (UserManager.userTokenValue == null) {
                        loginStylishViaFacebook(loginDialog)
                        Log.i("loginStylishViaFacebook", "loginStylishViaFacebook")
                        false
                    } else {
                        findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_profileFragment)
                        true
                    }
                }
                R.id.cartFragment -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_cartFragment)
                    true
                }
                R.id.catalogFragment -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_catalogFragment)
                    true
                }
                else -> {
                    findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_homeFragment)
                    true
                }
            }
        }
        binding.setLifecycleOwner(this)
        mainViewModel.signItem.observe(this, androidx.lifecycle.Observer {
            it?.let {
                UserManager.userTokenValue = it.accessToken
                displayLoginSuccess()
            }
        })
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    loginDialog.dismiss()
                    loginResult?.accessToken?.let {
                        mainViewModel.postProperty(it.token)
                    }
                }

                override fun onCancel() {
                }

                override fun onError(exception: FacebookException) {
                }
            })
    }
    override fun onActivityResult( requestCode: Int, resultCode: Int, data: Intent?
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun displayLoginSuccess() {
        val successDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_login_success, null)
        successDialog.setContentView(view)
        successDialog.show()
        Handler().postDelayed({
            successDialog.dismiss()
            findNavController(R.id.myNavHostFragment).navigate(R.id.action_global_profileFragment)
            binding.bottomNavigation.selectedItemId = R.id.profileFragment
        }, 1000)
    }
    fun loginStylishViaFacebook(dialog: BottomSheetDialog) {
        val viewLogin = layoutInflater.inflate(R.layout.dialog_ask_login, null)
        val close = viewLogin.findViewById<Button>(R.id.closeButton)
        close.setOnClickListener {
            dialog.dismiss()
        }
        val loginButton = viewLogin.findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(
                    this,
                    Arrays.asList("user_photos", "public_profile", "email")
                )
        }
        dialog.setContentView(viewLogin)
        dialog.show()
    }
    fun displayAddToCartSuccess() {
        val successDialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_add2_cart_success, null)
        successDialog.setContentView(view)
        successDialog.show()
        Handler().postDelayed({
            successDialog.dismiss()
        }, 1000)
    }
    fun setToolbarTitleAndLogo(navigationButton: NavigationButton) {
        binding.toolbar.setNavigationIcon(R.drawable.drawer_icon_nav)
        when (navigationButton) {
            NavigationButton.Home -> {
                binding.toolbarTitle.text = ""
                binding.toolbar.setLogo(R.mipmap.image_logo02)
            }
            NavigationButton.Catalog -> {
                binding.toolbarTitle.text = getString(R.string.catalog_string)
                binding.toolbar.logo = null
            }
            NavigationButton.Cart -> {
                binding.toolbarTitle.text = getString(R.string.cart_string)
                binding.toolbar.logo = null
            }
            NavigationButton.Profile -> {
                binding.toolbarTitle.text = getString(R.string.profile_string)
                binding.toolbar.logo = null
            }
        }
    }
    fun setToolbarAndBottomNavigationVisible(visible:Boolean) {
        if(visible) {
            binding.toolbar.visibility = View.VISIBLE
            binding.toolbarTitle.visibility = View.VISIBLE
            binding.bottomNavigation.visibility = View.VISIBLE
        }
        else {
            binding.toolbar.visibility = View.GONE
            binding.toolbarTitle.visibility = View.GONE
            binding.bottomNavigation.visibility = View.GONE
        }
    }

}
