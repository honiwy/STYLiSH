package student.rachel.stylish.fragment.product.add2cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.rachel.stylish.`object`.Variant
import student.rachel.stylish.databinding.LayoutSizeBinding


class SizeAdapter(val viewModel: Add2cartViewModel) : ListAdapter<Variant, SizeAdapter.ViewHolder>(
    DiffCallbackSize()
) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item.stock > 0) {
            holder.itemView.setOnClickListener {
                viewModel.changeSize(item.size)
                notifyDataSetChanged()
            }
        }
        holder.bind(item, viewModel.size.value ?: "")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: LayoutSizeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(variant: Variant, size: String) {
            binding.variant = variant
            binding.isSelected = (variant.size == size)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSizeBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

}

class DiffCallbackSize :
    DiffUtil.ItemCallback<Variant>() {
    override fun areItemsTheSame(oldItem: Variant, newItem: Variant): Boolean {
        return oldItem.colorCode == newItem.colorCode
    }

    override fun areContentsTheSame(oldItem: Variant, newItem: Variant): Boolean {
        return oldItem == newItem
    }

}