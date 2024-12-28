package com.phc.accountapp.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.phc.accountapp.R

class SelectTimeDialog(private val context: Context) : Dialog(context), View.OnClickListener {

    private lateinit var cancelBtn: Button
    private lateinit var ensureBtn: Button
    private lateinit var datePicker: DatePicker
    private lateinit var hourEt: EditText
    private lateinit var minEt: EditText
    private lateinit var onEnsureListener: OnEnsureListener

    interface OnEnsureListener {
        fun onEnsure(timeFormat: String, year: Int, month: Int, day: Int)
    }

    fun setOnEnsureListener(onEnsureListener: OnEnsureListener) {
        this.onEnsureListener = onEnsureListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_time)

        cancelBtn = findViewById(R.id.dialog_time_btn_cancel)
        ensureBtn = findViewById(R.id.dialog_time_btn_ensure)
        datePicker = findViewById(R.id.dialog_time_dp)
        hourEt = findViewById(R.id.dialog_time_et_hour)
        minEt = findViewById(R.id.dialog_time_et_minute)


        cancelBtn.setOnClickListener(this)
        ensureBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dialog_time_btn_cancel -> {
                cancel()
            }

            R.id.dialog_time_btn_ensure -> {
                ensureClick()
                cancel()
            }
        }
    }

    private fun ensureClick() {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        val monthFormatStr = if (month < 10) "0$month" else "$month"
        val dayFormatStr = if (day < 10) "0$day" else "$day"
        // 获取用户输入的时:分
        val hourStr = hourEt.text.toString()
        val minStr = minEt.text.toString()

        val hour = if (hourStr.isNotEmpty()) {
            // 24小时
            hourStr.toInt() % 24
        } else 0
        val minute = if (minStr.isNotEmpty()) {
            // 60分钟
            minStr.toInt() % 60
        } else 0
        val hourFormatStr = if (hour < 10) "0$hour" else "$hour"
        val minuteFormatStr = if (minute < 10) "0$minute" else "$minute"
        val timeFormat =
            "${year}年${monthFormatStr}月${dayFormatStr}日${hourFormatStr}时${minuteFormatStr}分"
        if (::onEnsureListener.isInitialized) {
            onEnsureListener.onEnsure(timeFormat, year, month, day)
        }
    }
}
