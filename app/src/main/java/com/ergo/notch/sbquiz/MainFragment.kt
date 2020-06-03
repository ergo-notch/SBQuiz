package com.ergo.notch.sbquiz

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ergo.notch.sbquiz.base.view.BaseFragment
import com.ergo.notch.sbquiz.ui.view.fragments.ListFragment
import com.ergo.notch.sbquiz.ui.view.fragments.MapFragment
import kotlinx.android.synthetic.main.fragment_main_layout.*

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : BaseFragment() {

    private val listFragment: ListFragment by lazy {
        ListFragment.newInstance()
    }
    private val mapFragment: MapFragment by lazy {
        MapFragment.newInstance()
    }

    override fun getResourceLayout(): Int = R.layout.fragment_main_layout

    override fun initView(view: View, savedInstanceState: Bundle?) {
        this.configureMenu()
    }

    private fun configureMenu() {
        this.navigation.setOnNavigationItemSelectedListener { menuItem ->
            val fragment: Fragment = when (menuItem.itemId) {
                R.id.mapItem -> this.mapFragment
                else -> this.listFragment
            }
            this@MainFragment.replaceChildFragment(
                fragment,
                R.id.fragment_container,
                this@MainFragment.childFragmentManager,
                false
            )
            true
        }
        this.navigation.selectedItemId = R.id.mapItem
    }

    companion object {
        fun newInstance() = MainFragment()
    }


}