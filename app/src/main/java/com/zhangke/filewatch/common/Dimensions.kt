package com.zhangke.awesomewidget.common

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

/**
 * Created by ZhangKe on 2020/11/28.
 */
/**
 * Represents DP or SP dimension
 */
inline class DensityIndependentPixel(val valueAndUnit: Long) {
    constructor(value: Float, unit: Int) : this(
        (unit.toLong() shl 32) or value.toRawBits().toLong()
    )

    /**
     * The unit of this dimension. One of the COMPLEX_UNIT_* constants in [TypedValue].
     */
    val unit: Int get() = (valueAndUnit shr 32).toInt()

    /**
     * The value of this dimension.
     */
    val value: Float get() = Float.fromBits(valueAndUnit.toInt())

    /**
     * Convert this dimension to px.
     */
    fun toPx(context: Context): Float {
        return TypedValue.applyDimension(unit, value, context.resources.displayMetrics)
    }

    /**
     * Convert this dimension to px and round to int.
     */
    fun toPxInt(context: Context): Int = toPx(context).roundToInt()
}

val Float.dp: DensityIndependentPixel
    get() = DensityIndependentPixel(
        this,
        TypedValue.COMPLEX_UNIT_DIP
    )

val Int.dp: DensityIndependentPixel get() = this.toFloat().dp

val Float.sp: DensityIndependentPixel
    get() = DensityIndependentPixel(
        this,
        TypedValue.COMPLEX_UNIT_SP
    )

val Int.sp: DensityIndependentPixel get() = this.toFloat().sp
