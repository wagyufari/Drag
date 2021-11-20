package com.mayburger.drag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mayburger.drag.data.PersistenceDatabase
import com.mayburger.drag.databinding.ComposerBsdBinding
import com.mayburger.drag.model.Task
import dagger.hilt.android.AndroidEntryPoint
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
                    lifecycleScope.launch {
                        PersistenceDatabase.getDatabase(requireActivity()).taskDao().putTasks(
                            Task(
                                id = 0,
                                title = binding.input.text.toString(),
                                caption = null,
                                image = null,
                                language = "bahasa",
                                state = "in_progress"
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