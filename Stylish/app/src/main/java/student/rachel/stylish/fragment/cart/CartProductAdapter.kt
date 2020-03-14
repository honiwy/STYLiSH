package student.rachel.stylish.fragment.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.rachel.stylish.databinding.LayoutItemCartBinding
import student.rachel.stylish.local.DesireProduct

class CartProductAdapter(val viewModel: CartViewModel) :
    ListAdapter<DesireProduct, CartProductAdapter.ViewHolder>(
        DiffCallbackDesireProduct()
    ) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.minusButton.setOnClickListener {
            viewModel.onDecreaseAmount(item)
            notifyDataSetChanged()
        }
        holder.binding.plusButton.setOnClickListener {
            viewModel.onIncreaseAmount(item)
            notifyDataSetChanged()
        }
        holder.binding.buttonRemove.setOnClickListener {
            viewModel.onDelete(item.id)
            notifyDataSetChanged()
        }
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: LayoutItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(desireProduct: DesireProduct) {
            binding.desireProduct = desireProduct
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemCartBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

}

class DiffCallbackDesireProduct :
    DiffUtil.ItemCallback<DesireProduct>() {
    override fun areItemsTheSame(oldItem: DesireProduct, newItem: DesireProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DesireProduct, newItem: DesireProduct): Boolean {
        return oldItem == newItem
    }

}