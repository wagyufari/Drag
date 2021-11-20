package com.mayburger.drag.data

import com.google.gson.Gson
import com.mayburger.drag.model.Flyer
import com.pixplicity.easyprefs.library.Prefs

object Prefs {
    const val PREF_KEY_DRAGGING_FLYER = "pref_key_isya_offset"

    var draggingFlyer: Flyer
        get() = Gson().fromJson(Prefs.getString(PREF_KEY_DRAGGING_FLYER), Flyer::class.java)
        set(value) {
            Prefs.putString(PREF_KEY_DRAGGING_FLYER, Gson().toJson(value))
        }
}