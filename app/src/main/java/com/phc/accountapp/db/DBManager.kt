package com.phc.accountapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

/*
* 负责管理数据库的对象
* 主要对于表当中的内容进行操作，增删改查
* */
object DBManager {
    private const val TAG = "DBManager"
    private lateinit var db: SQLiteDatabase

    /* 初始化数据库对象*/
    // 传入了ApplicationContext
    fun initDB(context: Context) {
        val helper = DBOpenHelper(context)
        // 获得可写入的数据库对象
        db = helper.writableDatabase
    }

    /**
     * 读取数据库当中的数据，写入内存集合里
     *   [type] :表示收入或者支出
     * */
    fun getListByConsumeType(type: ConsumeType): List<TypeBean> {
        if (::db.isInitialized.not()) {
            Log.e("DB", "db is not initialized")
            return emptyList()
        }
        val resultList = mutableListOf<TypeBean>()
        val sql = "select * from typetb where consumeType=${type.value}"
        val cursor = db.rawQuery(sql, null)
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

    fun getAccountListOneDayFromAccounttb(year: Int, month: Int, day: Int): List<AccountBean> {
        if (::db.isInitialized.not()) {
            Log.e("DB", "db is not initialized")
            return emptyList()
        }
        val dataList = mutableListOf<AccountBean>()
        val sql =
            "select * from accounttb where year = ? and month = ? and day = ? order by id desc"
        val cursor =
            db.rawQuery(sql, arrayOf(year.toString(), month.toString(), day.toString()))
        while (cursor.moveToNext()) {
            val accountBean = AccountBean()
            cursor.getColumnIndex("id").apply {
                if (this >= 0) {
                    accountBean.id = cursor.getInt(this)
                }
            }
            cursor.getColumnIndex("year").apply {
                if (this >= 0) {
                    accountBean.year = cursor.getInt(this)
                }
            }
            cursor.getColumnIndex("month").apply {
                if (this >= 0) {
                    accountBean.month = cursor.getInt(this)
                }
            }
            cursor.getColumnIndex("day").apply {
                if (this >= 0) {
                    accountBean.day = cursor.getInt(this)
                }
            }
            cursor.getColumnIndex("typename").apply {
                if (this >= 0) {
                    accountBean.typename = cursor.getString(this)
                }
            }
            cursor.getColumnIndex("beizhu").apply {
                if (this >= 0) {
                    accountBean.beizhu = cursor.getString(this)
                }
            }
            cursor.getColumnIndex("time").apply {
                if (this >= 0) {
                    accountBean.time = cursor.getString(this)
                }
            }
            cursor.getColumnIndex("sImageId").apply {
                if (this >= 0) {
                    accountBean.sImageId = cursor.getInt(this)
                }
            }
            cursor.getColumnIndex("consumeType").apply {
                if (this >= 0) {
                    accountBean.consumeType =
                        if (cursor.getInt(this) == 0) ConsumeType.EXPENSE else ConsumeType.INCOME
                }
            }
            cursor.getColumnIndex("money").apply {
                if (this >= 0) {
                    accountBean.money = cursor.getFloat(this)
                }
            }
            dataList.add(accountBean)
        }
        return dataList
    }

    /**
     * 向记账表中插入一条数据
     */
    fun insertItemToAccountTb(bean: AccountBean) {
        val contentValues = ContentValues()
        contentValues.put("typename", bean.typename)
        contentValues.put("sImageId", bean.sImageId)
        contentValues.put("beizhu", bean.beizhu)
        contentValues.put("money", bean.money)
        contentValues.put("time", bean.time)
        contentValues.put("year", bean.year)
        contentValues.put("month", bean.month)
        contentValues.put("day", bean.day)
        contentValues.put("consumeType", bean.consumeType.value)
        db.insert("accounttb", null, contentValues)
    }

    /**
     * 获取某一月的支出或者收入的总金额
     */
    fun getSumMoneyOneMonth(year: Int, month: Int, consumeType: ConsumeType): Float {
        var total = 0.0f
        val sql = "select sum(money) from accounttb where year=? and month=? and consumeType=?"
        val cursor = db.rawQuery(
            sql,
            arrayOf(year.toString(), month.toString(), consumeType.value.toString())
        )
        if (cursor.moveToNext()) {
            cursor.getColumnIndex("money").apply {
                if (this >= 0) {
                    total = cursor.getFloat(this)
                }
            }
        }
        return total
    }
}