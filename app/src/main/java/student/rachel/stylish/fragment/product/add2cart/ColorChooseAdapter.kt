package student.rachel.stylish.fragment.product.add2cart

import student.rachel.stylish.`object`.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.rachel.stylish.databinding.*
import student.rachel.stylish.fragment.product.DiffCallbackColor

class ColorChooseAdapter(val viewModel: Add2cartViewModel) :
    ListAdapter<Color, ColorChooseAdapter.ViewHolder>(
        DiffCallbackColor()
    ) {
    //如果input有val viewModel: ProductViewModel,  則viewModel是一種依賴注入
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorItem = getItem(position)
        holder.itemView.setOnClickListener {
            viewModel.changeColor(colorItem)
            notifyDataSetChanged()
        }
        holder.bind(colorItem, viewModel.color.value?.code ?: "")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }


    class ViewHolder private constructor(val binding: LayoutColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Color, colorCode: String) {
            binding.color = item
            binding.isSelected = (item.code == colorCode)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutColorBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }
}
