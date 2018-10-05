package de.muensterhack.marketplace

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.muensterhack.R
import kotlinx.android.synthetic.main.item_marketplace.view.*

class TaskAdapter : RecyclerView.Adapter<TaskViewHolder>() {

    var tasks: List<TaskViewModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_marketplace, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }
}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageViewCategoryIcon = itemView.imageViewCategoryIcon
    private val textViewTitle = itemView.textViewTitle
    private val textViewDistance = itemView.textViewDistance
    private val textViewDescription = itemView.textViewDescription
    private val textViewLongDescription = itemView.textViewLongDescription
    private val textViewDuration = itemView.textViewDuration
    private val textViewDueDate = itemView.textViewDueDate

    fun bind(taskViewModel: TaskViewModel) {
        val context = itemView.context

        taskViewModel.run {
            textViewTitle.text = title
            textViewDescription.text = description
            textViewDuration.text = context.getString(R.string.duration_format, duration)
            textViewDueDate.text = context.getString(R.string.due_date_format, dueDate)
        }
    }
}
