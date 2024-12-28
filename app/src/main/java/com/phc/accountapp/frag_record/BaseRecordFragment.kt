package com.phc.accountapp.frag_record

import android.content.Context
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.phc.accountapp.R
import com.phc.accountapp.adapter.TypeBaseAdapter
import com.phc.accountapp.db.ConsumeType
import com.phc.accountapp.db.DBManager
import com.phc.accountapp.db.TypeBean
import com.phc.accountapp.utils.BeiZhuDialog
import com.phc.accountapp.utils.KeyBoardUtils
import com.phc.accountapp.utils.SelectTimeDialog

abstract class BaseRecordFragment : Fragment(), View.OnClickListener {
    private lateinit var timeTv: TextView
    private lateinit var beiZhuTv: TextView
    private lateinit var typeGv: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_outcome, container, false)
        initView(view)
        //给GridView填充数据的方法
        loadDataToGV()
        return view
    }

    private fun loadDataToGV() {
        val typeList = DBManager.getListByConsumeType(ConsumeType.EXPENSE)
        val adapter = context?.let { TypeBaseAdapter(it, typeList) }
        typeGv.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun initView(view: View) {
        val keyboardView = view.findViewById<KeyboardView>(R.id.frag_record_keyboard)
        val moneyEt = view.findViewById<EditText>(R.id.frag_record_et_money)
        beiZhuTv = view.findViewById(R.id.frag_record_tv_beizhu)
        timeTv = view.findViewById(R.id.frag_record_tv_time)
        typeGv = view.findViewById(R.id.frag_record_gv)

        timeTv.setOnClickListener(this)
        beiZhuTv.setOnClickListener(this)

        val keyBoard = KeyBoardUtils(keyboardView, moneyEt)
        //让自定义软键盘显示出来
        keyBoard.showKeyBoard()
        //设置接口，监听确定按钮按钮被点击了
        keyBoard.setOnEnsureListener(object : KeyBoardUtils.OnEnsureListener {
            override fun onEnSure() {
                val editText = moneyEt.text.toString()
                if (editText.isEmpty() || editText == "0") {
                    activity?.finish()
                    return
                }
                val money = editText.toFloatOrNull()
                activity?.finish()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.frag_record_tv_time -> {
                // 时间弹窗
                context?.let { showTimeDialog(it) }
            }

            R.id.frag_record_tv_beizhu -> {
                context?.let { showBZDialog(it) }
            }
        }
    }

    // 日历弹窗
    private fun showTimeDialog(context: Context) {
        SelectTimeDialog(context).apply {
            show()
            setOnEnsureListener(object : SelectTimeDialog.OnEnsureListener {
                override fun onEnsure(timeFormat: String, year: Int, month: Int, day: Int) {
                    timeTv.text = timeFormat
                }
            })
        }

    }

    // 备注
    private fun showBZDialog(context: Context) {
        BeiZhuDialog(context).apply {
            show()
            setOnEnsureListener(object : BeiZhuDialog.OnEnsureListener {
                override fun onEnsure() {
                    val msg = getBeiZhuEditText()
                    if (msg.isNotEmpty()) {
                        beiZhuTv.text = msg
                    }
                    // 存储到数据库
                }

            })
        }

    }
}