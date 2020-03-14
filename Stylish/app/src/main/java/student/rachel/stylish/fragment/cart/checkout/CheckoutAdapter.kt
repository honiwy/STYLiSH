package student.rachel.stylish.fragment.cart.checkout

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import student.rachel.stylish.NavigationDirections
import student.rachel.stylish.R
import student.rachel.stylish.StylishApplication
import student.rachel.stylish.databinding.LayoutHeaderBinding
import student.rachel.stylish.databinding.LayoutItemCheckoutBinding
import student.rachel.stylish.databinding.LayoutPaymentBinding
import student.rachel.stylish.databinding.LayoutReceiverInfoBinding
import student.rachel.stylish.local.DesireProduct
import tech.cherri.tpdirect.api.TPDCard
import tech.cherri.tpdirect.api.TPDServerType
import tech.cherri.tpdirect.api.TPDSetup
import tech.cherri.tpdirect.callback.TPDCardTokenSuccessCallback
import tech.cherri.tpdirect.callback.TPDTokenFailureCallback
import tech.cherri.tpdirect.model.TPDStatus

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_PRODUCT = 1
private val ITEM_VIEW_TYPE_RECEIVER = 2
private val ITEM_VIEW_TYPE_PAYMENT = 3

class CheckoutAdapter(private val viewModel: CheckoutViewModel) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun addHeaderAndFooter(list: List<DesireProduct>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header(StylishApplication.appContext.getString(R.string.checkout_title)))
                else -> listOf(DataItem.Header(StylishApplication.appContext.getString(R.string.checkout_title))) + list.map {
                    DataItem.Product(
                        it
                    )
                } + listOf(DataItem.Receiver, DataItem.Payment)
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class TextViewHolder(private var binding: LayoutHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title = title
            binding.executePendingBindings()
        }
    }

    class ProductViewHolder(private var binding: LayoutItemCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(desireProduct: DesireProduct) {
            binding.desireProduct = desireProduct
            binding.executePendingBindings()
        }
    }

    class ReceiverViewHolder(private var binding: LayoutReceiverInfoBinding,private val viewModel: CheckoutViewModel) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.viewModel = viewModel
            binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                viewModel.updateDeliverTime(checkedId)
            }
            binding.executePendingBindings()
        }
    }

    class PaymentViewHolder(
        private var binding: LayoutPaymentBinding,private val viewModel: CheckoutViewModel ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.viewModel = viewModel

            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {  binding.creditCard.visibility = View.GONE  }

                override fun onItemSelected(  parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                    when (position) {
                        0 -> binding.creditCard.visibility = View.GONE
                        1 -> binding.creditCard.visibility = View.VISIBLE
                    }
                }
            }

            val context = StylishApplication.appContext
            TPDSetup.initInstance( context, context.getString(R.string.global_app_id).toInt(),context.getString(R.string.global_app_key), TPDServerType.Sandbox  )
            binding.creditCard.setOnFormUpdateListener {
                if (it.cardNumberStatus == TPDStatus.STATUS_ERROR)
                    Toast.makeText(
                        StylishApplication.appContext,
                        "Invalid Card Number", Toast.LENGTH_SHORT ).show()
                else if (it.expirationDateStatus == TPDStatus.STATUS_ERROR)
                    Toast.makeText(
                        StylishApplication.appContext,
                        "Invalid Expiration Date", Toast.LENGTH_SHORT ).show()
                else if (it.ccvStatus == TPDStatus.STATUS_ERROR)
                    Toast.makeText(
                        StylishApplication.appContext,
                        "Invalid CCV", Toast.LENGTH_SHORT ).show()
            }

            val tpdTokenSuccessCallback = TPDCardTokenSuccessCallback { token, tpdCardInfo, cardIdentifier ->
                viewModel.postPurchase(token)
                Toast.makeText( context,"Create Token Success",Toast.LENGTH_SHORT ).show() }

            val tpdTokenFailureCallback = TPDTokenFailureCallback { status, reportMsg ->  Toast.makeText(  context, "Create Token Failed\n$status: $reportMsg", Toast.LENGTH_SHORT ).show() }

            val tpdCard = TPDCard.setup(binding.creditCard).onSuccessCallback(tpdTokenSuccessCallback).onFailureCallback(tpdTokenFailureCallback)


            binding.buttonReadyCheckout.setOnClickListener {
                tpdCard?.getPrime()
            }

            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.Product -> ITEM_VIEW_TYPE_PRODUCT
            is DataItem.Receiver -> ITEM_VIEW_TYPE_RECEIVER
            is DataItem.Payment -> ITEM_VIEW_TYPE_PAYMENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER ->
                TextViewHolder(
                    LayoutHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            ITEM_VIEW_TYPE_PRODUCT ->
                ProductViewHolder(
                    LayoutItemCheckoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            ITEM_VIEW_TYPE_RECEIVER ->
                ReceiverViewHolder(
                    LayoutReceiverInfoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), viewModel
                )
            ITEM_VIEW_TYPE_PAYMENT ->
                PaymentViewHolder(
                    LayoutPaymentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), viewModel
                )
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextViewHolder -> {
                val headerItem = getItem(position) as DataItem.Header
                holder.bind(headerItem.title)
            }
            is ProductViewHolder -> {
                val productItem = getItem(position) as DataItem.Product
                holder.bind(productItem.desireProduct)
            }
            is ReceiverViewHolder -> {
                holder.bind()
            }
            is PaymentViewHolder -> {
                holder.bind()
            }
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
}

sealed class DataItem {
    data class Product(val desireProduct: DesireProduct) : DataItem() {
        override val id = desireProduct.id
    }

    object Receiver : DataItem() {
        override val id = 30L
    }

    object Payment : DataItem() {
        override val id = 0L
    }

    data class Header(val title: String) : DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}