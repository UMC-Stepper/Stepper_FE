package com.example.umc_stepper.ui.stepper

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityCameraBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService

class CameraActivity : BaseActivity<ActivityCameraBinding>(R.layout.activity_camera) {

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                //Manifest.permission.READ_EXTERNAL_STORAGE
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    // 카메라 작업을 수행하기 위한 스레드 풀을 관리 (메인 스레드와 별도로 동작)
    private lateinit var cameraExecutor: ExecutorService

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun setLayout() {

        // 이 화면 들어오면 권한 확인 및 요청
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        } else {
            startCamera()
        }

        // 사진 찍기 버튼
        binding.activityCameraCaptureIv.setOnClickListener {
            takePhoto(this)
        }

        // 취소 버튼
        binding.activityCameraCancelIv.setOnClickListener {

        }
    }

    // 버튼 클릭해서 카메라 전면 후면 전환 함수
    private fun toggleCamera() {
        val changeBtn = binding.activityCameraChangeIv
        changeBtn.setOnClickListener {
            // CameraSelector 업데이트
            if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                startCamera() //바뀐 카메라 화면으로 카메라 재실행
            } else {
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                startCamera() //바뀐 카메라 화면으로 카메라 재실행
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    // 카메로부터 이미지를 촬영하고 저장하는 함수
    private fun takePhoto(context: Context) {

        // 새로 캡처한 이미지를 저장하기 위한 옵션
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry. (MediaStore 콘텐츠 값 생성)
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            // Android Q 이상- > RELATIVE_PATH 사용해서 저장 경로 지정.
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata (이미지 저장 옵션 설정)
        // 이 객체에서 원하는 출력 방법 지정 가능
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(this.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // Set up image capture listener, which is triggered after photo has been taken
        // takePicture() 호출, 이미지 캡처 및 저장
        // outputOptions, 실행자, 이미지 저장될 때 콜백 전달
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.d(TAG, "Photo capture exc: $exc")
                    Log.d(TAG, "사진 촬영에 실패하였습니다.")
                }

                // 캡쳐 성공 -> 사진을 저장
                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    Log.d(TAG, "사진 촬영에 성공하였습니다.")

                    // 촬영한 사진을
                    output.savedUri?.let { uri ->
                        val intent = Intent(this@CameraActivity, CameraDetailActivity::class.java).apply {
                            putExtra("photo_uri", uri.toString())
                        }
                        startActivity(intent)
                    }
                }
            }
        )
    }

    // 카메라를 사용하여 미리보기 표시하고 객체 감지 수행하는 함수 (카메라 화면 제공)
    private fun startCamera() {

        // 카메라 제공자 초기화, 리스너 등록
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // 카메라 설정 초기화 (Preview 사용 사례 초기화)
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = binding.viewFinder.surfaceProvider // 미리보기 제공
            }

            // imageCaputre 인스턴스를 빌드
            imageCapture = ImageCapture.Builder().build()

            // 버튼 클릭 시 카메라 전면 후면 전환
            toggleCamera()

            try {
                // 기존에 바인딩된 사용 사례를 해제
                cameraProvider.unbindAll()

                // 카메라를 사용하여 미리보기를 표시
                val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                val cameraController = camera.cameraControl //CameraX 핵심 기능을 제공

                // 터치 이벤트를 통한 초점 맞추기
                binding.viewFinder.setOnTouchListener { v : View, event : MotionEvent ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            v.performClick()
                            return@setOnTouchListener true
                        }
                        MotionEvent.ACTION_UP -> {

                            val factory = binding.viewFinder.meteringPointFactory
                            val point = factory.createPoint(event.x, event.y)
                            val action = FocusMeteringAction.Builder(point).build()
                            cameraController?.startFocusAndMetering(action)
                            v.performClick()
                            return@setOnTouchListener true
                        }
                        else -> return@setOnTouchListener false
                    }
                }
            } catch (exc: Exception) {
                Log.e(TAG, "카메라 시작 실패", exc)
            }
        }, ContextCompat.getMainExecutor(this)) // 메인 스레드에서 실행
    }


}