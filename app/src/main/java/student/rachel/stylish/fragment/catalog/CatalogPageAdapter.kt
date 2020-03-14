package student.rachel.stylish.fragment.catalog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import student.rachel.stylish.R
import student.rachel.stylish.StylishApplication
import student.rachel.stylish.fragment.catalog.subcatagory.SubcatagoryFragment


private const val NUM_PAGES = 3

class FragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SubcatagoryFragment(
                "women"
            )
            1 -> SubcatagoryFragment(
                "men"
            )
            else -> SubcatagoryFragment(
                "accessories"
            )
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> StylishApplication.appContext.getString(R.string.women)
            1 -> StylishApplication.appContext.getString(R.string.men)
            else -> StylishApplication.appContext.getString(R.string.accessory)
        }
    }

    override fun getCount(): Int {
        return NUM_PAGES
    }
}

