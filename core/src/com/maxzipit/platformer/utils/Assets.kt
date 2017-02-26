package com.maxzipit.platformer.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.sun.media.jfxmediaimpl.MediaDisposer
import com.badlogic.gdx.utils.Array
import java.io.File


/**
 * Assets Loader
 *
 * Created by maks on 06/12/16.
 */
class Assets private constructor() : AssetErrorListener, MediaDisposer.Disposable {

    private lateinit var assetManager: AssetManager

    lateinit var heroAssets: HeroAsset
    lateinit var platformAssets: PlatformAsset

    fun init(assetManager: AssetManager) {
        this.assetManager = assetManager
        assetManager.setErrorListener(this)
        assetManager.load("images"+ File.separator +"sheet.pack.atlas", TextureAtlas::class.java)
        assetManager.load("images"+ File.separator +"characters.pack.atlas", TextureAtlas::class.java)

        assetManager.finishLoading()

        val sheetAtlas: TextureAtlas = assetManager.get("images"+ File.separator +"sheet.pack.atlas")
        val heroesAtlas: TextureAtlas = assetManager.get("images"+ File.separator +"characters.pack.atlas")
        heroAssets = HeroAsset(heroesAtlas)
        platformAssets = PlatformAsset(sheetAtlas)
//        platformAssets = PlatformAssets(atlas)
//        bulletAssets = BulletAssets(atlas)
//        enemyAssets = EnemyAssets(atlas)
//        explosionAssets = ExplosionAssets(atlas)
//        powerupAssets = PowerupAssets(atlas)
//        exitPortalAssets = ExitPortalAssets(atlas)
//        onscreenControlsAssets = OnscreenControlsAssets(atlas)
    }


    private object Holder { val INSTANCE = Assets() }

    companion object {
        val instance: Assets by lazy { Holder.INSTANCE }
    }


    inner class HeroAsset(atlas: TextureAtlas) {
        val standingRight : TextureAtlas.AtlasRegion = atlas.findRegion("herowalk-4")

        var walkingFrames: Array<TextureAtlas.AtlasRegion> = Array<TextureAtlas.AtlasRegion>()

        init {
            walkingFrames.add(atlas.findRegion("herowalk-4"))
            walkingFrames.add(atlas.findRegion("herowalk-1"))
            walkingFrames.add(atlas.findRegion("herowalk-2"))
            walkingFrames.add(atlas.findRegion("herowalk-3"))
        }

        val walkAnimation : Animation = Animation(0.25f, walkingFrames, Animation.PlayMode.LOOP)
    }

    inner class PlatformAsset(atlas: TextureAtlas) {
        var groundGreen: TextureAtlas.AtlasRegion = atlas.findRegion("groundgreen")
        /*init {
            val region: TextureAtlas.AtlasRegion = atlas.findRegion("groundgreen")
            groundGreenPatch = NinePatch(region, 0, 0, 10, 0)
        }*/
    }

    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        Gdx.app.error("ASSSETS", "Couldn't load asset: " + asset?.fileName, throwable)
    }

    override fun dispose() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}