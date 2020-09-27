package com.urban.volumeapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

private const val DEFAULT_VOLUME_POWER = 50
private const val DEFAULT_VOLUME_SCALE = 10
private const val DEFAULT_VOLUME_COLOR = Color.BLUE

class VolumeControlView(context: Context, attrs: AttributeSet) : View(context, attrs)
{
    private var mVolumePower = DEFAULT_VOLUME_POWER
    private var mVolumeScale = DEFAULT_VOLUME_SCALE
    private var mVolumeColor = DEFAULT_VOLUME_COLOR
    private var mBarWidth    =  0.3
    private var mPaddingBot   = 0.97

    private var mViewHeight = this.height

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    interface OnVolumePowerChangeListener
    {
        fun onVolumePowerChange(volume : Int)
    }

    private var onVolumeChangeListener : OnVolumePowerChangeListener? = null

    fun setOnVolumeChangeListener(l : OnVolumePowerChangeListener)
    {
        onVolumeChangeListener = l
    }

    fun setVolumeScale(scale: Int)
    {
        mVolumeScale = scale
        invalidate()
    }

    fun setVolumePower(scale: Int)
    {
        mVolumePower = scale
        invalidate()
    }

    init
    {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight/2)
        mViewHeight = measuredHeight
    }

    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)
        drawVolumeCells(canvas)
    }

    private fun drawVolumeCells(canvas: Canvas)
    {
        val viewHeight = measuredHeight - (measuredHeight * 0.1)
        val viewWidth = measuredWidth
        val cellHeight = (viewHeight / mVolumeScale)

        paint.style = Paint.Style.FILL
        paint.strokeWidth = cellHeight.toFloat() / 2

        for (i in 1 until mVolumeScale +1 )
        {
            if(((i).toFloat() / (mVolumeScale).toFloat() * 100) > (100 - mVolumePower))
                paint.color = mVolumeColor
            else
                paint.color = Color.GRAY

            canvas.drawLine((viewWidth * mBarWidth).toFloat(), (i * cellHeight).toFloat(), (viewWidth * (1-mBarWidth)).toFloat(), ((i * cellHeight)).toFloat(), paint);
        }

        paint.color = Color.GRAY
        paint.textSize = 30F
        canvas.drawText("Volume set at:$mVolumePower%", (viewWidth * 0.3).toFloat(),
            (mViewHeight * mPaddingBot).toFloat(), paint)
    }

    private fun setupAttributes(attrs: AttributeSet?)
    {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.VolumeControlView,
            0, 0)

        mVolumePower = typedArray.getInt(R.styleable.VolumeControlView_volumePower, DEFAULT_VOLUME_POWER)
        mVolumeScale = typedArray.getInt(R.styleable.VolumeControlView_volumeScale, DEFAULT_VOLUME_SCALE)
        mVolumeColor = typedArray.getColor(R.styleable.VolumeControlView_volumeColor, DEFAULT_VOLUME_COLOR)

        typedArray.recycle()
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean
    {
        if (ev.action == MotionEvent::ACTION_DOWN.get() || ev.action == MotionEvent.ACTION_MOVE)
        {
            val y = ev.y

            val tempp = 100 - (y / mViewHeight*100).toInt()
            if (tempp in 0..100)
                mVolumePower = tempp
            invalidate()
            onVolumeChangeListener?.onVolumePowerChange(mVolumePower)
            return true
        }
        return false
    }

    override fun onSaveInstanceState(): Parcelable
    {
        val bundle = Bundle()
        bundle.putInt("volumePwr", mVolumePower)
        bundle.putInt("volumeScale", mVolumeScale)
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable)
    {
        var viewState = state
        if (viewState is Bundle)
        {
            mVolumePower = viewState.getInt("volumePwr", 50)
            mVolumeScale = viewState.getInt("volumeScale", 10)
            viewState    = viewState.getParcelable("superState")!!
        }
        super.onRestoreInstanceState(viewState)
    }
}

