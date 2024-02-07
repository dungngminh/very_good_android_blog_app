package me.dungngminh.verygoodblogapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(
    val left: Int = 0,
    val right: Int = 0,
    val top: Int = 0,
    val bottom: Int = 0,
    private val shouldAvoidSpacingOfLastItem: Boolean = true,
) :
    RecyclerView.ItemDecoration() {
    constructor(space: Int) : this(left = space, right = space, top = space, bottom = space)

    constructor(spaceHorizontal: Int, spaceVertical: Int) : this(
        left = spaceHorizontal,
        right = spaceHorizontal,
        top = spaceVertical,
        bottom = spaceVertical,
    )

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        if (shouldAvoidSpacingOfLastItem && parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.setEmpty()
            return
        }
        outRect.left = left
        outRect.right = right
        outRect.bottom = bottom
        outRect.top = top
    }
}
