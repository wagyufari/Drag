package com.mayburger.drag.ui.bsd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mayburger.drag.data.PersistenceDatabase
import com.mayburger.drag.databinding.StateComposerBsdBinding
import com.mayburger.drag.model.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StateComposerBSD : BottomSheetDialogFragment() {

    lateinit var binding: StateComposerBsdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding = StateComposerBsdBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.input.setOnEditorActionListener { v, actionId, _ ->
            if (binding.input.text.toString().isEmpty().not()) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val database = PersistenceDatabase.getDatabase(requireActivity()).stateDao()
                    CoroutineScope(IO).launch {
                        val states = database.getStatesSuspended()
                        val nextOrder = if (states.isEmpty()) {
                            0
                        } else {
                            states.maxOf { it.order }
                        }
                        CoroutineScope(IO).launch {
                            PersistenceDatabase.getDatabase(requireActivity()).stateDao().putState(
                                State(id = 0, title = binding.input.text.toString(), stateId = binding.input.text.toString().lowercase().replace(" ","_"), order = nextOrder)
                            )
                            dismiss()
                        }
                    }
                }
            }
            false
        }
    }
}