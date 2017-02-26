package com.maxzipit.platformer

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.maxzipit.platformer.enteties.Hero
import com.maxzipit.platformer.enteties.Platform


class Level {
    var gameOver: Boolean = false
    var victory: Boolean = false
    var viewport: Viewport
    var score: Int = 0

    lateinit var hero: Hero
    lateinit var platforms: Array<Platform>

    init {
        viewport = ExtendViewport(480f, 480f)

        gameOver = false
        victory = false
        score = 0


    }

    fun update(delta: Float) {
        hero.update(delta, platforms)

    }

    fun render(batch: SpriteBatch, sbatch: ShapeRenderer) {

        viewport.apply()

        batch.projectionMatrix = viewport.camera.combined
        batch.begin()

        hero.render(batch)

        for (platform in platforms) {
            platform.render(batch)
        }

        batch.end()

        sbatch.projectionMatrix = viewport.camera.combined
        sbatch.begin()
        hero.renderTest(sbatch)
        for (platform in platforms) {
            platform.renderTest(sbatch)
        }
        sbatch.end()
    }

    private fun initializeDebugLevel() {
        hero = Hero(Vector2(20f, 200f), this)

        platforms = Array<Platform>()
        platforms.add(Platform(10f, 50f, 200f, 40f))

    }

    companion object {

        val TAG = Level::class.java.name

        fun debugLevel(): Level {
            val level = Level()
            level.initializeDebugLevel()
            return level
        }
    }
}
