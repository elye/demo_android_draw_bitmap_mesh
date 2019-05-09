package com.elyeproj.drawbitmapmesh

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectResources = ProjectResources(resources)
        setContentView(R.layout.activity_main)

        btn_simple_draw.setOnClickListener {
            startActivity(Intent(this, SimpleDrawBitmapMeshActivity::class.java))
        }

        btn_draw.setOnClickListener {
            startActivity(Intent(this, DrawBitmapMeshActivity::class.java))
        }
    }
}
