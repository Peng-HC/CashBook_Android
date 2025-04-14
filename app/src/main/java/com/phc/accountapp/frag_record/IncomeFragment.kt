package com.phc.accountapp.frag_record

import com.phc.accountapp.db.ConsumeType
import com.phc.accountapp.db.DBManager

class IncomeFragment : BaseRecordFragment() {
    override fun loadDataToGV() {
        super.loadDataToGV()
        val inList = DBManager.getListByConsumeType(ConsumeType.INCOME)
        typeList.addAll(inList)
        gvAdapter.notifyDataSetChanged()
    }

    override fun saveAccountToDB() {
        getAccountBeanInstance().let {
            it.consumeType = ConsumeType.INCOME
            DBManager.insertItemToAccountTb(it)
        }
    }

}
