package com.elyeproj.drawbitmapmesh

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.KeyEvent.ACTION_DOWN
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import android.view.View


class DrawBitmapMeshView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val MESH_WIDTH = 6
        const val MESH_HEIGHT = 10
    }

    private val bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.person_picture)
    }

    private lateinit var coordinates: List<Pair<Float, Float>>

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        coordinates = generateCoordinate(MESH_WIDTH, MESH_HEIGHT, width, height, paddingStart, paddingEnd, paddingTop, paddingBottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmapMesh(bitmap, MESH_WIDTH, MESH_HEIGHT, coordinates.flatMap { listOf(it.first, it.second) }.toFloatArray(),
            0,
            null, 0, null)

        drawCoordinates(canvas)
        drawLines(canvas)
    }

    private fun drawCoordinates(canvas: Canvas) {
        coordinates.forEach {
            canvas.drawPoint(it.first, it.second, projectResources.paintDark)
        }
    }

    private fun drawLines(canvas: Canvas) {

        coordinates.groupBy { it.first }.forEach {
            drawLines(canvas, it.value)
        }

        coordinates.groupBy { it.second }.forEach {
            drawLines(canvas, it.value)
        }
    }

    private fun drawLines(canvas: Canvas, coordinates: List<Pair<Float, Float>>) {
        var currentCoordinate : Pair<Float, Float>? = null

        coordinates.forEach {
            currentCoordinate?.let{ currentCoordinate ->
                canvas.drawLine(currentCoordinate.first, currentCoordinate.second,
                    it.first, it.second, projectResources.paintLight)
            }
            currentCoordinate = it
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            ACTION_DOWN, ACTION_MOVE, ACTION_UP -> {
                invalidate()
            }
        }

        return true
    }

    private fun generateCoordinate(
        col: Int, row: Int, width: Int, height: Int,
        paddingStart: Int = 0, paddingEnd: Int = 0,
        paddingTop: Int = 0, paddingBottom: Int = 0
    ): List<Pair<Float, Float>> {

        val widthSlice = (width - (paddingStart + paddingEnd)) / (col)
        val heightSlice = (height - (paddingTop + paddingBottom)) / (row)

        val coordinates = mutableListOf<Pair<Float, Float>>()

        for (y in 0 .. row) {
            for (x in 0 .. col) {
                coordinates.add(Pair(
                    (x * widthSlice + paddingStart).toFloat(),
                    (y * heightSlice + paddingTop).toFloat()))
            }
        }

        return coordinates
    }
}
