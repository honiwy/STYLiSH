package student.rachel.stylish.fragment.catalog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import student.rachel.stylish.MainActivity
import student.rachel.stylish.NavigationButton
import student.rachel.stylish.R
import student.rachel.stylish.databinding.FragmentCatalogBinding


/**
 * A simple [Fragment] subclass.
 */

open class CatalogFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).setToolbarTitleAndLogo(NavigationButton.Catalog)
        (activity as MainActivity).setToolbarAndBottomNavigationVisible(true)
        val binding: FragmentCatalogBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_catalog, container, false)

        binding.viewPager.adapter = FragmentPagerAdapter(childFragmentManager)
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        // Inflate the layout for this fragment
        return binding.root
    }

}

