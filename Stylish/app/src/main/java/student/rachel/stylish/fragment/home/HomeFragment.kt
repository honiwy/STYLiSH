package student.rachel.stylish.fragment.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import student.rachel.stylish.MainActivity
import student.rachel.stylish.NavigationButton
import student.rachel.stylish.NavigationDirections
import student.rachel.stylish.R
import student.rachel.stylish.`object`.Product
import student.rachel.stylish.databinding.FragmentHomeBinding
import androidx.lifecycle.Observer


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }//要用到的時候再創建才不會浪費記憶體資源

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setToolbarTitleAndLogo(NavigationButton.Home)

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the DemoViewModel
        binding.viewModel = homeViewModel




        binding.productGrid.adapter = HotAdapter(HotAdapter.OnClickListener {
            homeViewModel.displayHotDetails(it)
        })//HotAdapter()

        homeViewModel.navigateToSelectedHot.observe(this, Observer {dataItem->

            fun navigateDetail(product: Product) {
                findNavController().navigate(NavigationDirections.actionGlobalProductDetailFragment(product))
                (activity as MainActivity).setToolbarAndBottomNavigationVisible(false)

                homeViewModel.displayHotDetailsComplete()
            }

            when (dataItem) {
                is DataItem.SingleImgItem ->  navigateDetail(dataItem.product)
                is DataItem.MultipleImgItem -> navigateDetail(dataItem.product)
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

fun createMockData(): ArrayList<DataItem> {
    val dataItems: ArrayList<DataItem> = ArrayList()

    val item1: DataItem.HeaderMainPage = DataItem.HeaderMainPage("title")
    dataItems.add(item1)
    val product = Product()
    product.title = "Warm coat"
    product.description = "It's just in style!"
    val item2: DataItem.SingleImgItem = DataItem.SingleImgItem(product)
    val item3: DataItem.MultipleImgItem = DataItem.MultipleImgItem(product)
    dataItems.add(item2)
    dataItems.add(item3)
    dataItems.add(item1)
    dataItems.add(item3)

    return dataItems
}

