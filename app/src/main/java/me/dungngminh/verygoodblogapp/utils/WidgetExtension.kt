package me.dungngminh.verygoodblogapp.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.models.Category


fun BottomNavigationView.hide(duration: Long = 250) {
    this.clearAnimation()
    this.animate().translationY(this.height.toFloat()).setDuration(duration)
}

fun BottomNavigationView.show(duration: Long = 250) {
    this.clearAnimation()
    this.animate().translationY(0f).setDuration(duration)
}

fun ChipGroup.addChip(
    category: Category,
    fragment: Fragment,
    onCategoryPress: (Category) -> Unit,
) {
    val chip = fragment.layoutInflater.inflate(R.layout.item_category, this, false) as Chip
    chip.apply {
        id = View.generateViewId()
        text = fragment.getString(category.getLocalizedName())
        setOnClickListener { onCategoryPress(category) }
    }
    this.addView(chip)
    if (category == Category.ALL) this.check(chip.id)
}