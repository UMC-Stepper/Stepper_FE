package com.example.umc_stepper.ui.stepper.additional

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.umc_stepper.R

class AdditionalExerciseMiddleFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView
                (inflater: LayoutInflater,
                 container: ViewGroup?,
                 savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_additional_exercise_middle, container, false)
    }

}