package com.elyeproj.drawbitmapmesh

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SETTING_DIALOG_TAG = "dialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectResources = ProjectResources(resources)
        setContentView(R.layout.activity_main)

        btn_simple_draw.setOnClickListener {
            startActivity(Intent(this, SimpleDrawBitmapMeshActivity::class.java))
        }

        btn_draw.setOnClickListener {
            SettingDialog().show(supportFragmentManager.beginTransaction(), SETTING_DIALOG_TAG)
        }
    }
}
