package student.rachel.stylish.fragment.catalog.subcatagory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SubcatagoryViewModelFactory(private val catalogTitle: String) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubcatagoryViewModel::class.java)) {
            return SubcatagoryViewModel(
                catalogTitle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

