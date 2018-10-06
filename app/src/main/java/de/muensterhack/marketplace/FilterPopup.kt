package de.muensterhack.marketplace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.widget.ListPopupWindow.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import de.muensterhack.R
import de.muensterhack.api.category.Categories
import de.muensterhack.api.category.Categories.*
import de.muensterhack.preferences.Preferences
import kotlinx.android.synthetic.main.popup_filter.view.*

class FilterPopup(
        context: Context,
        preferences: Preferences
) : PopupWindow() {

    private val filteredCategories: MutableList<Int>
    lateinit var filterListener: FilterListener

    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.popup_filter, null, false)
        height = WRAP_CONTENT
        width = WRAP_CONTENT
        isOutsideTouchable = true

        filteredCategories = preferences.getFilteredCategories().toMutableList()

        contentView.run {
            mapOf<View, Categories>(
                    filterCompany to COMPANY,
                    filterShopping to SHOPPING,
                    filterGarden to GARDEN,
                    filterHome to HOME,
                    filterPet to PETS,
                    filterTechnology to TECHNOLOGY,
                    filterMisc to MISC)
                    .forEach { entry ->
                        val button = (entry.key as ConstraintLayout)
                        button.setActive(filteredCategories.contains(entry.value.id), entry.value)

                        button.setOnClickListener {
                            val active = filteredCategories.contains(entry.value.id)

                            if (active) {
                                filteredCategories.remove(entry.value.id)
                            } else {
                                filteredCategories.add(entry.value.id)
                            }

                            button.setActive(!active, entry.value)

                            preferences.setFilteredCategories(filteredCategories)
                            filterListener.filterChanged(filteredCategories)
                        }
                    }
        }
    }

    private fun ConstraintLayout.setActive(active: Boolean, categories: Categories) {
        (getChildAt(0) as ImageView).setImageResource(if (active) categories.activeIcon else categories.inactiveIcon)

        val activeColor = ContextCompat.getColor(context, R.color.black87)
        val inactiveColor = ContextCompat.getColor(context, R.color.black18)
        (getChildAt(1) as TextView).setTextColor(if (active) activeColor else inactiveColor)
    }
}

interface FilterListener {

    fun filterChanged(filteredCategories: List<Int>)
}
