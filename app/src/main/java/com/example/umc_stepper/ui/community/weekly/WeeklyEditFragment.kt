package com.example.umc_stepper.ui.community.weekly

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentWeeklyEditBinding
import com.example.umc_stepper.domain.model.request.post_controller.PostEditDto
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityDialog
import com.example.umc_stepper.ui.community.CommunityDialogInterface
import com.example.umc_stepper.ui.community.CommunityRemoveInterface
import com.example.umc_stepper.ui.community.CommunityViewModel
import com.example.umc_stepper.utils.enums.DialogType
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.Collections

class WeeklyEditFragment : BaseFragment<FragmentWeeklyEditBinding>(R.layout.fragment_weekly_edit),
    CommunityDialogInterface,
    CommunityRemoveInterface {
    private lateinit var mainActivity: MainActivity
    private lateinit var postEditDto: PostEditDto

    private var selectedRemoveItemId = 0
    private lateinit var galleryForResult: ActivityResultLauncher<Intent>
    private lateinit var uploadImgAdapter: WeeklyEditImageAdapter
    private lateinit var communityDialog: CommunityDialog
//    private val mainViewModel: MainViewModel by activityViewModels()
    private val imageList: MutableList<MultipartBody.Part> = mutableListOf()
    private val communityViewModel: CommunityViewModel by activityViewModels()

    val imgList: MutableList<UploadImageCard> = mutableListOf(
    )
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun setLayout() {
        updateMainToolbar()
        initRecyclerView()
        onClickBtn()
        initTitle()
        initActivityResultLauncher()
        // weeklyMissionTitle 수신
        Log.d("로그", "weeklyMissionTitle : ${arguments?.getString("weeklyMissionTitle")}")
    }
    private fun initTitle(){
        binding.fragmentWeeklyTitleTv.text = arguments?.getString("weeklyMissionTitle")
    }

    private fun showDialog(title: String, btn1: String, btn2: String) {
        communityDialog = CommunityDialog(title, btn1, btn2, this)
        communityDialog.isCancelable = false
        communityDialog.show(activity?.supportFragmentManager!!, "CommunityDialog")
    }

    private fun onClickBtn() {
        binding.fragmentWeeklyAddPictureIv.setOnClickListener {
            showDialog("사진 업로드 하기", "앨범 선택", "취소하기")
        }
        binding.fragmentWeeklyEditSuccessBt.setOnClickListener {
            //저장
//            updateBadge(3)  // 첫 게시물 작성 완료 뱃지
            postEditDto = PostEditDto(
                imageUrl = "string",
                title = binding.fragmentWeeklySubtitleEt.text.toString(),
                body = binding.fragmentWeeklyDescriptionTv.text.toString(),
                bodyPart = null,
                subCategory = null,
                weeklyMissionId = 1
            )
            val gson = Gson()
            val userJson = gson.toJson(postEditDto, PostEditDto::class.java)
            val userRequest = userJson.toRequestBody("application/json".toMediaTypeOrNull())
            communityViewModel.postEditResponse(
                userRequest, imageList
            )
            findNavController().navigateUp()
        }
        binding.fragmentWeeklyEditCancelBt.setOnClickListener {
            findNavController().navigateUp()
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
                            handleSelectedImage(it)
                            imgList.add(i, UploadImageCard(it.toString(), i))
                        }
                    }
                    uploadImgAdapter.submitList(imgList)
                }
            }
        }
    }

//    private fun updateBadge(i:Int) {
//        // 첫 번째 badgeList 항목의 hasBadge 값이 false일 때만 true로 변경하고 토스트 메시지 띄우기
//        if (!mainViewModel.badgeList[i].hasBadge) {
//            // 첫 번째 badgeList 항목의 hasBadge 값을 true로 설정
//            mainViewModel.badgeList[i].hasBadge = true
//
//            // "새로운 뱃지 획득! My Badge를 확인해주세요"라는 토스트 메시지 띄우기
//            Toast.makeText(requireContext(), "새로운 뱃지 획득! My Badge를 확인해주세요", Toast.LENGTH_SHORT).show()
//        }
//    }


    private fun initRecyclerView() {
        uploadImgAdapter = WeeklyEditImageAdapter( this)
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

    override fun OnClickBtn1(btn1: String, dialogType: DialogType?) {
        when (btn1) {
            "앨범 선택" -> openGallery()
            "삭제하기" -> {
                binding.fragmentWeeklyImageUploadRv.adapter?.notifyItemRemoved(selectedRemoveItemId)
                Log.d("삭제", "$selectedRemoveItemId ${uploadImgAdapter.itemCount}")
            }
        }
    }

    override fun onRemove(pos: Int) {
        showDialog("해당 사진을 삭제하시겠습니까?", "삭제하기", "취소하기")
        selectedRemoveItemId = pos
    }

    private fun resizeBitmapTo16x16(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 100, 100, true)
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("Weekly Mission")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_back)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_today)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_stepper)
    }

    private fun handleSelectedImage(uri: Uri) {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        inputStream?.use { stream ->
            val bitmap = BitmapFactory.decodeStream(stream)
            val resizedBitmap = resizeBitmapTo16x16(bitmap) // 16x16으로 크기 조정
            val compressedFile = compressBitmap(resizedBitmap, 500)

            val requestFile = compressedFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData(
                "image",
                compressedFile.name,
                requestFile
            )
            imageList.add(imagePart)
        }
    }

    private var fileTail = 0
    private fun compressBitmap(bitmap: Bitmap, maxSizeKB: Int): File {
        var quality = 100
        val outputStream = ByteArrayOutputStream()

        do {
            outputStream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            quality -= 5
        } while (outputStream.size() / 1024 > maxSizeKB && quality > 0)

        val file = File(requireActivity().cacheDir, "compressed_image$fileTail.jpg")
        fileTail++
        FileOutputStream(file).use { fos ->
            fos.write(outputStream.toByteArray())
        }

        return file
    }
}