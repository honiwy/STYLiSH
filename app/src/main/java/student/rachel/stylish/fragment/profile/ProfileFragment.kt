package student.rachel.stylish.fragment.profile


import android.os.Bundle
import android.os.UserManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_profile.*
import student.rachel.stylish.MainActivity
import student.rachel.stylish.NavigationButton
import student.rachel.stylish.R
import student.rachel.stylish.databinding.FragmentProfileBinding


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }//要用到的時候再創建才不會浪費記憶體資源

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setToolbarTitleAndLogo(
            NavigationButton.Profile
        )
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.setLifecycleOwner(this)
        binding.profileFragment = this
        binding.profileViewModel = profileViewModel
        profileViewModel.getUser()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun findRelativeToast(view: View) {
        showToast(
            when (view) {
                is TextView -> view.text.toString()
                is ImageView ->
                    when (view.id) {
                        R.id.awaitPayImg -> awaitPayString.text
                        R.id.awaitShipImg -> awaitShipString.text
                        R.id.shippedImg -> shippedString.text
                        R.id.awaitReviewImg -> awaitReviewString.text
                        R.id.exchangeImg -> exchangeString.text
                        R.id.starImg -> starString.text
                        R.id.bellImg -> bellString.text
                        R.id.refundImg -> refundString.text
                        R.id.addressImg -> addressString.text
                        R.id.messageImg -> messageString.text
                        R.id.feedbackImg -> feedbackString.text
                        R.id.phoneBindingImg -> phoneBindingString.text
                        R.id.settingImg -> settingString.text
                        else -> ""
                    }
                else -> "Not support this kind of view"
            }
        )
    }

    fun showToast(info: CharSequence) {
        Toast.makeText(context, "<$info> COMING SOON", Toast.LENGTH_SHORT).show()
    }

}

