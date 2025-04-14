package com.phc.accountapp.frag_record

import android.content.Context
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.phc.accountapp.R
import com.phc.accountapp.adapter.TypeBaseAdapter
import com.phc.accountapp.db.AccountBean
import com.phc.accountapp.db.ConsumeType
import com.phc.accountapp.db.DBManager
import com.phc.accountapp.db.TypeBean
import com.phc.accountapp.utils.BeiZhuDialog
import com.phc.accountapp.utils.KeyBoardUtils
import com.phc.accountapp.utils.SelectTimeDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

abstract class BaseRecordFragment : Fragment(), View.OnClickListener {
    private lateinit var typeIv: ImageView
    private lateinit var timeTv: TextView
    private lateinit var beiZhuTv: TextView
    private lateinit var typeTv: TextView
    private lateinit var typeGv: GridView
    private lateinit var accountBean: AccountBean

    lateinit var gvAdapter: TypeBaseAdapter
    lateinit var typeList: MutableList<TypeBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountBean = AccountBean(
            sImageId = R.mipmap.ic_qita_fs,
            typename = "其他"
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_outcome, container, false)
        initView(view)
        setInitTime()
        //给GridView填充数据的方法
        loadDataToGV()
        // 给GridView的每一个item设置点击监听事件
        setGridViewItemListener()
        return view
    }

    /**
     * 获取当前时间，显示在timeTv上
     */
    private fun setInitTime() {
        val date = Date()
        val sdf = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        val time = sdf.format(date)
        timeTv.text = time
        accountBean.time = time

        val calendar = Calendar.getInstance()
        accountBean.year = calendar.get(Calendar.YEAR)
        accountBean.month = calendar.get(Calendar.MONTH) + 1
        accountBean.day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun setGridViewItemListener() {
        typeGv.setOnItemClickListener { _, _, position, _ ->
            gvAdapter.setSelectedPos(position)
            gvAdapter.notifyDataSetInvalidated() //提示绘制发生变化了

            val bean = typeList[position]
            typeIv.setImageResource(bean.selectedImageId)
            typeTv.text = bean.typeName
            accountBean.typename = bean.typeName
            accountBean.sImageId = bean.selectedImageId
        }
    }

    open fun loadDataToGV() {
        typeList = mutableListOf()
        gvAdapter = TypeBaseAdapter(requireContext(), typeList)
        typeGv.adapter = gvAdapter
    }

    private fun initView(view: View) {
        val keyboardView = view.findViewById<KeyboardView>(R.id.frag_record_keyboard)
        val moneyEt = view.findViewById<EditText>(R.id.frag_record_et_money)
        typeIv = view.findViewById(R.id.frag_record_iv)
        beiZhuTv = view.findViewById(R.id.frag_record_tv_beizhu)
        typeTv = view.findViewById(R.id.frag_record_tv_type)
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
                    Toast.makeText(context,"请输入正确的金额(大于0)!",Toast.LENGTH_SHORT).show()
                    return
                }
                val money = editText.toFloatOrNull()
                if (money != null && money > 0) {
                    accountBean.money = money
                }
                saveAccountToDB()
                activity?.finish()
            }
        })
    }

    /* 让子类一定要重写这个方法*/
    abstract fun saveAccountToDB()

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
                    accountBean.time = timeFormat
                    accountBean.year = year
                    accountBean.month = month
                    accountBean.day = day
                }
            })
        }

    }

    // 备注
    private fun showBZDialog(context: Context) {
        BeiZhuDialog(context).apply {
            show()
            setDialogSize()
            setOnEnsureListener(object : BeiZhuDialog.OnEnsureListener {
                override fun onEnsure() {
                    val msg = getBeiZhuEditText()
                    if (msg.isNotEmpty()) {
                        beiZhuTv.text = msg
                        accountBean.beizhu = msg
                    }
                    // 存储到数据库
                }
            })
        }
    }

    fun getAccountBeanInstance(): AccountBean {
        return accountBean
    }
}