package student.rachel.stylish.fragment.cart.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import student.rachel.stylish.MainActivity
import student.rachel.stylish.NavigationDirections
import student.rachel.stylish.R
import student.rachel.stylish.databinding.FragmentCheckoutBinding
import student.rachel.stylish.local.LocalDatabase
import student.rachel.stylish.fragment.cart.CartViewModelFactory

class CheckoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCheckoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = LocalDatabase.getInstance(application).localDatabaseDao

        val viewModelFactory = CartViewModelFactory(dataSource, application)

        val viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(CheckoutViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = CheckoutAdapter(viewModel)
        viewModel.checkoutProducts.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.countTotal()
            }
        })


        viewModel.checkoutSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.actionGlobalSuccessFragment())
                viewModel.onSuccessNavigated()
            }
        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).binding.toolbarTitle.text = getString(R.string.chechout_string)
        (activity as MainActivity).binding.toolbar.logo = null
        (activity as MainActivity).binding.toolbar.setNavigationIcon(R.drawable.back_icon_nav)
        (activity as MainActivity).binding.bottomNavigation.visibility = View.GONE
        (activity as MainActivity).binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
            (activity as MainActivity).binding.bottomNavigation.visibility = View.VISIBLE
        }
    }
}