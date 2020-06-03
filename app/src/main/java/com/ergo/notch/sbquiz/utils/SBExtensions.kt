package com.ergo.notch.sbquiz.utils

import android.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context
import android.content.DialogInterface
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import com.ergo.notch.sbquiz.R
import com.jakewharton.rxbinding.view.RxView
import io.reactivex.subjects.ReplaySubject
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

fun createDialog(
    context: Context,
    message: String = "Message",
    okBtn: String = context.getString(android.R.string.ok),
    noBtn: String = "",
    isCancelable: Boolean = true,
    positiveListener: DialogInterface.OnClickListener? = null,
    negativeListener: DialogInterface.OnClickListener? = null
): AlertDialog {
    val dialog = AlertDialog.Builder(context, R.style.BasicDialog)
        .setMessage(message)
        .setPositiveButton(okBtn, positiveListener)
        .setNegativeButton(noBtn, negativeListener)
        .setCancelable(isCancelable)
    if (noBtn.isNotEmpty()) {
        dialog.setNegativeButton(noBtn, negativeListener)
    }
    dialog.create()
    return dialog.show()
}

fun createDialogWithTitle(
    context: Context,
    title: String = "Title",
    message: String = "Message",
    okBtn: String = context.getString(android.R.string.ok),
    noBtn: String = "",
    isCancelable: Boolean = true,
    positiveListener: DialogInterface.OnClickListener? = null,
    negativeListener: DialogInterface.OnClickListener? = null
): AlertDialog {
    val dialog = AlertDialog.Builder(context, R.style.BasicDialog)
        .setMessage(message)
        .setTitle(title)
        .setPositiveButton(okBtn, positiveListener)
        .setCancelable(isCancelable)
    if (noBtn.isNotEmpty()) {
        dialog.setNegativeButton(noBtn, negativeListener)
    }
    dialog.create()
    return dialog.show()
}

fun View.fadeInAnimation(animationDuration: Long) {
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.apply {
        interpolator = AccelerateInterpolator()
        duration = animationDuration
    }
    this.animation = fadeIn
    this.visible()
}

fun View.fadeOutAnimation(animationDuration: Long) {
    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.apply {
        interpolator = AccelerateInterpolator()
        duration = animationDuration
    }
    this.animation = fadeOut
    this.invisible()
}

fun View.clickEvent(): LiveData<Void> {
    val listener = MutableLiveData<Void>()
    RxView.clicks(this)
        .throttleFirst(1000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            listener.value = it
        }
    return listener
}

fun Group.clickEvent(): LiveData<Void> {
    val listener = MutableLiveData<Void>()
    this.referencedIds.forEach { id ->
        val groupView = rootView.findViewById<View>(id)
        RxView.clicks(groupView)
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.value = it
            }
    }
    return listener
}

fun View.clickEventObservable(): ReplaySubject<Void> {
    val replaySubject = ReplaySubject.create<Void>()
    RxView.clicks(this)
        .throttleFirst(1000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            replaySubject.onNext(it)
        }
    return replaySubject
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun ViewGroup.inflate(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.gone() {
    this.visibility = View.GONE
}

fun View.setDrawableBackground(drawableResId: Int) {
    this.background = ContextCompat.getDrawable(this.context, drawableResId)
}

fun String.isValidEmail(): Boolean {
    val emailPattern =
        ("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    val pattern = Pattern.compile(emailPattern)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}
