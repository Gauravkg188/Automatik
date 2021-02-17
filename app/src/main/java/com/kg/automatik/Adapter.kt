package com.kg.automatik

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import java.util.*

class Adapter(fragmentManager: FragmentManager,private var fragments: ArrayList<Fragment>,
        private var titles: ArrayList<String>
): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    fun addFragment(fragment:Fragment,title:String)
    {
        fragments.add(fragment);
        titles.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
       return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles.get(position)
    }

}