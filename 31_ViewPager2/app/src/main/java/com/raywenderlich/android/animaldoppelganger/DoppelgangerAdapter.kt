package com.raywenderlich.android.animaldoppelganger

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DoppelgangerAdapter(activity: AppCompatActivity, val itemsCount: Int): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = itemsCount

    override fun createFragment(position: Int): Fragment = DoppelgangerFragment.getInstance(position)

}