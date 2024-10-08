package com.example.umc_stepper.ui.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityLoginBinding
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.token.TokenManager
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.man
import com.example.umc_stepper.ui.signup.SignUpActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login),
    ConfirmDialogInterface{
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var tokenManager: TokenManager
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var agreeDialog: AgreeDialog
    private lateinit var requestMultiplePermissionsLauncher: ActivityResultLauncher<Array<String>>

    private var fcmToken = ""

    override fun setLayout() {
        requestForUserData()
        initPermissionLaunchers()
        checkPermissionsAndProceed()
        setting()
        dd()
    }


    private fun dd(){
        binding.activityLoginStepperIv.setOnClickListener {
            startActivity(Intent(this@LoginActivity, man::class.java))
        }
    }
    // FCM 토큰 TokenManager에 저장하는 함수
    private fun saveFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) return@OnCompleteListener

            // Get new FCM registration token
            val token = task.result
            lifecycleScope.launch {
                tokenManager.saveFcmToken(token)
            }
        })
    }

    // FCM 토큰 TokenManager에서 가져오는 함수
    private fun getFcmToken() {
        lifecycleScope.launch {
            fcmToken = tokenManager.getFcmToken().first().toString()
        }
    }

    private fun activateLoginButton() {
        val enableButtonBackground = R.drawable.shape_rounded_square_purple700_50dp
        val disableButtonBackground = R.drawable.shape_rounded_square_50dp_stroke_1
        val enableButtonTextColor = ContextCompat.getColor(this, R.color.White)
        val disableButtonTextColor = ContextCompat.getColor(this, R.color.Purple_700)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val emailText = binding.activityLoginEmailEt.text.toString().trim()
                val passwordText = binding.activityLoginPasswordEt.text.toString().trim()

                if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                    binding.activityLoginBtn.setBackgroundResource(enableButtonBackground)
                    binding.activityLoginBtn.setTextColor(enableButtonTextColor)
                    binding.activityLoginBtn.isEnabled = true
                } else {
                    binding.activityLoginBtn.setBackgroundResource(disableButtonBackground)
                    binding.activityLoginBtn.setTextColor(disableButtonTextColor)
                    binding.activityLoginBtn.isEnabled = false
                }
            }
        }
        binding.activityLoginEmailEt.addTextChangedListener(textWatcher)
        binding.activityLoginPasswordEt.addTextChangedListener(textWatcher)
    }

    private fun setting() {
        lifecycleScope.launch {
            tokenManager.deleteAccessToken()
        }
        saveFcmToken()
        getFcmToken()
        setViewModel()
        activateLoginButton()
        barTransparent()
        onClicked()
    }

    private fun initPermissionLaunchers() {
        requestMultiplePermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                handlePermissionsResult(permissions)
            } else {
                handleLegacyPermissionsResult(permissions)
            }
        }
    }

    private fun checkPermissionsAndProceed() {
        if (!areAllPermissionsGranted()) {
            showDialog()
        }
    }

    //권한 확인
    private fun areAllPermissionsGranted(): Boolean {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    // 외부 터치시 키보드 숨기기, 포커스 제거
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        if (currentFocus is EditText) {
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun setViewModel() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this@LoginActivity.finish()
    }

    private fun goSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun barTransparent() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun resetAllBadges() {
        mainViewModel.resetAllBadges()
    }

    private fun onClicked() {
        with(binding) {
            activityLoginBtn.setOnClickListener {
                val email = activityLoginEmailEt.text.toString()
                val password = activityLoginPasswordEt.text.toString()
                resetAllBadges()
                tokenManager.saveEmail(email)
                loginViewModel.postUserLogInInfo(
                    LogInDto(
                        email = email,
                        password = password,
                        firebaseToken = fcmToken
                    )
                )
            }

            activityRegisterBtn.setOnClickListener {
                goSignUp()
            }
        }
    }

    private fun requestForUserData() { //응답
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginData.collectLatest { response ->
                    if (response.isSuccess) {
                        tokenManager.saveToken(response.result!!)
                        goHome()
                        Log.e("okhttp", "이동")
                    }
                }
            }
        }
    }

    //다이얼 로그 표시
    private fun showDialog() {
        agreeDialog = AgreeDialog(this@LoginActivity)

        with(agreeDialog) {
            isCancelable = false
            show(this@LoginActivity.supportFragmentManager, "AgreeDialog")
        }
    }

    //다이얼로그 확인 버튼
    override fun onClickConfirmButton(ok: Boolean) {
        if (ok) {
            askAllPermissions()
        }
    }

    //권한 요청 생성
    private fun askAllPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

        requestMultiplePermissionsLauncher.launch(permissions)
    }

    //android 13 버전 이상
    private fun handlePermissionsResult(permissions: Map<String, Boolean>) {
        if (permissions[Manifest.permission.POST_NOTIFICATIONS] == true) {
            Snackbar.make(binding.root, "알림 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        }
        if (permissions[Manifest.permission.CAMERA] == true) {
            Snackbar.make(binding.root, "카메라 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        }
        if (permissions[Manifest.permission.READ_MEDIA_IMAGES] == true || permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
            Snackbar.make(binding.root, "갤러리 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(binding.root, "필수 권한이 거부되었습니다. 설정에서 권한을 허용해 주세요.", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    //android 13 버전 이하
    private fun handleLegacyPermissionsResult(permissions: Map<String, Boolean>) {
        if (permissions[Manifest.permission.CAMERA] == true) {
            Snackbar.make(binding.root, "카메라 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        }
        if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
            Snackbar.make(binding.root, "갤러리 권한을 허용하였습니다.", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(binding.root, "필수 권한이 거부되었습니다. 설정에서 권한을 허용해 주세요.", Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}
