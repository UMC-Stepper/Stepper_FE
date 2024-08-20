package com.example.umc_stepper.ui.stepper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentEvaluationExerciseBinding
import com.example.umc_stepper.domain.model.request.rate_diary_controller.RateDiaryDto
import com.example.umc_stepper.token.TokenManager
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.today.evaluation_log.EvaluationExerciseTodayFragmentDirections
import com.example.umc_stepper.utils.GlobalApplication
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class EvaluationExerciseFragment :
    BaseFragment<FragmentEvaluationExerciseBinding>(R.layout.fragment_evaluation_exercise) {    // 평가 일지 작성

    @Inject lateinit var tokenManager: TokenManager

    private lateinit var imgList: List<ImageView>
    private lateinit var tvList: List<TextView>
    private lateinit var triangleList: List<ImageView>
    private lateinit var blurList: List<Int>
    private lateinit var notBlurList: List<Int>
    private lateinit var stateTitleList: List<String>
    private lateinit var descriptionList: List<String>
    private lateinit var profileImage: MultipartBody.Part
    private lateinit var sharedPreferences: SharedPreferences

    private val stepperViewModel: StepperViewModel by activityViewModels()
    private var selectTextDescription = 0
    private var exerciseId by Delegates.notNull<Int>()
    private var score = 0


    override fun onPause() {
        super.onPause()
        saveSharedPreferences()
    }

    override fun onResume() {
        super.onResume()
        activateButton() // 초기 상태 설정
    }

    private fun deleteSharedPreferences() {
        sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()  // 모든 데이터 삭제
        editor.apply()
    }

    private fun saveSharedPreferences() {
        sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("conditionPoint", binding.fragmentEvaluationExercisePointTv.text.toString())
        editor.putString("memo", binding.fragmentEvaluationExerciseMemoEt.text.toString())
        editor.apply()
    }

    private fun saveDescriptionNum (description: Int) {
        sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("selectedDescription", description)
        editor.apply()
    }

    private fun getSharedPreferences() {
        sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val point = sharedPreferences.getString("conditionPoint", "")
        val memo = sharedPreferences.getString("memo", "")
        selectTextDescription = sharedPreferences.getInt("selectedDescription",0)
        stateAllToggle()
        binding.fragmentEvaluationExercisePointTv.setText(point)
        binding.fragmentEvaluationExerciseMemoEt.setText(memo)
    }

    override fun setLayout() {
        initSetting()
        lifecycleScope.launch {
            exerciseId = tokenManager.getExerciseCardId().first()?.toInt() ?: 0
        }

        // 사진이 존재해야만 이전 값 매핑됨
        if(arguments?.getString("photo_uri") != null) {
            getSharedPreferences()
        }
        activateButton()
    }

    private fun setImageView() {
        val photoUriString = arguments?.getString("photo_uri")

        val photoUri = photoUriString?.let { Uri.parse(it) }
        photoUri?.let {
            GlobalApplication.loadImage(binding.fragmentEvaluationExercisePictureExerciseIv, it)
        }

        val file = photoUri?.let { getPathFromUri(requireContext(), it)?.let { File(it) } }

        // 이미지 파일을 압축
        val compressedFile = file?.let { compressImageFile(it, 500) }  // 여기서 maxSizeKB를 지정

        val requestFile = compressedFile?.asRequestBody("image/*".toMediaTypeOrNull())
        if (compressedFile != null) {
            profileImage = requestFile?.let {
                MultipartBody.Part.createFormData("image", compressedFile.name, it)
            }!!
        }

        if (compressedFile != null) {
            Log.d("CompressedImage", "File size: ${compressedFile.length()} bytes")
        }

    }


    private fun compressImageFile(imageFile: File, maxSizeKB: Int): File {
        val bitmap = BitmapFactory.decodeFile(imageFile.path)
        var quality = 100
        val outputStream = ByteArrayOutputStream()

        do {
            outputStream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            quality -= 5
        } while (outputStream.size() / 1024 > maxSizeKB && quality > 0)

        val compressedFile = File(imageFile.parent, "compressed_${imageFile.name}")
        try {
            val fos = FileOutputStream(compressedFile)
            fos.write(outputStream.toByteArray())
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return compressedFile
    }

    private fun getPathFromUri(context: Context, uri: Uri): String? {
        var path: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = it.getString(columnIndex)
            }
        }
        return path
    }


    private fun initSetting() {
//        initActivityResultLauncher()
        observeLifeCycle()
        setImageView()
        setList()
        setScoreText()
        setOnClickBtn()
    }



    private fun activateButton() {
        val args = arguments?.getString("photo_uri").toString()
        if(binding.fragmentEvaluationExercisePointTv.text.isNullOrEmpty().not() &&
            binding.fragmentEvaluationExerciseMemoEt.text.isNullOrEmpty().not() &&
            args.isNullOrEmpty().not() &&
            selectTextDescription in 0..4
            ) {
            binding.fragmentEvaluationExerciseSuccessBt.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
            binding.fragmentEvaluationExerciseSuccessBt.isEnabled = true
            binding.fragmentEvaluationExerciseSuccessBt.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
        } else {
            binding.fragmentEvaluationExerciseSuccessBt.setBackgroundResource(R.drawable.radius_corners_61dp_stroke_1)
            binding.fragmentEvaluationExerciseSuccessBt.isEnabled = false
            binding.fragmentEvaluationExerciseSuccessBt.setTextColor(ContextCompat.getColor(requireContext(), R.color.Purple_700))
        }
    }

    private fun observeLifeCycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                stepperViewModel.diaryItems.collectLatest {
                    if (it.isSuccess) {
                        val action = EvaluationExerciseFragmentDirections.actionFragmentEvaluationExerciseToFragmentExerciseComplete()
                        findNavController().navigateSafe(action.actionId)
                    }
                }
            }
        }
    }

    private fun setScoreText() {
        binding.fragmentEvaluationExercisePointTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                score = try {
                    binding.fragmentEvaluationExercisePointTv.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                if (score in 0..100) {
                    binding.fragmentEvaluationExerciseProgressbarPb.progress = score
                } else {
                    binding.fragmentEvaluationExerciseProgressbarPb.progress = 0
                    score = 0
                }
                binding.fragmentEvaluationExerciseScoreTv.text = "${score}점"
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun setOnClickBtn() {
        with(binding) {
            fragmentEvaluationExerciseBlur20Iv.setOnClickListener {
                saveDescriptionNum(0)
                selectTextDescription = 0
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur40Iv.setOnClickListener {
                saveDescriptionNum(1)
                selectTextDescription = 1
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur60Iv.setOnClickListener {
                saveDescriptionNum(2)
                selectTextDescription = 2
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur80Iv.setOnClickListener {
                saveDescriptionNum(3)
                selectTextDescription = 3
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur100Iv.setOnClickListener {
                saveDescriptionNum(4)
                selectTextDescription = 4
                stateAllToggle()
            }

            // 뒤로 가기
            fragmentEvaluationExerciseBackIv.setOnClickListener {
                findNavController().popBackStack()
            }

            // 투데이 버튼
            fragmentEvaluationExerciseGoTodayIv.setOnClickListener {
                val action =
                    EvaluationExerciseFragmentDirections.actionFragmentEvaluationExerciseToTodayHomeFragment()
                findNavController().navigateSafe(action.actionId)
            }

            // 스테퍼 버튼
            fragmentEvaluationExerciseGoStepperIv.setOnClickListener {
                val action =
                    EvaluationExerciseFragmentDirections.actionFragmentEvaluationExerciseToStepperFragment()
                findNavController().navigateSafe(action.actionId)
            }

            // 카메라 프래그먼트로 이동
            fragmentEvaluationExercisePictureExerciseIv.setOnClickListener {
                val action = EvaluationExerciseFragmentDirections.actionFragmentEvaluationExerciseToCameraFragment()
                findNavController().navigateSafe(action.actionId)
            }

            // 운동 아이디 필요
            fragmentEvaluationExerciseSuccessBt.setOnClickListener {
                val memo = fragmentEvaluationExerciseMemoEt.text.toString() //메모
                val state = sharedPreferences.getInt("selectedDescription",0) //상태값 0 1 2 3 4
                Log.d("클릭","state : $state")
                val imageUrl = profileImage //이미지
                val point = score //점수
                val gson = Gson()
                Log.d("평가", exerciseId.toString())
                val rateDiary = RateDiaryDto(
                    conditionRate = point.toString(),
                    painImage = "x",
                    painMemo = memo,
                    painRate = state.toString(),
                    exerciseCardId = exerciseId.toString() //운동아이디
                )
                val rj = gson.toJson(rateDiary, RateDiaryDto::class.java)
                val rb = rj.toRequestBody("application/json".toMediaTypeOrNull())
                stepperViewModel.postDiaryEdit(
                    image = imageUrl,
                    request = rb
                )
                deleteSharedPreferences() // 평가일지 작성 정보 삭제
                Log.d("클릭","rateDiary : $rateDiary")
                //위의 값들 서버로 전달
            }
        }
    }

    private fun setList() {
        with(binding) {
            imgList = listOf(
                fragmentEvaluationExerciseBlur20Iv,
                fragmentEvaluationExerciseBlur40Iv,
                fragmentEvaluationExerciseBlur60Iv,
                fragmentEvaluationExerciseBlur80Iv,
                fragmentEvaluationExerciseBlur100Iv
            )

            tvList = listOf(
                fragmentEvaluationExerciseBlur20Tv,
                fragmentEvaluationExerciseBlur40Tv,
                fragmentEvaluationExerciseBlur60Tv,
                fragmentEvaluationExerciseBlur80Tv,
                fragmentEvaluationExerciseBlur100Tv
            )

            triangleList = listOf(
                fragmentEvaluationExerciseTriangle20Iv,
                fragmentEvaluationExerciseTriangle40Iv,
                fragmentEvaluationExerciseTriangle60Iv,
                fragmentEvaluationExerciseTriangle80Iv,
                fragmentEvaluationExerciseTriangle100Iv
            )

            blurList = listOf(
                R.drawable.ic_img_20_bl,
                R.drawable.ic_img_40_bl,
                R.drawable.ic_img_60_bl,
                R.drawable.ic_img_80_bl,
                R.drawable.ic_img_100_bl,
            )

            notBlurList = listOf(
                R.drawable.ic_img_20,
                R.drawable.ic_img_40,
                R.drawable.ic_img_60,
                R.drawable.ic_img_80,
                R.drawable.ic_img_100,
            )

            stateTitleList = listOf(
                "완전 괜찮아요",
                "조금 덜 아팠어요",
                "큰 차이가 없어요",
                "조금 더 불편해요",
                "많이 아파요",
            )

            descriptionList = listOf(
                "온전한 상태로 회복하고 있는 것 같네요!\n오늘 운동도 고생 많았어요\n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "점점 나아지고 있는 모습 보기 좋아요!\n" +
                        "오늘 운동도 고생 많았어요 \n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "꾸준히 운동을 하면서 통증을 함께 더 \n" +
                        "줄여나가 봅시다!\n오늘 운동도 고생 많았어요\n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "오늘 운동할 때 몸이 많이 불편했나요? \n" +
                        "스트레칭으로 충분히 몸을 풀어주세요. \n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "해당 운동이 통증에 맞는 건지 한번 확인해봐요!\n" +
                        "꼼꼼하게 스트레칭 해주시고 통증이\n" +
                        "지속된다면 병원을 방문해 보세요\n" +
                        "차트에 오늘 컨디션 기록할게요!"
            )
        }
    }

    private fun stateAllToggle() {
        with(binding) {
            fragmentEvaluationExerciseDescriptionCl.visibility = View.VISIBLE
            for (i in 0..4) {
                if (i == selectTextDescription) {
                    imgList[i].setBackgroundResource(blurList[i])
                    triangleList[i].visibility = View.VISIBLE
                    fragmentEvaluationExerciseStateTv.text = stateTitleList[i]
                    fragmentEvaluationExerciseDescriptionTv.text = descriptionList[i]
                } else {
                    imgList[i].setBackgroundResource(notBlurList[i])
                    triangleList[i].visibility = View.GONE
                }
            }
        }
    }

//    private fun initActivityResultLauncher() {
//        galleryForResult = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result: ActivityResult ->
//            if (result.resultCode == AppCompatActivity.RESULT_OK) {
//                val selectImageUrl = result.data?.data
//                selectImageUrl?.let {
//                    GlobalApplication.loadCropRoundedSquareImage(
//                        requireContext(),
//                        binding.fragmentEvaluationExercisePictureExerciseIv,
//                        it.toString(),
//                        18
//                    )
//                    binding.fragmentEvaluationExercisePictureExerciseIb.visibility = View.GONE
//                    profileImage = it.toString()
//                }
//            }
//        }
//    }

//    private fun openGallery() {
//        val galleryIntent = Intent()
//            .setType("image/*")
//            .setAction(Intent.ACTION_GET_CONTENT)
//        galleryForResult.launch(Intent.createChooser(galleryIntent, "Select Picture"))
//    }
}