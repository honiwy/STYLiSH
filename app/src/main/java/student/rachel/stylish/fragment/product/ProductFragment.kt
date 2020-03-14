package student.rachel.stylish.fragment.product

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import student.rachel.stylish.R
import student.rachel.stylish.databinding.FragmentProductBinding
import student.rachel.stylish.fragment.product.add2cart.AddToCartDialogFragment
import student.rachel.stylish.fragment.product.add2cart.toPixel


class ProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentProductBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        binding.lifecycleOwner = this
        val product = ProductFragmentArgs.fromBundle(arguments!!).selectedProduct
        binding.viewModel = ViewModelProviders.of(this, ProductViewModelFactory(product))
            .get(ProductViewModel::class.java)
        binding.productImgGrid.adapter = ProductImgAdapter()
        binding.colorGrid.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                // Add top margin only for the first item to avoid double space between items
                outRect.right = 8.toPixel()
//                if (parent.getChildLayoutPosition(view) == 0) {
//                    outRect.left = 0
//                } else {
//                    outRect.left = 20
//                }
            }
        })
        binding.colorGrid.adapter = DisplayColorAdapter()
        binding.buttonBack.setOnClickListener {
            backToList()
        }
        binding.buttonAddToCart.setOnClickListener {
            val fragment =
                AddToCartDialogFragment(
                    product
                )
            fragment.show(
                childFragmentManager,
                fragment.tag
            )
        }

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.productImgGrid)//滑動viewHolder卡一半的時候會自動對齊


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun backToList() {
        findNavController().navigateUp()
    }


}