package com.mayburger.drag.data

import com.google.gson.Gson
import com.mayburger.drag.model.Task
import com.pixplicity.easyprefs.library.Prefs

object Prefs {
    const val PREF_KEY_DRAGGING_TASK = "pref_key_dragging_task"

    var draggingTask: Task
        get() = Gson().fromJson(Prefs.getString(PREF_KEY_DRAGGING_TASK), Task::class.java)
        set(value) {
            Prefs.putString(PREF_KEY_DRAGGING_TASK, Gson().toJson(value))
        }

    fun resetDrag(){
        draggingTask = Task(
            id = 0,
            title = null,
            image = null,
            state = null
        )
    }
}