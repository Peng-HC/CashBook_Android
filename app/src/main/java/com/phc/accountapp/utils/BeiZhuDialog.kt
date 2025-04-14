package com.phc.accountapp.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import com.phc.accountapp.R

class BeiZhuDialog(private val context: Context) : Dialog(context), View.OnClickListener {
    private var beiZhuEt: EditText? = null
    private var cancelBtn: Button? = null
    private var ensureBtn: Button? = null

    companion object {
        const val TAG = "BeiZhuDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_beizhu)
        beiZhuEt = findViewById(R.id.dialog_beizhu_et)
        cancelBtn = findViewById(R.id.dialog_beizhu_btn_cancel)
        ensureBtn = findViewById(R.id.dialog_beizhu_btn_ensure)

        cancelBtn?.setOnClickListener(this)
        ensureBtn?.setOnClickListener(this)
    }

    private lateinit var onEnsureListener: OnEnsureListener

    // 回调接口
    interface OnEnsureListener {
        fun onEnsure()
    }

    fun setOnEnsureListener(listener: OnEnsureListener) {
        this.onEnsureListener = listener
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // 取消
            R.id.dialog_beizhu_btn_cancel -> {
                cancel()
            }
            // 确定
            R.id.dialog_beizhu_btn_ensure -> {
                // TODO:数据存储到数据库
                if (::onEnsureListener.isInitialized) {
                    onEnsureListener.onEnsure()
                }
                cancel()
            }

        }
    }

    fun getBeiZhuEditText(): String {
        return beiZhuEt?.text.toString().trim()
    }

    /* 设置Dialog的尺寸和屏幕尺寸一致*/
    fun setDialogSize() {
        window?.let {
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels

            it.setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setBackgroundDrawableResource(android.R.color.transparent)
            it.setGravity(Gravity.BOTTOM)
            handler.sendEmptyMessageDelayed(1, 200)
        }
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        // 这是在主线程上，所以我们可以安全地使用this作为Activity的上下文
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val inputMethodManager: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}