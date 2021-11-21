package com.mayburger.drag.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.mayburger.drag.adapter.TabPagerAdapter
import com.mayburger.drag.data.PersistenceDatabase
import com.mayburger.drag.data.Prefs
import com.mayburger.drag.databinding.ActivityTaskBinding
import com.mayburger.drag.dpToPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityTaskBinding

    val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel.states.observe(this) {
            val fragments = ArrayList<Fragment>().apply {
                it.forEach {
                    add(TaskFragment.newInstance(title = it.title, state = it.stateId))
                }
                add(TaskFragment.newInstance("New list", Integer.MIN_VALUE.toString()))
            }
            binding.pager.adapter = TabPagerAdapter(
                this,
                fragments
            )
        }

        val pageTranslationX = dpToPx(64)
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
        }
        binding.pager.offscreenPageLimit = 1
        binding.pager.setPageTransformer(pageTransformer)

        var isCooldown = false
        val timer = object : CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                isCooldown = false
            }
        }
        binding.endBorder.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_LOCATION -> {
                    if (binding.pager.currentItem != 2 && !isCooldown) {
                        binding.pager.currentItem = binding.pager.currentItem + 1
                        isCooldown = true
                        timer.start()
                    }
                }
            }
            true
        }
        binding.startBorder.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_LOCATION -> {
                    if (binding.pager.currentItem != 0 && !isCooldown) {
                        binding.pager.currentItem = binding.pager.currentItem - 1
                        isCooldown = true
                        timer.start()
                    }
                }
            }
            true
        }
        binding.pager.setOnDragListener { v, event ->
            println(event.action)
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    binding.trash.show()
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    binding.trash.hide()
                }
            }
            true
        }

        binding.trash.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    lifecycleScope.launch {
                        PersistenceDatabase.getDatabase(this@TaskActivity).taskDao().deleteTask(
                            Prefs.draggingTask
                        )
                        Prefs.resetDrag()
                    }
                }
            }
            true
        }

        binding.add.setOnClickListener {
            CoroutineScope(IO).launch {
                if (PersistenceDatabase.getDatabase(this@TaskActivity).stateDao().getStatesSuspended().isEmpty()) {
                    Toast.makeText(
                        this@TaskActivity,
                        "Create a new list first!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    TaskComposerBSD().show(supportFragmentManager, "")
                }
            }
        }
    }

}