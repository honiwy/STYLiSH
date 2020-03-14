package student.rachel.stylish.fragment.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.rachel.stylish.databinding.LayoutItemImageBinding

class ProductImgAdapter : ListAdapter<String, ProductImgAdapter.ViewHolder>(
    DiffCallbackImg()
) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor(val binding: LayoutItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageLink: String) {
            binding.imageUrl = imageLink
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemImageBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

}
class DiffCallbackImg:
    DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.length == newItem.length
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}