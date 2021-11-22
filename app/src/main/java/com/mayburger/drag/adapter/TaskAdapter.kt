package com.mayburger.drag.adapter

import android.content.ClipData
import android.content.ClipDescription
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mayburger.drag.R
import com.mayburger.drag.base.BaseViewHolder
import com.mayburger.drag.data.Prefs
import com.mayburger.drag.databinding.ItemTaskBinding
import com.mayburger.drag.databinding.ItemTaskGhostBinding
import com.mayburger.drag.model.Task

class TaskAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private var mListener: Callback? = null

//    object TaskDiff : DiffUtil.ItemCallback<Task>() {
//        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
//            return oldItem == newItem
//        }
//    }

    val data = ArrayList<Task>()

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItems(data: ArrayList<Task>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setItems(data: ArrayList<Task>, position: Int) {
        this.data.clear()
        this.data.addAll(data.filter {
            it.id != Prefs.draggingTask.id
        })
        this.data.add(position, Task(
            id = -1,
            title = null,
            image = null,
            state = null
        ))
        notifyDataSetChanged()
    }

    fun setItemsLast(data: ArrayList<Task>) {
        this.data.clear()
        this.data.addAll(data.filter {
            it.id != Prefs.draggingTask.id
        })
        this.data.add(Task(
            id = -1,
            title = null,
            image = null,
            state = null
        ))
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return when {
            data.isEmpty() -> {
                -1
            }
            data[position].id == -1 -> {
                -1
            }
            else -> {
                0
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if(viewType == -1){
            TaskGhostViewHolder(
                ItemTaskGhostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else{
            TaskViewHolder(
                ItemTaskBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun setListener(listener: Callback) {
        this.mListener = listener
    }

    interface Callback {
        fun onSelectedItem(task: Task, view: View)
        fun onDragEntered(position: Int)
        fun onDropped()
    }

    inner class TaskViewHolder(private val mBinding: ItemTaskBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            val task = data[position]
            mBinding.title.text = task.title
            mBinding.type.text = task.type
            mBinding.progressMetric.text = "${task.currentProgress} / ${task.target_progress} (%)"
            mBinding.progressPercentage.text = "${((task.currentProgress * 100) / task.target_progress).toInt()}%"
            mBinding.progress.max = task.target_progress.toInt()
            mBinding.progress.progress = task.currentProgress.toInt()
            mBinding.root.setOnLongClickListener {
                val item = ClipData.Item(task.id.toString())
                val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
                val dragData = ClipData(task.id.toString(), mimeTypes, item)
                val myShadow = View.DragShadowBuilder(it)
                it.startDrag(dragData, myShadow, null, 0)
                Prefs.draggingTask = task
                true
            }
            Glide.with(mBinding.profile).load(R.drawable.cat)
                .apply(RequestOptions().circleCrop())
                .into(mBinding.profile)
            mBinding.root.setOnDragListener { v, event ->
                when(event.action){
                    DragEvent.ACTION_DRAG_ENTERED->{
                        mListener?.onDragEntered(position)
                    }
                    DragEvent.ACTION_DROP->{
                        mListener?.onDropped()
                    }
                }
                true
            }
        }
    }


    inner class TaskGhostViewHolder(private val mBinding: ItemTaskGhostBinding) :
        BaseViewHolder(mBinding.root) {

        override fun onBind(position: Int) {
            mBinding.title.text = Prefs.draggingTask.title
        }
    }

}