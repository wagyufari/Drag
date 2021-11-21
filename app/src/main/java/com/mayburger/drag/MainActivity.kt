package com.mayburger.drag

import android.os.Bundle
import android.os.CountDownTimer
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.mayburger.drag.adapter.TabPagerAdapter
import com.mayburger.drag.data.PersistenceDatabase
import com.mayburger.drag.data.Prefs
import com.mayburger.drag.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.pager.adapter = TabPagerAdapter(
            this,
            arrayListOf(
                TaskFragment.newInstance("In Progress", "in_progress"),
                TaskFragment.newInstance("Completed", "completed"),
                TaskFragment.newInstance("Reviewed", "reviewed")
            )
        )

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
                        PersistenceDatabase.getDatabase(this@MainActivity).taskDao().deleteTask(
                            Prefs.draggingTask)
                        Prefs.resetDrag()
                    }
                }
            }
            true
        }

        binding.add.setOnClickListener {
            ComposerBSD().show(supportFragmentManager, "")
        }
    }

}