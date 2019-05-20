package com.elyeproj.drawbitmapmesh

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import kotlin.math.pow
import kotlin.random.Random


class DrawBitmapMeshView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val MESH_WIDTH = 6
        const val MESH_HEIGHT = 10
        const val FULL_COLOR = 256
        const val HALF_COLOR = 128
        private val colorRandom = Random(0)

    }

    private var meshWidth = MESH_WIDTH
    private var mestHeight = MESH_HEIGHT
    private var hasColor = false
    private var isTransparent = false

    private val bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.person_picture)
    }

    private lateinit var coordinates: List<Pair<Float, Float>>
    private var colors: List<Int>? = null

    fun setup(column: Int, row: Int, randomColor: Boolean, transparent: Boolean) {
        meshWidth = column
        mestHeight = row
        hasColor = randomColor
        isTransparent = transparent
        generateCoordinates()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        generateCoordinates()
    }

    private fun generateCoordinates() {
        coordinates = generateCoordinate(
            meshWidth,
            mestHeight,
            width,
            height,
            paddingStart,
            paddingEnd,
            paddingTop,
            paddingBottom
        )

        if (hasColor) {
                colors = (1..(meshWidth + 1) * (mestHeight + 1)).map {
                        Color.argb(if (isTransparent) HALF_COLOR else FULL_COLOR - 1,
                        colorRandom.nextInt(FULL_COLOR),
                        colorRandom.nextInt(FULL_COLOR),
                        colorRandom.nextInt(FULL_COLOR))
                }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmapMesh(
            bitmap, meshWidth, mestHeight, coordinates.flatMap { listOf(it.first, it.second) }.toFloatArray(),
            0, colors?.toIntArray(), 0, null
        )

        drawCoordinates(canvas)
        drawLines(canvas)
    }

    private fun drawCoordinates(canvas: Canvas) {
        coordinates.forEach {
            canvas.drawPoint(it.first, it.second, projectResources.paintDark)
        }
    }

    private fun drawLines(canvas: Canvas) {

        coordinates.forEachIndexed { index, pair ->
            // Draw horizontal line with next column
            if (((index + 1) % (meshWidth + 1)) != 0) {
                val nextCoordinate = coordinates[index + 1]
                drawLine(canvas, pair, nextCoordinate)
            }

            // Draw horizontal line with next row
            if (((index < (meshWidth + 1) * mestHeight))) {
                val nextCoordinate = coordinates[index + meshWidth + 1]
                drawLine(canvas, pair, nextCoordinate)
            }
        }
    }

    private fun drawLine(canvas: Canvas, pair: Pair<Float, Float>, nextCoordinate: Pair<Float, Float>) {
        canvas.drawLine(
            pair.first, pair.second,
            nextCoordinate.first, nextCoordinate.second,
            projectResources.paintLight
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            ACTION_DOWN, ACTION_UP, ACTION_MOVE -> {
                val sorted = coordinates.sortedBy { (it.first - event.x).pow(2) + (it.second - event.y).pow(2) }
                val selectedIndex = coordinates.indexOf(sorted[0])
                coordinates =
                    coordinates.mapIndexed { index, pair -> if (index == selectedIndex) (event.x to event.y) else pair }
                invalidate()
                return true
            }
        }

        return false
    }

    private fun generateCoordinate(
        col: Int, row: Int, width: Int, height: Int,
        paddingStart: Int = 0, paddingEnd: Int = 0,
        paddingTop: Int = 0, paddingBottom: Int = 0
    ): List<Pair<Float, Float>> {

        val widthSlice = (width - (paddingStart + paddingEnd)) / (col)
        val heightSlice = (height - (paddingTop + paddingBottom)) / (row)

        val coordinates = mutableListOf<Pair<Float, Float>>()

        for (y in 0..row) {
            for (x in 0..col) {
                coordinates.add(
                    Pair(
                        (x * widthSlice + paddingStart).toFloat(),
                        (y * heightSlice + paddingTop).toFloat()
                    )
                )
            }
        }

        return coordinates
    }
}
