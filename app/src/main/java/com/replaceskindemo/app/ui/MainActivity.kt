package com.replaceskindemo.app.ui

import android.app.Activity
import android.os.Bundle
import com.replaceskindemo.app.R
import com.replaceskindemo.app.skin.SkinManager


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SkinManager.getDefault().initSkinLayoutFactory(layoutInflater)

        setContentView(R.layout.activity_main)
    }
}