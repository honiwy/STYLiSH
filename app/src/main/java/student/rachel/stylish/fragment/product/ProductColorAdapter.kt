package student.rachel.stylish.fragment.product


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.rachel.stylish.`object`.Color
import student.rachel.stylish.databinding.LayoutColorBinding

class DisplayColorAdapter : ListAdapter<Color, DisplayColorAdapter.ViewHolder>(
    DiffCallbackColor()
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

    class ViewHolder private constructor(val binding: LayoutColorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Color) {
            binding.color = color
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutColorBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

}
class DiffCallbackColor:
    DiffUtil.ItemCallback<Color>() {
    override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
        return oldItem == newItem
    }

}