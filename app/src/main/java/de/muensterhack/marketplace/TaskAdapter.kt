package de.muensterhack.marketplace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    private val buttonConfirm = itemView.buttonConfirm
    private val textViewDuration = itemView.textViewDuration
    private val textViewDueDate = itemView.textViewDueDate

    fun bind(taskViewModel: TaskViewModel) {

        buttonConfirm.setOnClickListener { }

        val context = itemView.context

        taskViewModel.run {
            // TODO category
            imageViewCategoryIcon.setImageResource(R.drawable.ic_account_circle)
            textViewTitle.text = "Kategorie"
            textViewDescription.text = title

            // TODO distance
            textViewDistance.text = context.getString(R.string.distance_format, "500")

            textViewLongDescription.text = description
            textViewDuration.text = context.getString(R.string.duration_format, duration)
            textViewDueDate.text = context.getString(R.string.due_date_format, dueDate)
        }
    }
}
