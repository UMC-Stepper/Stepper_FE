package com.example.umc_stepper.ui.community

import android.view.View
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunitySearchBinding

class CommunitySearchFragment :
    BaseFragment<FragmentCommunitySearchBinding>(R.layout.fragment_community_search) {

    private lateinit var searchItemAdapter: CommunitySearchAdapter

    override fun setLayout() {
        init()
    }

    private fun init() {
        initRecyclerView()
        setOnClick()
    }

    private fun initRecyclerView() {
        searchItemAdapter = CommunitySearchAdapter()
        binding.fragmentCommunitySearchItemRv.adapter = searchItemAdapter
    }

    private fun setOnClick() {
        with(binding) {
            textInputLayout.setOnClickListener {
                val query = fragmentCommunitySearchEditEt.text.toString()

            }
            fragmentCommunitySearchCancelTv.setOnClickListener {
                fragmentCommunitySearchEditEt.text?.clear()
            }
        }
    }

    private fun showProgress(){
        binding.fragmentCommunitySearchProgressPb.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        binding.fragmentCommunitySearchProgressPb.visibility = View.GONE
    }
}