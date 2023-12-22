package com.example.homework5.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.homework5.data.db.HwDatabase

object ServiceLocator {

    private var dbInstance: HwDatabase? = null

    private var sharedPref: SharedPreferences? = null

    fun initData(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, HwDatabase::class.java, "homework.db")
            .build()

        sharedPref = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }

    fun getDbInstance(): HwDatabase {
        return dbInstance ?: throw IllegalStateException("Db is not initialized")
    }

    fun getSharedPreferencesInstance(): SharedPreferences {
        return sharedPref ?: throw IllegalStateException("Preferences is not initialized")
    }

}