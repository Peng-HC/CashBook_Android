package com.phc.accountapp

import android.app.Application
import com.phc.accountapp.db.DBManager

/**
 * 表示全局应用的类
 */
class UniteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DBManager.initDB(applicationContext)
    }
}