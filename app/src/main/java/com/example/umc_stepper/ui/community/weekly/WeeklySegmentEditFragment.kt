package com.example.umc_stepper.ui.community.weekly

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentWeeklySegmentEditBinding
import com.google.android.material.tabs.TabLayout
import java.util.Collections

class WeeklySegmentEditFragment : BaseFragment<FragmentWeeklySegmentEditBinding>(R.layout.fragment_weekly_segment_edit) {

    private lateinit var galleryForResult: ActivityResultLauncher<Intent>
    private lateinit var uploadImgAdapter: WeeklyEditImageAdapter
    private val imgList: MutableList<UploadImageCard> = mutableListOf()
    private var selectedTab = ""

    override fun setLayout() {
        initTabLayout()
        initRecyclerView()
        onClickBtn()
        initActivityResultLauncher()
    }

    private fun onClickBtn() {
        binding.fragmentWeeklyAddPictureIv.setOnClickListener {
            openGallery()
        }
        binding.fragmentWeeklySuccessEditBt.setOnClickListener {
            //저장
        }
        binding.fragmentWeeklySegmentEditCancelBt.setOnClickListener {
            //취소
        }
    }

    //갤러리 열기
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        galleryForResult.launch(Intent.createChooser(galleryIntent, "Select Pictures"))
    }

    //갤러리 런처 초기화
    private fun initActivityResultLauncher() {
        galleryForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val clipData = result.data?.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val imageUri = clipData.getItemAt(i).uri
                        imageUri?.let {
                            imgList.add(i, UploadImageCard(it.toString(), i))
                        }
                    }
                    uploadImgAdapter.submitList(imgList)
                }
            }
        }
    }

    private fun initTabLayout(){
        with(binding.fragmentWeeklySegmentItemTb) {
            addTab(newTab().setText("QnA"))
            addTab(newTab().setText("건강정보"))
            addTab(newTab().setText("자유토크"))
            addTab(newTab().setText("동기부여"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab) {
                    selectedTab = tab.text.toString()
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    // 필요 시 구현
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    // 필요 시 구현
                }
            })
        }
    }

    private fun initRecyclerView() {
        uploadImgAdapter = WeeklyEditImageAdapter(mutableListOf())
        binding.fragmentWeeklyImageUploadRv.adapter = uploadImgAdapter
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.bindingAdapterPosition
                val toPos = target.bindingAdapterPosition
                if (fromPos == RecyclerView.NO_POSITION || toPos == RecyclerView.NO_POSITION) {
                    return false
                }
                Collections.swap(imgList, fromPos, toPos)
                recyclerView.adapter?.notifyItemMoved(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // No operation on swipe
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.fragmentWeeklyImageUploadRv)
    }
}
