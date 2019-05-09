package com.elyeproj.drawbitmapmesh

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent.ACTION_DOWN
import android.view.View
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP


class SimpleDrawBitmapMeshView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.person_picture)
    }

    private val firstX by lazy { 0f + paddingLeft }
    private val firstY by lazy { 0f + paddingTop }
    private var secondX = 0f
    private var secondY = 0f
    private val thirdX by lazy { width.toFloat() - paddingRight }
    private val thirdY by lazy { height.toFloat() - paddingBottom }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        secondX = width/2f
        secondY = height/2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmapMesh(bitmap, 2, 2,
            floatArrayOf(
                firstX, firstY, secondX, firstY, thirdX, firstY,
                firstX, secondY, secondX, secondY, thirdX, secondY,
                firstX, thirdY, secondX, thirdY, thirdX, thirdY),
            0,
            intArrayOf(
                Color.RED, Color.GREEN, Color.BLUE,
                Color.CYAN, Color.MAGENTA, Color.DKGRAY,
                Color.BLACK, Color.YELLOW, Color.WHITE),
            0, null)

        drawMeshLines(canvas)
    }

    private fun drawMeshLines(canvas: Canvas) {
        canvas.drawRect(firstX, firstY, firstX, thirdY, projectResources.paintLight)
        canvas.drawLine(secondX, firstY, secondX, thirdY, projectResources.paintLight)
        canvas.drawLine(thirdX, firstY, thirdX, thirdY, projectResources.paintLight)

        canvas.drawLine(firstX, firstY, thirdX, firstY, projectResources.paintLight)
        canvas.drawLine(firstX, secondY, thirdX, secondY, projectResources.paintLight)
        canvas.drawLine(firstX, thirdY, thirdX, thirdY, projectResources.paintLight)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            ACTION_DOWN, ACTION_MOVE, ACTION_UP -> {
                secondX = event.x
                secondY = event.y
                invalidate()
            }
        }

        return true
    }
}
