package com.mayburger.drag.ui.bsd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mayburger.drag.data.PersistenceDatabase
import com.mayburger.drag.databinding.TaskComposerBsdBinding
import com.mayburger.drag.model.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TaskComposerBSD: BottomSheetDialogFragment() {

    lateinit var binding: TaskComposerBsdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding = TaskComposerBsdBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.input.setOnEditorActionListener { v, actionId, _ ->
            if (binding.input.text.toString().isEmpty().not()) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val database = PersistenceDatabase.getDatabase(requireActivity())
                    CoroutineScope(IO).launch {
                        val states = database.stateDao().getStatesSuspended()
                        val stateId = states.firstOrNull()?.stateId?:""
                        val tasks = database.taskDao().getTasksSuspended(stateId)
                        val nextOrder = if (tasks.isEmpty()){0}else{tasks.maxOf { it.order?:0 }}
                        database.taskDao().putTasksSuspended(
                            Task(
                                id = 0,
                                title = binding.input.text.toString(),
                                image = null,
                                state = stateId,
                                order = nextOrder
                            )
                        )
                        dismiss()
                    }
                }
            }
            false
        }
    }
}