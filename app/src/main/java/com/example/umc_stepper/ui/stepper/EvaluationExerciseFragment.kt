package com.example.umc_stepper.ui.stepper

import android.content.Context
import android.content.Intent
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
    @Inject
    lateinit var tokenManager: TokenManager
    val stepperViewModel: StepperViewModel by activityViewModels()
    lateinit var imgList: List<ImageView>
    lateinit var tvList: List<TextView>
    lateinit var triangleList: List<ImageView>
    lateinit var blurList: List<Int>
    lateinit var notBlurList: List<Int>
    lateinit var stateTitleList: List<String>
    lateinit var descriptionList: List<String>
    var selectTextDescription = 0
    var exerciseId by Delegates.notNull<Int>()
    lateinit var profileImage: MultipartBody.Part
    var score = 0
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun setToolbar() {
        mainActivity.setBg2()
        mainActivity.updateToolbarTitle("운동 평가하기")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_back)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_today)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_stepper)
    }

    override fun setLayout() {
        initSetting()
        lifecycleScope.launch {
            exerciseId = tokenManager.getExerciseCardId().first()?.toInt() ?: 0
        }
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


    fun compressImageFile(imageFile: File, maxSizeKB: Int): File {
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
        setToolbar()
        setImageView()
        setList()
        setScoreText()
        setOnClickBtn()
    }

    private fun observeLifeCycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                stepperViewModel.diaryItems.collectLatest {
                    if (it.isSuccess) {
                        val action =
                            R.id.action_fragmentEvaluationExercise_to_fragmentExerciseComplete
                        findNavController().navigateSafe(action)
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
                selectTextDescription = 0
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur40Iv.setOnClickListener {
                selectTextDescription = 1
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur60Iv.setOnClickListener {
                selectTextDescription = 2
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur80Iv.setOnClickListener {
                selectTextDescription = 3
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur100Iv.setOnClickListener {
                selectTextDescription = 4
                stateAllToggle()
            }
            //카메라액티비티로 이동
            binding.fragmentEvaluationExercisePictureExerciseIv.setOnClickListener {
                val intent = Intent(activity, CameraActivity::class.java)
                startActivity(intent)
            }

            //운동아이디필요
            fragmentEvaluationExerciseSuccessBt.setOnClickListener {
                val memo = fragmentEvaluationExerciseMemoEt.text.toString() //메모
                val state = selectTextDescription //상태값 0 1 2 3 4
                val imageUrl = profileImage //이미지
                val point = score //점수
                mainActivity.visibleTag()
                val gson = Gson()
                Log.d("평가", exerciseId.toString())
                val rateDiary = RateDiaryDto(
                    conditionRate = point.toString(),
                    painImage = "x",
                    painMemo = memo,
                    painRate = selectTextDescription.toString(),
                    exerciseCardId = exerciseId.toString() //운동아이디
                )
                val rj = gson.toJson(rateDiary, RateDiaryDto::class.java)
                val rb = rj.toRequestBody("application/json".toMediaTypeOrNull())
                stepperViewModel.postDiaryEdit(
                    image = imageUrl,
                    request = rb
                )
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