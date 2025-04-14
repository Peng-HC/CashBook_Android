package com.phc.accountapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.phc.accountapp.adapter.AccountAdapter
import com.phc.accountapp.db.AccountBean
import com.phc.accountapp.db.DBManager
import java.util.Calendar

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var accountAdapter: AccountAdapter
    private var time = TimeType()
    private var mData = mutableListOf<AccountBean>()
    private lateinit var todayLv: ListView

    var topOutTv: TextView? = null
    var topInTv: TextView? = null
    var topbudgetTv: TextView? = null
    var topConTv: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTime()
        initView()
        //添加ListView的头布局
        addLVHeaderView()
        accountAdapter = AccountAdapter(this, mData)
        todayLv.adapter = accountAdapter
    }

    private fun addLVHeaderView() {
        val headerView = layoutInflater.inflate(R.layout.item_mainlv_top,null)
        todayLv.addHeaderView(headerView)
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        time.year = calendar.get(Calendar.YEAR)
        time.month = calendar.get(Calendar.MONTH) + 1
        time.day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun initView() {
        todayLv = findViewById(R.id.main_lv)

    }

    override fun onResume() {
        super.onResume()
        // 读取数据库的账本信息
        loadData()
        setTopTvShow()
    }

    /**
     * 设置头布局当中文本内容的显示
     */
    private fun setTopTvShow() {
        TODO("Not yet implemented")
    }

    private fun loadData() {
        val list = DBManager.getAccountListOneDayFromAccounttb(time.year, time.month, time.day)
        mData.clear()
        mData.addAll(list)
        accountAdapter.notifyDataSetChanged()
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

data class TimeType(
    var year: Int = 2025,
    var month: Int = 1,
    var day: Int = 1,
    val time: String = "",
)