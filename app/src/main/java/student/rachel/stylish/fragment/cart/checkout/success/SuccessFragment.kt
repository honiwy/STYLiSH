package student.rachel.stylish.fragment.cart.checkout.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import student.rachel.stylish.MainActivity
import student.rachel.stylish.NavigationDirections
import student.rachel.stylish.R
import student.rachel.stylish.databinding.FragmentCheckoutSuccessBinding

class SuccessFragment : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCheckoutSuccessBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_checkout_success, container, false)

        binding.buttonKeepShopping.setOnClickListener {
            it.findNavController().navigate(NavigationDirections.actionGlobalHomeFragment())
            (activity as MainActivity).binding.bottomNavigation.selectedItemId = R.id.homeFragment
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).binding.toolbarTitle.text = getString(R.string.checkout_result)
        (activity as MainActivity).binding.toolbar.logo = null//
        (activity as MainActivity).binding.toolbar.navigationIcon = null
        (activity as MainActivity).binding.bottomNavigation.visibility = View.GONE//
    }
}