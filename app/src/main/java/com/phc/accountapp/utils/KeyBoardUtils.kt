package com.phc.accountapp.utils

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.opengl.Visibility
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.phc.accountapp.R

/**
 * 自定义键盘
 */
class KeyBoardUtils(
    private val keyboardView: KeyboardView,
    private val editText: EditText,
) {
    private lateinit var onEnsureListener: OnEnsureListener
    // 确认的回调方法
    interface OnEnsureListener {
        fun onEnSure()
    }

    fun setOnEnsureListener(listener: OnEnsureListener) {
        this.onEnsureListener = listener
    }


    init {
        // 禁止系统弹键盘窗
        editText.inputType = InputType.TYPE_NULL
        keyboardView.keyboard = Keyboard(editText.context, R.xml.customize_keyboard)
        keyboardView.isEnabled = true
        //设置键盘按钮被点击了的监听
        keyboardView.setOnKeyboardActionListener(object: OnKeyboardActionListener {
            override fun onPress(primaryCode: Int) {
            }

            override fun onRelease(primaryCode: Int) {
            }

            override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
                val editable = editText.text
                val startPos = editText.selectionStart
                when (primaryCode) {
                    // 删除键
                    Keyboard.KEYCODE_DELETE -> {
                        if (editable != null && editable.isNotEmpty()) {
                            editable.delete(startPos - 1, startPos)
                        }
                    }
                    // 清空
                    Keyboard.KEYCODE_CANCEL -> {
                        editable.clear()
                    }
                    // 完成
                    Keyboard.KEYCODE_DONE -> {
                        //通过接口回调的方法，当点击确定时，可以调用这个方法
                        onEnsureListener.onEnSure()
                    }

                    else -> {
                    }
                }
                true
            }

            override fun onText(text: CharSequence?) {
            }

            override fun swipeLeft() {
            }

            override fun swipeRight() {
            }

            override fun swipeDown() {
            }

            override fun swipeUp() {
            }

        })
    }
    // 显示键盘
    fun showKeyBoard() {
        if (keyboardView.visibility == View.VISIBLE) {
            return
        }
        keyboardView.visibility = View.VISIBLE
    }
    // 隐藏键盘
    fun hideKeyBoard() {
        if (keyboardView.visibility == View.GONE) {
            return
        }
        keyboardView.visibility = View.GONE
    }
}