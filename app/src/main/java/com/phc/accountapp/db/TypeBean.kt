package com.phc.accountapp.db

/**
 * 收入或支出具体的类型
 */
data class TypeBean(
    var id: Int = 0,
    var typeName: String = "",
    var unselectedImageId: Int = 0, // 未被选中的图片ID
    var selectedImageId: Int = 0, // 被选中的图片ID
    var consumeType: ConsumeType = ConsumeType.EXPENSE // 消费类型
)

enum class ConsumeType(val value: Int) {
    // 支出
    EXPENSE(0),

    // 收入
    INCOME(1),
}