package com.phc.accountapp.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.phc.accountapp.R

class BeiZhuDialog(private val context: Context) : Dialog(context), View.OnClickListener {
    private var beiZhuEt: EditText? = null
    private var cancelBtn: Button? = null
    private var ensureBtn: Button? = null

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

}