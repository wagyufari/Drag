package com.mayburger.drag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mayburger.drag.adapter.TaskAdapter
import com.mayburger.drag.data.PersistenceDatabase
import com.mayburger.drag.databinding.FragmentTaskBinding
import com.mayburger.drag.model.Flyer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_task.*
import javax.inject.Inject

import android.view.DragEvent
import androidx.lifecycle.lifecycleScope
import com.mayburger.drag.data.Prefs


@AndroidEntryPoint
class TaskFragment(val title:String, val state:String): Fragment() {

    lateinit var binding: FragmentTaskBinding
    @Inject
    lateinit var database: PersistenceDatabase
    @Inject
    lateinit var flyerAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title
        binding.recycler.apply{
            adapter = flyerAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
        database.flyerDao().getFlyers("bahasa", state).observe(viewLifecycleOwner){
            flyerAdapter.submitList(it)
        }
        flyerAdapter.setListener(object:TaskAdapter.Callback{
            override fun onSelectedItem(flyer: Flyer, v: View) {
            }
        })

        binding.dropArea.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    lifecycleScope.launchWhenCreated {
                        database.flyerDao().updateFlyer(Prefs.draggingFlyer.apply { state = this@TaskFragment.state })
                    }
                    binding.recycler.setBackgroundColor(0)
                }
                DragEvent.ACTION_DRAG_ENTERED->{
                    binding.recycler.setBackgroundColor(resources.getColor(R.color.neutral_300,null))
                }
                DragEvent.ACTION_DRAG_EXITED->{
                    binding.recycler.setBackgroundColor(0)
                }
            }
            true
        }
    }
}