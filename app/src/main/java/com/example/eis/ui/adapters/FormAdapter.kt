package com.example.eis.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FormAdapter(private var fragments: ArrayList<Fragment>, fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}