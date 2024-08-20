package com.example.umc_stepper.utils.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        // 첫 번째 아이템은 오른쪽에만 마진을 추가
        when (position) {
            0 -> outRect.right = space
            itemCount - 1 -> outRect.left = space
            // 중간 아이템들은 양쪽에 마진을 추가
            else -> {
                outRect.left = space
                outRect.right = space
            }
        }
    }
}