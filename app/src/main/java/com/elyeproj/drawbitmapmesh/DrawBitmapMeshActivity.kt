package com.elyeproj.drawbitmapmesh

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_draw_bitmap_mesh_view.*

class DrawBitmapMeshActivity : AppCompatActivity() {

    companion object {
        const val ARG_COLUMN = "ARG_COLUMN"
        const val ARG_ROW = "ARG_ROW"
        const val ARG_COLOR = "ARG_COLOR"
        const val ARG_TRANSPARENT = "ARG_TRANSPARENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_bitmap_mesh_view)

        view_drawbitmapmesh.setup(
            intent.getIntExtra(ARG_COLUMN, DrawBitmapMeshView.MESH_WIDTH),
            intent.getIntExtra(ARG_ROW, DrawBitmapMeshView.MESH_HEIGHT),
            intent.getBooleanExtra(ARG_COLOR,false),
            intent.getBooleanExtra(ARG_TRANSPARENT, false)
        )
    }
}
