package com.mayburger.drag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mayburger.drag.data.PersistenceDatabase
import com.mayburger.drag.databinding.ComposerBsdBinding
import com.mayburger.drag.model.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ComposerBSD: BottomSheetDialogFragment() {

    lateinit var binding: ComposerBsdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding = ComposerBsdBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.input.setOnEditorActionListener { v, actionId, _ ->
            if (binding.input.text.toString().isEmpty().not()) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val database = PersistenceDatabase.getDatabase(requireActivity()).taskDao()
                    CoroutineScope(IO).launch {
                        val tasks = database.getTasksSuspended("in_progress")
                        val nextOrder = if (tasks.isEmpty()){0}else{tasks.maxOf { it.order?:0 }}
                        PersistenceDatabase.getDatabase(requireActivity()).taskDao().putTasksSuspended(
                            Task(
                                id = 0,
                                title = binding.input.text.toString(),
                                image = null,
                                state = "in_progress",
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