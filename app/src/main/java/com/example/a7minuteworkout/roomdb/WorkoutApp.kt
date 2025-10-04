package com.example.a7minuteworkout.roomdb

import android.app.Application

class WorkoutApp: Application()  {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}