package student.rachel.stylish.fragment.catalog.subcatagory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import student.rachel.stylish.R
import androidx.lifecycle.Observer
import student.rachel.stylish.MainActivity
import student.rachel.stylish.NavigationDirections
import student.rachel.stylish.databinding.FragmentSubcatagoryBinding
import student.rachel.stylish.fragment.catalog.CatalogFragment

class SubcatagoryFragment(private val catalog: String) : CatalogFragment() {

    private val viewModel: SubcatagoryViewModel by lazy {
        ViewModelProviders.of( this,
            SubcatagoryViewModelFactory(
                catalog
            )
        )
            .get<SubcatagoryViewModel>(    SubcatagoryViewModel::class.java  ) }//要用到的時候再創建才不會浪費記憶體資源

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding: FragmentSubcatagoryBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_subcatagory,
                container,
                false
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.productsGrid.adapter =
            ProductAdapter(
                ProductAdapter.OnClickListener { product ->
                    viewModel.displayProductDetails(product)//  ProductAdapter()
                })
        viewModel.navigateToSelectedProduct.observe(this, Observer {product->
            product?.let{
                findNavController().navigate(NavigationDirections.actionGlobalProductDetailFragment(product))
                (activity as MainActivity).setToolbarAndBottomNavigationVisible(false)

                viewModel.displayProductDetailsComplete()
            }
        })
        binding.productsGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)
                    && viewModel.nextToPage != null
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    viewModel.getProducts()
                }
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setToolbarAndBottomNavigationVisible(true)
    }
}