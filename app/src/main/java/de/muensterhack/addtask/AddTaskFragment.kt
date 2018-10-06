package de.muensterhack.addtask

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.muensterhack.R
import de.muensterhack.api.category.Categories
import de.muensterhack.api.task.Task
import de.muensterhack.api.task.TaskRepository
import de.muensterhack.preferences.Preferences
import kotlinx.android.synthetic.main.fragment_add_task.*
import org.koin.android.ext.android.inject

class AddTaskFragment : Fragment() {

    private val taskRepository: TaskRepository by inject()
    private val preferences: Preferences by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).run {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        val category = Categories.getById(AddTaskFragmentArgs.fromBundle(arguments).categoryId)
        chipCategory.chipIcon = ContextCompat.getDrawable(requireContext(), category!!.activeIcon)
        chipCategory.setText(category.title)

        chipCategory.setOnClickListener { findNavController().navigateUp() }

        editTextWhatToDo.setText(preferences.getWhatToDo())
        editTextWhatToDo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                buttonConfirm.isEnabled = s?.isNotBlank() ?: false
                preferences.setWhatToDo(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        editTextDescription.setText(preferences.getDescription())
        editTextDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                preferences.setDescription(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        buttonConfirm.isEnabled = false
        buttonConfirm.setOnClickListener {
            val whatToDo = editTextWhatToDo.text.toString()
            val description = editTextDescription.text.toString()

            taskRepository.putTask(Task(whatToDo, description, "2010-11-10", 0, 0, category.id, 1)) {
                preferences.setWhatToDo("")
                preferences.setDescription("")
                findNavController().popBackStack(R.id.marketplaceFragment, false)
            }
        }
    }
}
