package de.muensterhack.api.category

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import de.muensterhack.R

enum class Categories(
        val id: Int,
        @DrawableRes val inactiveIcon: Int,
        @DrawableRes val activeIcon: Int,
        @StringRes val title: Int
) {
    COMPANY(0, R.drawable.ic_directions_walk, R.drawable.ic_directions_walk_active, R.string.company),
    SHOPPING(1, R.drawable.ic_shopping_cart, R.drawable.ic_shopping_cart_active, R.string.shopping),
    GARDEN(2, R.drawable.ic_spa, R.drawable.ic_spa_active, R.string.garden),
    HOME(3, R.drawable.ic_kitchen, R.drawable.ic_kitchen_active, R.string.home),
    PETS(4, R.drawable.ic_pets, R.drawable.ic_pets_active, R.string.pets),
    TECHNOLOGY(5, R.drawable.ic_desktop_mac, R.drawable.ic_desktop_mac_active, R.string.technology),
    MISC(6, R.drawable.ic_supervisor_account, R.drawable.ic_supervisor_account_active, R.string.misc);

    companion object {

        fun getById(id: Int) = values().firstOrNull { it.id == id }
    }
}
