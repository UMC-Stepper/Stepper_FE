package com.example.umc_stepper.ui.community.part

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_stepper.R
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.community.CommunityDialog
import com.example.umc_stepper.ui.community.CommunityDialogInterface
import com.example.umc_stepper.ui.community.savedcontents.post.PagerFragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CommunityPartHomeFragment : Fragment(){

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var titlePart: TextView
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_community_part_home, container, false)
        updateToolbar()
        var bodyPart = arguments?.getString("bodyPart").toString()
        bodyPart = when (bodyPart) {
            "무릎,다리" -> "무릎다리"
            "어깨,팔" -> "어깨팔"
            else -> {
                bodyPart
            }
        }
        titlePart = view.findViewById(R.id.community_part_home_title_tv)
        viewPager = view.findViewById(R.id.community_part_home_vp)
        tabLayout = view.findViewById(R.id.community_part_home_tl)
        titlePart.text = bodyPart
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        // 4개의 fragment add
        pagerAdapter.addFragment(CommunityPartHomeAskFragment())
        pagerAdapter.addFragment(CommunityPartHomeHealthFragment())
        pagerAdapter.addFragment(CommunityPartHomeFreeFragment())
        pagerAdapter.addFragment(CommunityPartHomeMotivationFragment())

        // adapter 연결
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int){
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position+1}")
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
        }.attach()
    }
    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("Community")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_toolbar_community_home)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_community_search)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_community_menu)
    }

}