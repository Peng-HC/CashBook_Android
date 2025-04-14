package com.phc.accountapp.db

data class AccountBean(
    var id: Int = 0,
    var typename: String? = null, //类型
    var sImageId: Int = 0, //被选中类型图片
    var beizhu: String? = null, //备注
    var money: Float = 0f, //价格
    var time: String? = null, //保存时间字符串
    var year: Int = 0,
    var month: Int = 0,
    var day: Int = 0,
    var consumeType: ConsumeType = ConsumeType.EXPENSE, //类型  收入---1   支出---0
)