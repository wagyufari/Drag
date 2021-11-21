package com.mayburger.drag.ui

import androidx.lifecycle.ViewModel
import com.mayburger.drag.data.PersistenceDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(database:PersistenceDatabase):ViewModel() {

    val states = database.stateDao().getStates()

}