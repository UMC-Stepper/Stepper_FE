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
import com.example.umc_stepper.ui.community.CommunityRemoveInterface
import com.example.umc_stepper.utils.GlobalApplication

class WeeklyEditImageAdapter(
    private val removeInterface: CommunityRemoveInterface
) : BaseAdapter<UploadImageCard, ItemUploadPictureBinding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
){
    override val layoutId: Int
        get() = R.layout.item_upload_picture

    override fun bind(binding: ItemUploadPictureBinding, item: UploadImageCard) {
        GlobalApplication.loadCropRoundedSquareImage(binding.root.context,binding.itemUploadPictureIv,item.imgUrl,18
        )
        binding.itemUploadPictureCancelIb.setOnClickListener {
            removeInterface.onRemove(item.id)
        }
    }

    fun removeItem(pos: Int) {
        val currentList = currentList.toMutableList()
            val index = currentList.findLast{it.id == pos}
            currentList.remove(index)
            submitList(currentList)
    }


}


data class UploadImageCard(
    var imgUrl : String,
    val id : Int
)