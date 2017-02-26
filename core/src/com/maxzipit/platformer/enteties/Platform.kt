package com.maxzipit.platformer.enteties

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.maxzipit.platformer.utils.Assets

/**
 * Platform Class
 *
 * Created by maks on 19/12/16.
 */
class Platform (val left: Float, val top: Float, val width: Float, val height: Float){

    var identifier: String = ""
    val right = left + width
    val bottom = top - height

    fun render(batch: SpriteBatch) {
        //Assets.instance.platformAssets.groundGreenPatch.draw(batch, left-1, bottom-1, width+2, height+2)
        val region = Assets.instance.platformAssets.groundGreen

        val texture = region.texture
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge)
        //val region: TextureRegion = TextureRegion(texture)

        batch.draw(
                texture,
                left,
                bottom,
                0f,
                0f,
                width,
                height,
                1f,
                1f,
                0f,
                region.regionX,
                region.regionY,
                region.regionWidth,
                region.regionHeight,
                false,
                false)

    }

    fun  renderTest(sbatch: ShapeRenderer) {
        sbatch.color = Color.GREEN
        sbatch.rect(left, bottom, width, height)
        sbatch.color = Color.GOLD
        sbatch.rect(left-1, bottom-1, 2f, 2f)
    }
}