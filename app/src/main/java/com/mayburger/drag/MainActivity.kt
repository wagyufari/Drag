package com.mayburger.drag

import android.os.Bundle
import android.os.CountDownTimer
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.mayburger.drag.adapter.TabPagerAdapter
import com.mayburger.drag.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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
                TaskFragment("In Progress", "in_progress"),
                TaskFragment("Completed", "completed"),
                TaskFragment("Reviewed", "reviewed")
            )
        )

        val pageTranslationX = dpToPx(64)
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
        }
        binding.pager.offscreenPageLimit = 1
        binding.pager.setPageTransformer(pageTransformer)

        var isCooldown = false
        val timer = object:CountDownTimer(500,500){
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                isCooldown = false
            }
        }
        binding.endBorder.setOnDragListener { v, event ->
            println(event.action)
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
    }

}


//lifecycleScope.launchWhenCreated {
//    PersistenceDatabase.getDatabase(this@MainActivity).flyerDao().putFlyers(
//        arrayListOf(
//            Flyer(
//                id = "1",
//                title = null,
//                caption = null,
//                image = "https://firebasestorage.googleapis.com/v0/b/dzikir-qu.appspot.com/o/flyer%2FMerahasiakan%20Amal%20Kebaikan.png?alt=media&token=d680c076-5528-4838-b003-a1b05eabbdea",
//                language = "bahasa",
//                state = "in_progress"
//            ),
//            Flyer(
//                id = "2",
//                title = null,
//                caption = null,
//                image = "https://firebasestorage.googleapis.com/v0/b/dzikir-qu.appspot.com/o/flyer%2FUlama%20Pewaris%20Nabi.png?alt=media&token=df741108-f11e-4f1a-a4b1-ac7a2e295ced",
//                language = "bahasa",
//                state = "in_progress"
//            ),
//            Flyer(
//                id = "3",
//                title = null,
//                caption = null,
//                image = "https://firebasestorage.googleapis.com/v0/b/dzikir-qu.appspot.com/o/flyer%2FSebab%20istiqomah%20dalam%20menuntut%20ilmu.png?alt=media&token=a87f9ab3-5d21-4a0b-8740-637bba5c42c9",
//                language = "bahasa",
//                state = "in_progress"
//            ),
//            Flyer(
//                id = "4",
//                title = null,
//                caption = null,
//                image = "https://firebasestorage.googleapis.com/v0/b/dzikir-qu.appspot.com/o/flyer%2FIMG_20211117_231905.jpg.png?alt=media&token=0f80dd7e-bb3d-48c0-b45b-62c3a73999ad",
//                language = "bahasa",
//                state = "in_progress"
//            ),
//        )
//    )
//}
