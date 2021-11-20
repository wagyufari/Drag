package com.mayburger.drag.adapter

import android.content.ClipData
import android.content.ClipDescription
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mayburger.drag.base.BaseViewHolder
import com.mayburger.drag.data.Prefs
import com.mayburger.drag.databinding.ItemTaskBinding
import com.mayburger.drag.model.Flyer
import com.mayburger.drag.utils.ViewUtils.hide
import com.mayburger.drag.utils.ViewUtils.show

class TaskAdapter : ListAdapter<Flyer, BaseViewHolder>(TaskDiff) {

    private var mListener: Callback? = null

    object TaskDiff : DiffUtil.ItemCallback<Flyer>() {
        override fun areItemsTheSame(oldItem: Flyer, newItem: Flyer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Flyer, newItem: Flyer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return FlyerViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setListener(listener: Callback) {
        this.mListener = listener
    }

    interface Callback {
        fun onSelectedItem(flyer: Flyer, view: View)
    }

    inner class FlyerViewHolder(private val mBinding: ItemTaskBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            val flyer = getItem(position)
            mBinding.root.setOnLongClickListener {
                val item = ClipData.Item(flyer.id)
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val dragData = ClipData(flyer.id, mimeTypes, item)
                val myShadow = View.DragShadowBuilder(it)
                it.startDrag(dragData, myShadow, null, 0)
                Prefs.draggingFlyer = flyer
                true
            }
        }
    }
}