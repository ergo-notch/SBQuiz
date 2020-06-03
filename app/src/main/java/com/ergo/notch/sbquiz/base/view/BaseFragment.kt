package com.ergo.notch.sbquiz.base.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.FragmentManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.ergo.notch.sbquiz.R
import com.ergo.notch.sbquiz.utils.createDialog


abstract class BaseFragment : Fragment() {

    abstract fun getResourceLayout(): Int

    abstract fun initView(view: View, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(getResourceLayout(), container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    open fun replaceFragment(
        fragment: Fragment, @IdRes container: Int,
        addToBackStack: Boolean
    ) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()

        fragmentTransaction?.replace(
            container,
            fragment
        )
        if (addToBackStack) fragmentTransaction?.addToBackStack(fragment::class.java.name)
        fragmentTransaction?.commitAllowingStateLoss()
    }

    open fun replaceChildFragment(
        fragment: Fragment,
        @IdRes container: Int,
        childFragmentManager: FragmentManager,
        addToBackStack: Boolean
    ) {

        val fragmentTransaction = childFragmentManager.beginTransaction()

        fragmentTransaction.replace(
            container,
            fragment
        )
        if (addToBackStack) fragmentTransaction?.addToBackStack(fragment::class.java.name)
        fragmentTransaction.commitAllowingStateLoss()
    }

    open fun replaceParentFragment(
        fragment: Fragment, @IdRes container: Int,
        addToBackStack: Boolean
    ) {
        val fragmentManager = parentFragment?.fragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()

        fragmentTransaction?.replace(
            container,
            fragment
        )
        if (addToBackStack) fragmentTransaction?.addToBackStack(fragment::class.java.name)
        fragmentTransaction?.commitAllowingStateLoss()
    }

    open fun showAlert(message: String) {
        createDialog(
            requireContext(),
            message,
            getString(R.string.accept),
            isCancelable = false,
            positiveListener = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
        )
    }

    open fun showAlertWithAction(message: String, function: () -> (Unit)) {
        createDialog(
            requireContext(),
            message,
            getString(R.string.accept),
            getString(R.string.cancel),
            isCancelable = false,
            positiveListener = DialogInterface.OnClickListener { dialog, _ ->
                function()
                dialog.dismiss()
            },
            negativeListener = DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            }
        )
    }

    open fun showAlertWithResource(message: Int) {
        createDialog(
            requireContext(),
            getString(message),
            getString(R.string.accept),
            isCancelable = false,
            positiveListener = DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
        )
    }
}