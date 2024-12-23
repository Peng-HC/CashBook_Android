package com.phc.accountapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

class MainActivity : AppCompatActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_iv_search -> {

            }

            R.id.main_btn_more -> {

            }

            R.id.main_btn_edit -> {
                startActivity(Intent(this, RecordActivity::class.java))
            }
        }
    }
}