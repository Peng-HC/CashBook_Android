package com.phc.accountapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.phc.accountapp.adapter.RecordPagerAdapter
import com.phc.accountapp.frag_record.IncomeFragment
import com.phc.accountapp.frag_record.OutcomeFragment

/**
 * 记一笔页面
 */
class RecordActivity : AppCompatActivity() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        //1.查找控件
        tabLayout = findViewById<TabLayout>(R.id.record_tabs)
        viewPager = findViewById<ViewPager>(R.id.record_vp)
        //2.设置ViewPager加载页面
        initPager()
    }

    private fun initPager() {
//        初始化ViewPager页面的集合
        val fragmentList: MutableList<Fragment> = ArrayList()
        //        创建收入和支出页面，放置在Fragment当中
        val outFrag = OutcomeFragment() //支出
        val inFrag = IncomeFragment() //收入
        fragmentList.add(outFrag)
        fragmentList.add(inFrag)

//        创建适配器
        val pagerAdapter = RecordPagerAdapter(supportFragmentManager, fragmentList)
        //        设置适配器
        viewPager!!.adapter = pagerAdapter
        //将TabLayout和ViwePager进行关联
        tabLayout!!.setupWithViewPager(viewPager)
    }

    /* 点击事件*/
    fun onClick(view: View) {
        when (view.id) {
            R.id.record_iv_back -> finish()
        }
    }
}
