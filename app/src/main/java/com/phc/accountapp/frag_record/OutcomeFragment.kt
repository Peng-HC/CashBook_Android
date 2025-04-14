package com.phc.accountapp.frag_record

import com.phc.accountapp.db.ConsumeType
import com.phc.accountapp.db.DBManager

class OutcomeFragment : BaseRecordFragment() {
    override fun loadDataToGV() {
        super.loadDataToGV()
        val inList = DBManager.getListByConsumeType(ConsumeType.EXPENSE)
        typeList.addAll(inList)
        gvAdapter.notifyDataSetChanged()
    }
    override fun saveAccountToDB() {
        getAccountBeanInstance().let {
            it.consumeType = ConsumeType.EXPENSE
            DBManager.insertItemToAccountTb(it)
        }
    }

}
