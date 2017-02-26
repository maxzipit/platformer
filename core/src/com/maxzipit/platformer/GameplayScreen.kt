package com.maxzipit.platformer

import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.maxzipit.platformer.utils.Assets


class GameplayScreen : ScreenAdapter() {

    internal lateinit var batch: SpriteBatch
    internal lateinit var sbatch: ShapeRenderer
    internal var levelEndOverlayStartTime: Long = 0

    internal lateinit var level: Level

    override fun show() {
        val am = AssetManager()
        Assets.instance.init(am)

        batch = SpriteBatch()
        sbatch = ShapeRenderer()
        sbatch.setAutoShapeType(true)

        startNewLevel()
    }

    private fun onMobile(): Boolean {
        return Gdx.app.type == ApplicationType.Android || Gdx.app.type == ApplicationType.iOS
    }

    override fun resize(width: Int, height: Int) {
        level.viewport.update(width, height, true)
    }

    override fun dispose() {
        Assets.instance.dispose()
    }

    override fun render(delta: Float) {
        val beginTime = System.currentTimeMillis()

        level.update(delta)


        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.graphics.setVSync(true)

        level.render(batch, sbatch)

        renderLevelEndOverlays(batch)

        val timeDiff = System.currentTimeMillis() - beginTime
        val sleepTime = (1000 / 60 - timeDiff)
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime)
            } catch (e: InterruptedException) {
            }

        }
//        println(Gdx.graphics.framesPerSecond)
    }

    private fun renderLevelEndOverlays(batch: SpriteBatch) {
//        if (level!!.gameOver) {
//
//            if (levelEndOverlayStartTime == 0) {
//                levelEndOverlayStartTime = TimeUtils.nanoTime()
//            }
//
//            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
//                levelEndOverlayStartTime = 0
//                levelFailed()
//            }
//        } else if (level!!.victory) {
//            if (levelEndOverlayStartTime == 0) {
//                levelEndOverlayStartTime = TimeUtils.nanoTime()
//                victoryOverlay!!.init()
//            }
//
//            victoryOverlay!!.render(batch)
//            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
//                levelEndOverlayStartTime = 0
//                levelComplete()
//            }
//
//        }
    }

    private fun startNewLevel() {

        level = Level.debugLevel()

        resize(Gdx.graphics.width, Gdx.graphics.height)
    }

    fun levelComplete() {
        startNewLevel()
    }

    fun levelFailed() {
        startNewLevel()
    }

    companion object {

        val TAG = GameplayScreen::class.java.name
    }
}
