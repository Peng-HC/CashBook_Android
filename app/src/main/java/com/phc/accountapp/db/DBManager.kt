package com.phc.accountapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

/*
* 负责管理数据库的对象
* 主要对于表当中的内容进行操作，增删改查
* */
object DBManager {
    private lateinit var db: SQLiteDatabase

    /* 初始化数据库对象*/
    fun initDB(context: Context) {
        val helper = DBOpenHelper(context)
        // 获得可写入的数据库对象
        db = helper.writableDatabase
    }

    /**
     * 读取数据库当中的数据，写入内存集合里
     *   kind :表示收入或者支出
     * */
    fun getListByConsumeType(type: ConsumeType): List<TypeBean> {
        if (db == null) {
            Log.e("DB", "db is null")
            return emptyList()
        }
        val resultList = mutableListOf<TypeBean>()
        // TODO:这样写执行sql后得到的数据是空的
//        val sql = "select * from typetb where consumeType=${type.value}"
        val sql = "select * from typetb"
        val cursor = db!!.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val bean = TypeBean()
            cursor.getColumnIndex("id").apply {
                if (this >= 0) {
                    bean.id = cursor.getInt(this)
                }
            }
            cursor.getColumnIndex("typeName").apply {
                if (this >= 0) {
                    bean.typeName = cursor.getString(this)
                }
            }
            cursor.getColumnIndex("unselectedImageId").apply {
                if (this >= 0) {
                    bean.unselectedImageId = cursor.getInt(this)
                }
            }
            cursor.getColumnIndex("selectedImageId").apply {
                if (this >= 0) {
                    bean.selectedImageId = cursor.getInt(this)
                }
            }
            cursor.getColumnIndex("consumeType").apply {
                if (this >= 0) {
                    bean.consumeType = if (cursor.getInt(this) == 0) {
                        ConsumeType.EXPENSE
                    } else {
                        ConsumeType.INCOME
                    }
                }
            }
            resultList.add(bean)
        }
        return resultList
    }
}