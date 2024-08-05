package com.example.umc_stepper.ui.community.weekly

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentWeeklySegmentEditBinding
import com.example.umc_stepper.ui.community.CommunityDialog
import com.example.umc_stepper.ui.community.CommunityDialogInterface
import com.example.umc_stepper.ui.community.CommunityRemoveInterface
import com.google.android.material.tabs.TabLayout
import java.util.Collections

class WeeklySegmentEditFragment : BaseFragment<FragmentWeeklySegmentEditBinding>(R.layout.fragment_weekly_segment_edit) ,
    CommunityDialogInterface,
    CommunityRemoveInterface{
    var selectedRemoveItemId = 0
    private lateinit var communityDialog : CommunityDialog
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
    private fun showDialog(title: String, btn1: String, btn2: String) {
        activity?.let {
            communityDialog = CommunityDialog(title, btn1, btn2, this)
            communityDialog.isCancelable = false
            communityDialog.show(it.supportFragmentManager, "CommunityDialog")
        } ?: run {
            // activity가 null인 경우 예외 처리
            Log.e("WeeklySegmentEditFragment", "Activity is null")
        }
    }

    private fun onClickBtn() {
        binding.fragmentWeeklyAddPictureIv.setOnClickListener {
            showDialog("사진 업로드 하기","앨범 선택","취소하기")
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
        uploadImgAdapter = WeeklyEditImageAdapter(this)
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
    override fun onRemove(pos: Int) {
        showDialog("해당 사진을 삭제하시겠습니까?","삭제하기","취소하기")
        selectedRemoveItemId = pos
    }
    override fun OnClickBtn1(btn1 : String) {
        when(btn1){
            "앨범 선택" -> openGallery()
            "삭제하기" -> {
                uploadImgAdapter.removeItem(selectedRemoveItemId)
                Log.d("삭제", "$selectedRemoveItemId, ${uploadImgAdapter.currentList.size}")
            }
        }
    }

}
