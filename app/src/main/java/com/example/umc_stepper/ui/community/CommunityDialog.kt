package com.example.umc_stepper.ui.community

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.umc_stepper.databinding.DialogCommunityBinding
import com.example.umc_stepper.ui.login.ConfirmDialogInterface

class CommunityDialog(
    private val title: String,
    private val btn1: String,
    private val btn2: String,
    private val communityDialogInterface: CommunityDialogInterface
) : DialogFragment() {

    private var _binding: DialogCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.65).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogCommunityBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(binding) {
            dialogCommunityTitle.text = title
            dialogCommunityButton1Bt.text = btn1
            dialogCommunityButton2Bt.text = btn2

            dialogCommunityButton1Bt.setOnClickListener {
                communityDialogInterface.OnClickBtn1(btn1)
                dismiss()
            }
            dialogCommunityButton2Bt.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
