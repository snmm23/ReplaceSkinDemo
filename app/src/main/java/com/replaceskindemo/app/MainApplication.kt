package com.replaceskindemo.app

import android.app.Application
import com.replaceskindemo.app.skin.SkinConfiguration
import com.replaceskindemo.app.skin.SkinManager
import com.replaceskindemo.app.skin.exception.SkinException


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        try {
            SkinManager.getDefault().config(SkinConfiguration().setSupport(true))
            SkinManager.getDefault().loadSkinByPackageName(applicationContext, "com.replaceskindemo.resource")
        } catch (e: SkinException) {
            e.printStackTrace()
        }
    }
}