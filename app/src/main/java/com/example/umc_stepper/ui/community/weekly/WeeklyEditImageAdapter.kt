package com.example.umc_stepper.ui.community.weekly

import android.content.Intent
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentWeeklyEditBinding
import com.example.umc_stepper.databinding.ItemImageBinding
import com.example.umc_stepper.databinding.ItemUploadPictureBinding
import com.example.umc_stepper.utils.GlobalApplication

class WeeklyEditImageAdapter(
    private var items: MutableList<UploadImageCard>
) : BaseAdapter<UploadImageCard, ItemUploadPictureBinding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        contentsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }
    )
){
    override val layoutId: Int
        get() = R.layout.item_upload_picture

    override fun bind(binding: ItemUploadPictureBinding, item: UploadImageCard) {
        GlobalApplication.loadCropRoundedSquareImage(binding.root.context,binding.itemUploadPictureIv,item.imgUrl,18
        )
        binding.itemUploadPictureCancelIb.setOnClickListener {
            removeItem(item.id)  // 해당 아이템 삭제
        }
    }

    private fun removeItem(pos: Int) {
        if (pos >= 0 && pos < items.size) {
            items.removeAt(pos)
            notifyItemRemoved(pos)
        }
    }


}


data class UploadImageCard(
    var imgUrl : String,
    val id : Int
)