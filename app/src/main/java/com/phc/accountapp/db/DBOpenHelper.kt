package com.phc.accountapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.phc.accountapp.R

class DBOpenHelper(
    private val context: Context,
) : SQLiteOpenHelper(context, "account.db", null, 1) {
    // 数据库第一次创建的时候才会执行
    override fun onCreate(db: SQLiteDatabase?) {
        // 创建表的SQL语句
        val typeSql =
            "create table typetb(" +
                    "id integer primary key autoincrement," +
                    "typeName varchar(10)," +
                    "unselectedImageId integer," +
                    "selectedImageId integer," +
                    "consumeType integer)"
        db?.execSQL(typeSql)
        insertType(db)
        //创建记账表
        val accountSql =
            "create table accounttb(id integer primary key autoincrement,typename varchar(10),selectedImageId integer,beizhu varchar(80),money float," +
                    "time varchar(60),year integer,month integer,day integer,consumeType integer)"
        db?.execSQL(accountSql)
    }

    // 插入数据
    private fun insertType(db: SQLiteDatabase?) {
        if (db == null) {
            return
        }
        // 向typetb表当中插入元素
        val sql =
            "insert into typetb (typename,unselectedImageId,selectedImageId,consumeType) values (?,?,?,?)"
        db.execSQL(
            sql,
            arrayOf<Any>("其他", R.mipmap.ic_qita, R.mipmap.ic_qita_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("餐饮", R.mipmap.ic_canyin, R.mipmap.ic_canyin_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("交通", R.mipmap.ic_jiaotong, R.mipmap.ic_jiaotong_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("购物", R.mipmap.ic_gouwu, R.mipmap.ic_gouwu_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("服饰", R.mipmap.ic_fushi, R.mipmap.ic_fushi_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>(
                "日用品",
                R.mipmap.ic_riyongpin,
                R.mipmap.ic_riyongpin_fs,
                ConsumeType.EXPENSE
            )
        )
        db.execSQL(
            sql,
            arrayOf<Any>("娱乐", R.mipmap.ic_yule, R.mipmap.ic_yule_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("零食", R.mipmap.ic_lingshi, R.mipmap.ic_lingshi_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("烟酒茶", R.mipmap.ic_yanjiu, R.mipmap.ic_yanjiu_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("学习", R.mipmap.ic_xuexi, R.mipmap.ic_xuexi_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("医疗", R.mipmap.ic_yiliao, R.mipmap.ic_yiliao_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("住宅", R.mipmap.ic_zhufang, R.mipmap.ic_zhufang_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>(
                "水电煤",
                R.mipmap.ic_shuidianfei,
                R.mipmap.ic_shuidianfei_fs,
                ConsumeType.EXPENSE
            )
        )
        db.execSQL(
            sql,
            arrayOf<Any>("通讯", R.mipmap.ic_tongxun, R.mipmap.ic_tongxun_fs, ConsumeType.EXPENSE)
        )
        db.execSQL(
            sql,
            arrayOf<Any>(
                "人情往来",
                R.mipmap.ic_renqingwanglai,
                R.mipmap.ic_renqingwanglai_fs,
                ConsumeType.EXPENSE
            )
        )

        db.execSQL(sql, arrayOf<Any>("其他", R.mipmap.in_qt, R.mipmap.in_qt_fs, ConsumeType.INCOME))
        db.execSQL(
            sql,
            arrayOf<Any>("薪资", R.mipmap.in_xinzi, R.mipmap.in_xinzi_fs, ConsumeType.INCOME)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("奖金", R.mipmap.in_jiangjin, R.mipmap.in_jiangjin_fs, ConsumeType.INCOME)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("借入", R.mipmap.in_jieru, R.mipmap.in_jieru_fs, ConsumeType.INCOME)
        )
        db.execSQL(
            sql,
            arrayOf<Any>("收债", R.mipmap.in_shouzhai, R.mipmap.in_shouzhai_fs, ConsumeType.INCOME)
        )
        db.execSQL(
            sql,
            arrayOf<Any>(
                "利息收入",
                R.mipmap.in_lixifuji,
                R.mipmap.in_lixifuji_fs,
                ConsumeType.INCOME
            )
        )
        db.execSQL(
            sql,
            arrayOf<Any>("投资回报", R.mipmap.in_touzi, R.mipmap.in_touzi_fs, ConsumeType.INCOME)
        )
        db.execSQL(
            sql,
            arrayOf<Any>(
                "二手交易",
                R.mipmap.in_ershoushebei,
                R.mipmap.in_ershoushebei_fs,
                ConsumeType.INCOME
            )
        )
        db.execSQL(
            sql,
            arrayOf<Any>("意外所得", R.mipmap.in_yiwai, R.mipmap.in_yiwai_fs, ConsumeType.INCOME)
        )
    }

    // 数据库版本更新时执行
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}