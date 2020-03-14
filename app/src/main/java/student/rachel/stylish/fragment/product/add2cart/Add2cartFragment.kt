package student.rachel.stylish.fragment.product.add2cart

import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import student.rachel.stylish.MainActivity
import student.rachel.stylish.R
import student.rachel.stylish.StylishApplication
import student.rachel.stylish.`object`.Product
import student.rachel.stylish.databinding.FragmentAdd2cartBinding
import student.rachel.stylish.fragment.product.ProductViewModelFactory
import kotlin.math.roundToInt

class AddToCartDialogFragment(val product: Product) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentAdd2cartBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add2cart, container, false)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProviders.of(
            this,
            ProductViewModelFactory(product)
        )
            .get(Add2cartViewModel::class.java)

        binding.viewModel = viewModel
        binding.closeButton.setOnClickListener {
            dismiss()
        }
        binding.colorGrid.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                // Add top margin only for the first item to avoid double space between items
                outRect.right = 16.toPixel()
//                if (parent.getChildLayoutPosition(view) == 0) {
//                    outRect.left = 0
//                } else {
//                    outRect.left = 20
//                }
            }
        })

        binding.colorGrid.adapter =
            ColorChooseAdapter(
                viewModel
            )

        binding.sizeGrid.adapter =
            SizeAdapter(
                viewModel
            )
        binding.minusButton.setOnClickListener {
            viewModel.minusOne()
        }
        binding.plusButton.setOnClickListener {
            viewModel.plusOne()
        }
        binding.buttonAddToCart.setOnClickListener {
            if (!viewModel.isOutOfStock()) {
                viewModel.onAddToCart()
                (activity as MainActivity).displayAddToCartSuccess()
            }
        }
        viewModel.productsString.observe(this, Observer {
            Log.i("hi", "The database:${viewModel.productsString.value}")
        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val dialog = getDialog() as BottomSheetDialog
        val bottomSheet =
            dialog.delegate.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let { view ->
            view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        view?.let {
            it.post {
                kotlin.run {
                    val parent = it.parent as View
                    val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                    val behavior = params.behavior
                    val bottomSheetBehavior = behavior as BottomSheetBehavior
                    bottomSheetBehavior.peekHeight = it.measuredHeight
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }
}

fun Number.toPixel(): Int {
    return (this.toInt() * StylishApplication.appContext.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT).roundToInt()
}

