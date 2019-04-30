package com.elyeproj.drawbitmapmesh

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectResources = ProjectResources(resources)
        setContentView(R.layout.activity_main)
    }
}
