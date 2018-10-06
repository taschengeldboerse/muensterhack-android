package de.muensterhack.choosecategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import de.muensterhack.R
import de.muensterhack.api.category.Categories
import kotlinx.android.synthetic.main.fragment_choose_category.*

class ChooseCategoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).run {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        Categories.values().forEach {
            val chip = LayoutInflater.from(requireContext()).inflate(R.layout.item_chip, null, false) as Chip
            chip.setText(it.title)
            chip.chipIcon = ContextCompat.getDrawable(requireContext(), it.activeIcon)
            val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val margin = resources.getDimension(R.dimen.chip_margin).toInt()
            layoutParams.topMargin = margin
            layoutParams.bottomMargin = margin
            chip.layoutParams = layoutParams

            chip.setOnClickListener { _ ->
                findNavController().navigate(ChooseCategoryFragmentDirections.actionAddTask().setCategoryId(it.id))
            }

            linearLayoutCategories.addView(chip)
        }
    }
}
