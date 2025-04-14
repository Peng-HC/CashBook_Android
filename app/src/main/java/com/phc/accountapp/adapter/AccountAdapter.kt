package com.phc.accountapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.phc.accountapp.R
import com.phc.accountapp.db.AccountBean
import java.util.Calendar

class AccountAdapter(
    private val context: Context,
    private val mData: List<AccountBean>
) : BaseAdapter() {

    class ViewHolder(private val view: View) {
        val typeIv: ImageView = view.findViewById(R.id.item_mainlv_iv)
        val titleTv: TextView = view.findViewById(R.id.item_mainlv_tv_title)
        val beiZhuTv: TextView = view.findViewById(R.id.item_mainlv_tv_beizhu)
        val timeTv: TextView = view.findViewById(R.id.item_mainlv_tv_money)
        val moneyTv: TextView = view.findViewById(R.id.item_mainlv_tv_time)
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): Any {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder?
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_mainly, parent, false)
            val viewHolder = ViewHolder(view)
            holder = viewHolder
            // 将ViewHolder对象存储在视图的tag属性中以便重用
            view.tag = holder
        } else {
            // 从视图的tag属性中获取ViewHolder对象
             holder= view.tag as ViewHolder
        }
        val bean = mData[position]
        holder.typeIv.setImageResource(bean.sImageId)
        holder.beiZhuTv.text = bean.beizhu
        holder.titleTv.text = bean.typename
        holder.moneyTv.text = "¥ " + bean.money
        holder.timeTv.text = if (isToday(bean)) {
            val timeSplit = bean.time?.split(" ")?: listOf()
            val t = if (timeSplit.size >= 2) {
                timeSplit[1]
            } else {
                ""
            }
            "今天 $t"
        } else {
            bean.time
        }
        return view ?: throw IllegalStateException("convertView must not be null")
    }

    private fun isToday(bean: AccountBean): Boolean {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return bean.year == year && bean.month == month && bean.day == day
    }

}