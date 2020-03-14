package student.rachel.stylish.fragment.cart


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import student.rachel.stylish.MainActivity
import student.rachel.stylish.NavigationButton
import student.rachel.stylish.NavigationDirections
import student.rachel.stylish.R
import student.rachel.stylish.databinding.FragmentCartBinding
import student.rachel.stylish.local.LocalDatabase


/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setToolbarTitleAndLogo(
            NavigationButton.Cart
        )

        val binding: FragmentCartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = LocalDatabase.getInstance(application).localDatabaseDao

        val viewModelFactory = CartViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(CartViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        val adapter = CartProductAdapter(viewModel)
        binding.cartGrid.adapter = adapter

        viewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        binding.buttonCheckout.setOnClickListener {
            findNavController().navigate(NavigationDirections.actionGlobalCheckoutFragment())
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}
