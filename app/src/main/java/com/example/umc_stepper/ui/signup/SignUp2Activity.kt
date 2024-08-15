package com.example.umc_stepper.ui.signup

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
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.FragmentRegister2Binding
import com.example.umc_stepper.domain.model.request.member_controller.UserDto
import com.example.umc_stepper.ui.login.LoginActivity
import com.example.umc_stepper.ui.login.LoginViewModel
import com.example.umc_stepper.utils.GlobalApplication
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class SignUp2Activity : BaseActivity<FragmentRegister2Binding>(R.layout.fragment_register_2) {
    private lateinit var user: UserDto
    private var gson: Gson = Gson()
    private lateinit var galleryForResult: ActivityResultLauncher<Intent>
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var selectImageUri: Uri
    private lateinit var profile: MultipartBody.Part

    override fun setLayout() {
        init()
    }

    private fun init() {
        initLoginViewModel()
        initActivityResultLauncher()
        observeLifeCycle()
        receiveUserData()
        setOnClickBtn()
        addTextWatcher()
    }

    private fun initLoginViewModel() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun initActivityResultLauncher() {
        galleryForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK && result.data?.data != null) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let { uri ->
                    try {
                        // 선택된 이미지를 이미지 뷰에 표시
                        GlobalApplication.loadProfileImage(
                            binding.fragmentRegister2ProfileFrameIv,
                            uri.toString()
                        )

                        // 이미지 처리
                        handleSelectedImage(uri)

                        // 이미지 선택 UI 업데이트
                        binding.fragmentRegister2ProfileImgIv.visibility = View.GONE

                        // UserDto에 프로필 이미지 경로 설정
                        user.profileImage = uri.toString()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "이미지 처리 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (result.resultCode != RESULT_OK) {
                Toast.makeText(this, "이미지 선택이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSelectedImage(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.use { stream ->
            val bitmap = BitmapFactory.decodeStream(stream)
            val compressedFile = compressBitmap(bitmap, 500)

            val requestFile = compressedFile.asRequestBody("image/*".toMediaTypeOrNull())
            profile = MultipartBody.Part.createFormData(
                "image",
                compressedFile.name,
                requestFile
            )
        }
    }

    private fun compressBitmap(bitmap: Bitmap, maxSizeKB: Int): File {
        var quality = 100
        val outputStream = ByteArrayOutputStream()

        do {
            outputStream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            quality -= 5
        } while (outputStream.size() / 1024 > maxSizeKB && quality > 0)

        val file = File(cacheDir, "compressed_image.jpg")
        FileOutputStream(file).use { fos ->
            fos.write(outputStream.toByteArray())
        }

        return file
    }

    private fun observeLifeCycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.userData.collectLatest {
                    if (it.isSuccess) {
                        startActivity(Intent(this@SignUp2Activity, LoginActivity::class.java))
                    }
                }
            }
        }
    }

    private fun receiveUserData() {
        try {
            intent.getStringExtra("user")?.let { userJson ->
                user = gson.fromJson(userJson, UserDto::class.java)
                Log.e("user", user.toString())
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            Log.e("user", "Error parsing user data")
        }
    }

    private fun setOnClickBtn() {
        with(binding) {
            fragmentRegister2SuccessInputBt.setOnClickListener {
                val height = fragmentRegister2HeightEt.text.toString()
                val weight = fragmentRegister2WeightEt.text.toString()
                confirmNumberText(height, fragmentRegister2HeightEt)
                confirmNumberText(weight, fragmentRegister2WeightEt)

                val gson = Gson()
                val userJson = gson.toJson(user, UserDto::class.java)
                val userRequest = userJson.toRequestBody("application/json".toMediaTypeOrNull())

                if (weight.isNotEmpty() && height.isNotEmpty()) {
                    user.weight = weight.toInt()
                    user.height = height.toInt()
                    loginViewModel.postSignUpInfo(profileImage = profile, userDto = userRequest)
                }
            }
            fragmentRegister2ProfileCardCv.setOnClickListener {
                openGallery()
            }
        }
    }

    private fun confirmNumberText(confirmText: String, view: EditText) {
        try {
            confirmText.toInt()
        } catch (e: NumberFormatException) {
            view.setText("")
            Toast.makeText(this@SignUp2Activity, "숫자만 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryForResult.launch(galleryIntent)
    }

    private fun addTextWatcher() {
        binding.fragmentRegister2WeightEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkWeightField()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.fragmentRegister2HeightEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkHeightField()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun checkWeightField() {
        val wTextField = binding.fragmentRegister2WeightEt.text.toString()
        val drawableOk = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_ok)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }
        val drawableError = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_error)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }

        if (wTextField.isNotEmpty()) {
            binding.fragmentRegister2WeightEt.setCompoundDrawables(null, null, drawableOk, null)
        } else {
            binding.fragmentRegister2WeightEt.setCompoundDrawables(null, null, drawableError, null)
        }
    }

    private fun checkHeightField() {
        val hTextField = binding.fragmentRegister2HeightEt.text.toString()
        val drawableOk = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_ok)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }
        val drawableError = ContextCompat.getDrawable(this, R.drawable.ic_signup_pwd_check_error)?.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }

        if (hTextField.isNotEmpty()) {
            binding.fragmentRegister2HeightEt.setCompoundDrawables(null, null, drawableOk, null)
        } else {
            binding.fragmentRegister2HeightEt.setCompoundDrawables(null, null, drawableError, null)
        }
    }
}