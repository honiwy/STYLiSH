package student.rachel.stylish.fragment.catalog.subcatagory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.rachel.stylish.`object`.Product
import student.rachel.stylish.databinding.LayoutItemDisplayBinding


class ProductAdapter( val onClickListener: OnClickListener) : ListAdapter<Product, ProductAdapter.ViewHolder>(
    DiffCallback()
) {
    //如果input有val viewModel: ProductViewModel,  則viewModel是一種依賴注入
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: LayoutItemDisplayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.product = item
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemDisplayBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
    class OnClickListener(val clickListener: (product:Product) -> Unit) {
        fun onClick(product:Product) = clickListener(product)
    }

}
class DiffCallback:
    DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}


