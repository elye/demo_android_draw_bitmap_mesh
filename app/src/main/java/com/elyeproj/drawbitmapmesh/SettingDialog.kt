package com.elyeproj.drawbitmapmesh

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_dialog_setting.*

class SettingDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_open.setOnClickListener {

            val column = edit_column.text.toString().toInt()
            val row = edit_row.text.toString().toInt()

            if (column < 1 || column > 99) {
                Toast.makeText(this.context, "Column value invalid", Toast.LENGTH_LONG).show()
            } else if (row < 1 || row > 99) {
                Toast.makeText(this.context, "Row value invalid", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this.context, DrawBitmapMeshActivity::class.java)
                intent.putExtra(DrawBitmapMeshActivity.ARG_COLUMN, column)
                intent.putExtra(DrawBitmapMeshActivity.ARG_ROW, row)
                intent.putExtra(DrawBitmapMeshActivity.ARG_COLOR, checkbox_color.isChecked)
                intent.putExtra(DrawBitmapMeshActivity.ARG_TRANSPARENT, checkbox_transparent.isChecked)
                startActivity(intent)
                dismiss()
            }
        }
    }
}
