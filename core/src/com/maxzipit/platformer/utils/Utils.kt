package com.maxzipit.platformer.utils

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.MathUtils.nanoToSec
import com.badlogic.gdx.utils.TimeUtils


/**
 * Utils
 *
 * Created by maks on 07/12/16.
 */
fun drawTextureRegion(batch: SpriteBatch, region: TextureRegion, position: Vector2) {
    drawTextureRegion(batch, region, position.x, position.y)
}

fun drawTextureRegion(batch: SpriteBatch, region: TextureRegion, position: Vector2, offset: Vector2) {
    drawTextureRegion(batch, region, position.x - offset.x, position.y - offset.y)
}

fun drawTextureRegion(batch: SpriteBatch, region: TextureRegion, x: Float, y: Float, scale: Float = 1f) {
    batch.draw(
            region.texture,
            x,
            y,
            0f,
            0f,
            region.regionWidth.toFloat(),
            region.regionHeight.toFloat(),
            scale,
            scale,
            0f,
            region.regionX,
            region.regionY,
            region.regionWidth,
            region.regionHeight,
            false,
            false)
}

fun secondsSince(timeNanos: Long): Float {
    return MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeNanos)
}
