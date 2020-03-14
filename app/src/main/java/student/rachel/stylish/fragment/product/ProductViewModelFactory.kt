package student.rachel.stylish.fragment.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import student.rachel.stylish.`object`.Product
import student.rachel.stylish.fragment.product.add2cart.Add2cartViewModel


class ProductViewModelFactory(private val product: Product) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(product) as T
        }
        if (modelClass.isAssignableFrom(Add2cartViewModel::class.java)) {
            return Add2cartViewModel( product ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}