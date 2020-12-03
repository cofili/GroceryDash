package com.example.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryapp.fragments.ProductListFragment
import com.example.groceryapp.models.SubCategory

class AdapterFragment(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var mFragment: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return mTitleList.size

    }

    override fun getItem(position: Int): Fragment {
        return mFragment.get(position)
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList.get(position)
    }

    // add fragment dynamically

    fun AddFragment(subCategory: SubCategory){

        mFragment.add(ProductListFragment.newInstance(subCategory.subId))
        mTitleList.add(subCategory.subName)

    }

}