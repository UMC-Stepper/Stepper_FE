package com.example.umc_stepper.ui.community.part

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentCommunityPartHomeBinding
import com.example.umc_stepper.ui.community.savedcontents.post.PagerFragmentStateAdapter
import com.example.umc_stepper.ui.community.weekly.CommunityWeeklyHomeFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CommunityPartHomeFragment : BaseFragment<FragmentCommunityPartHomeBinding>(R.layout.fragment_community_part_home) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var titlePart: TextView
    private lateinit var floatButton: FloatingActionButton
    private lateinit var subCategory: String

    override fun setLayout() {
        setButton()
        initView()
    }

    private fun initView() {
        floatButton = binding.fragmentCommunityWeeklyHomeFab
        titlePart = binding.communityPartHomeTitleTv
        viewPager = binding.communityPartHomeVp
        tabLayout = binding.communityPartHomeTl
    }

    private fun setButton() {
        binding.communityPartHomeToolbarBackIv.setOnClickListener {
            val action = CommunityPartHomeFragmentDirections.actionCommunityPartHomeFragmentToCommunityHomeFragment()
            findNavController().navigate(action.actionId)
        }

        // 검색 화면으로 이동
        binding.communityPartHomeToolbarGoSearch.setOnClickListener {
            val action = CommunityPartHomeFragmentDirections.actionCommunityPartHomeFragmentToCommunitySearchFragment()
            findNavController().navigate(action.actionId)
        }

        // 메뉴 화면으로 이동
        binding.communityPartHomeToolbarGoMenu.setOnClickListener {
            val action = CommunityPartHomeFragmentDirections.actionCommunityPartHomeFragmentToCommunityIndexFragment()
            findNavController().navigate(action.actionId)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val category2 = arguments?.getString("bodyPart").toString()
        titlePart.text = category2

        val category = when (arguments?.getString("bodyPart").toString()) {
            "무릎,다리" -> "무릎다리"
            "어깨,팔" -> "어깨팔"
            else -> arguments?.getString("bodyPart").toString()
        }

        val bundle = Bundle().apply {
            putString("bodyPart", category)
        }

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())

        // 4개의 fragment에 bundle 추가
        pagerAdapter.addFragment(CommunityPartHomeAskFragment(), bundle)
        pagerAdapter.addFragment(CommunityPartHomeHealthFragment(), bundle)
        pagerAdapter.addFragment(CommunityPartHomeFreeFragment(), bundle)
        pagerAdapter.addFragment(CommunityPartHomeMotivationFragment(), bundle)

        // adapter 연결
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position + 1}")
            }
        })

        // tablayout 붙이기
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> " QnA"
                1 -> " 건강정보 "
                2 -> "자유토크"
                3 -> "동기부여"
                else -> "Tab ${position + 1}"
            }
            subCategory=tab.text.toString()
        }.attach()

        //fab bodyPart, subCategory 넘기기
        floatButton.setOnClickListener {
            val args = Bundle().apply {
                putString("bodyPart", category)
                putString("subCategory",subCategory)
            }
            val action = CommunityPartHomeFragmentDirections.actionCommunityPartHomeFragmentToWeeklySegmentEditFragment()
            findNavController().navigateSafe(action.actionId, args)
        }
    }

}
