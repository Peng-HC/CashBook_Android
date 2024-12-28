package com.phc.accountapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.phc.accountapp.R
import com.phc.accountapp.db.TypeBean

class TypeBaseAdapter(val context: Context, private val mData: List<TypeBean>) : BaseAdapter() {
    // 选中的位置
    private var selectPos = - 1
    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): Any {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv, parent, false)
        val iv: ImageView? = view?.findViewById(R.id.item_recordfrag_iv)
        val tv: TextView? = view?.findViewById(R.id.item_recordfrag_tv)
        val bean = mData[position]
        tv?.text = bean.typeName
        if (selectPos == position) {
            iv?.setImageResource(bean.selectedImageId)
        } else {
            iv?.setImageResource(bean.unselectedImageId)
        }
        return view
    }
}