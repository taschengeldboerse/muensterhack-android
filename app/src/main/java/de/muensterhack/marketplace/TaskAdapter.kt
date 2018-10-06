package de.muensterhack.marketplace

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.muensterhack.R
import kotlinx.android.synthetic.main.item_marketplace.view.*

class TaskAdapter(private val taskConfirmListener: TaskConfirmListener) : RecyclerView.Adapter<TaskViewHolder>() {

    var tasks: List<TaskViewModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_marketplace, parent, false)
        return TaskViewHolder(view, taskConfirmListener)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemId(position: Int) = (tasks[position].title + tasks[position].category).hashCode().toLong()
}

class TaskViewHolder(
        itemView: View,
        private val taskConfirmListener: TaskConfirmListener
) : RecyclerView.ViewHolder(itemView) {

    private val imageViewCategoryIcon = itemView.imageViewCategoryIcon
    private val imageViewNavigation = itemView.imageViewNavigation
    private val textViewTitle = itemView.textViewTitle
    private val textViewDistance = itemView.textViewDistance
    private val textViewDescription = itemView.textViewDescription
    private val textViewLongDescription = itemView.textViewLongDescription
    private val buttonConfirm = itemView.buttonConfirm
    private val textViewDuration = itemView.textViewDuration
    private val textViewDueDate = itemView.textViewDueDate

    fun bind(taskViewModel: TaskViewModel) {

        val context = itemView.context

        buttonConfirm.setOnClickListener {
            taskConfirmListener.taskConfirmed(taskViewModel.id)
            buttonConfirm.isEnabled = false
            buttonConfirm.text = context.getString(R.string.task_confirmed)
        }

        taskViewModel.run {
            category?.let {
                imageViewCategoryIcon.setImageResource(it.activeIcon)
                textViewTitle.setText(it.title)
            }

            textViewDescription.text = title

            if (distance == null) {
                textViewDistance.visibility = GONE
                imageViewNavigation.visibility = GONE
            } else {
                if (distance >= 1000) {
                    textViewDistance.text = context.getString(R.string.distance_format_kilometers, distance / 1000)
                } else {
                    textViewDistance.text = context.getString(R.string.distance_format_meters, distance)
                }
                textViewDistance.visibility = VISIBLE
                imageViewNavigation.visibility = VISIBLE
            }

            textViewLongDescription.text = description
            textViewDuration.text = context.getString(R.string.duration_format, duration)
            textViewDueDate.text = context.getString(R.string.due_date_format, dueDate)
        }
    }
}

interface TaskConfirmListener {

    fun taskConfirmed(id: Int?)
}
