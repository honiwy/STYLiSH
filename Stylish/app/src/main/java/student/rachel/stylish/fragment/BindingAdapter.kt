package student.rachel.stylish.fragment

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import student.rachel.stylish.R
import student.rachel.stylish.StylishApplication
import student.rachel.stylish.`object`.Color
import student.rachel.stylish.`object`.Product
import student.rachel.stylish.`object`.Variant
import student.rachel.stylish.local.DesireProduct
import student.rachel.stylish.fragment.cart.checkout.CheckoutAdapter
import student.rachel.stylish.fragment.catalog.subcatagory.ProductAdapter
import student.rachel.stylish.fragment.product.add2cart.ColorChooseAdapter
import student.rachel.stylish.fragment.product.DisplayColorAdapter
import student.rachel.stylish.fragment.product.ProductImgAdapter
import student.rachel.stylish.fragment.product.add2cart.SizeAdapter
import student.rachel.stylish.fragment.home.DataItem
import student.rachel.stylish.fragment.home.HotAdapter

//HomeFragment
@BindingAdapter("listData")
fun bindData(recyclerView: RecyclerView, data: List<DataItem>?) {
    val adapter = recyclerView.adapter as HotAdapter
    adapter.submitList(data)
}

//CatalogFragment
@BindingAdapter("listProduct")
fun bindProduct(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as ProductAdapter
    adapter.submitList(data)
}

//DetailFragment
@BindingAdapter("listDetail")
fun bindDetail(recyclerView: RecyclerView, data: List<String>?) {
    val adapter = recyclerView.adapter as ProductImgAdapter
    adapter.submitList(data)
}

//CheckoutFragment
@BindingAdapter("listCheckout")
fun bindCheckoutProduct(recyclerView: RecyclerView, data: List<DesireProduct>?) {
    val adapter = recyclerView.adapter as CheckoutAdapter
    adapter.addHeaderAndFooter(data)
}


//DetailFragment & AddToCartFragment
@BindingAdapter("listColor")
fun bindColor(recyclerView: RecyclerView, data: List<Color>?) {
    var adapter = recyclerView.adapter
    when(adapter){
        is DisplayColorAdapter -> {
            adapter.submitList(data)
        }
        is ColorChooseAdapter -> {
            adapter.submitList(data)
        }
    }
}

//DetailFragment & AddToCartFragment
@BindingAdapter("colorAddBorder")
fun bindColorBorder(view: View, colorCode: String) {
    view.background = ShapeDrawable(object : Shape() {
        override fun draw(canvas: Canvas, paint: Paint) {
            paint.color = android.graphics.Color
                .parseColor("#${colorCode}")
            paint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, this.width, this.height, paint)
            paint.color = android.graphics.Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3.toFloat()
            canvas.drawRect(0f, 0f, this.width, this.height, paint)
        }
    })
}

//AddToCartFragment
@BindingAdapter("listSize")
fun bindSize(recyclerView: RecyclerView, data: List<Variant>?) {
    val adapter = recyclerView.adapter as SizeAdapter
    adapter.submitList(data)
}


/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}


@BindingAdapter("displayAmount")
fun bindAmount(textView: TextView, amount: Int) {
    textView.text = "x$amount"
}

@BindingAdapter("itemCount")
fun bindProductCount(textView: TextView, count:Int) {
    textView.text = StylishApplication.appContext.getString(R.string.total_amount, count)
}


@BindingAdapter("priceNT")
fun bindPrice(textView: TextView, price: Long) {
    textView.text = "NT$$price"
}



@BindingAdapter("colorCode")
fun bindColor(view : View, color:String) {
    view.setBackgroundColor(android.graphics.Color.parseColor("#$color"))
}

@BindingAdapter("stockRemain")
fun bindStock(textView: TextView, stock:Int) {
    textView.text = StylishApplication.appContext.getString(R.string.stock_remain_num, stock)
}


//  Model: database, server   local透過room拿 remote透過retrofit拿
//  ViewModel: **viewModel 處理邏輯
//  View: activity, fragment, recyclerViewAdapter