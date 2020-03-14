package student.rachel.stylish.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import student.rachel.stylish.`object`.Product
import student.rachel.stylish.databinding.*

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_SINGLE = 1
private val ITEM_VIEW_TYPE_MULTIPLE = 2


class HotAdapter( val onClickListener: OnClickListener): ListAdapter<DataItem,RecyclerView.ViewHolder>(DiffCallback) {
    class SingleImgViewHolder(private var binding: LayoutItemHot1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.HeaderMainPage -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SingleImgItem -> ITEM_VIEW_TYPE_SINGLE
            is DataItem.MultipleImgItem -> ITEM_VIEW_TYPE_MULTIPLE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder( LayoutHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            ITEM_VIEW_TYPE_SINGLE -> SingleImgViewHolder( LayoutItemHot1Binding.inflate(LayoutInflater.from(parent.context),parent,false))
            ITEM_VIEW_TYPE_MULTIPLE -> MultipleImgViewHolder( LayoutItemHot2Binding.inflate(LayoutInflater.from(parent.context),parent,false))
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }
    class TextViewHolder (private var binding: LayoutHeaderBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title = title
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> {
                holder.bind((getItem(position) as DataItem.HeaderMainPage).title)
            }

            is SingleImgViewHolder -> {
                val productItem = getItem(position) as DataItem.SingleImgItem
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(productItem )
                }
                holder.bind(productItem.product)
            }
            is MultipleImgViewHolder -> {
                val productItem = getItem(position) as DataItem.MultipleImgItem
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(productItem )
                }
                holder.bind(productItem.product)
            }
        }
    }

    class MultipleImgViewHolder(private var binding: LayoutItemHot2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (dataItem:DataItem) -> Unit) {
        fun onClick(dataItem:DataItem) = clickListener(dataItem)
    }
}

sealed class DataItem {
    data class SingleImgItem(val product: Product): DataItem(){
        override val id = product.id
    }
    data class MultipleImgItem(val product: Product): DataItem(){
        override val id = product.id
    }
    data class HeaderMainPage(val title : String): DataItem(){
        override val id  = Long.MIN_VALUE
    }
    abstract val id:Long
}