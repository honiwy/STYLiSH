package student.rachel.stylish.fragment.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import student.rachel.stylish.`object`.Product

class ProductViewModel(product: Product): ViewModel() {
    private val _selectedProduct = MutableLiveData<Product>()

    val selectedProduct: LiveData<Product>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }

    val displaySize = Transformations.map(selectedProduct) {
        val count:Int= it.sizes.count()
        when (count)
        {
            1-> it.sizes[0]
            else-> "${it.sizes[0]} - ${it.sizes[count-1]}"
        }
    }

    val displayProductStocking = Transformations.map(selectedProduct) {
        var count = 0
        it.variants.forEach{variant->
            count+=variant.stock
        }
        "${count}"
    }

}