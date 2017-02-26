package com.maxzipit.platformer.enteties

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import com.maxzipit.platformer.utils.*
import com.maxzipit.platformer.Level

/**
 * Hero class (main personage)
 *
 * Created by maks on 07/12/16.
 */

/*
 * HERO CONSTANTS
 */
val HERO_SCALE = 1f
val HERO_HEIGHT = 25f
val HERO_SPEED = 100f
val HERO_STANCE_WIDTH = 18f

class Hero (val spawnLocation: Vector2, level: Level) {

    private val lives = 3
    private var position: Vector2 = spawnLocation
    private var lastFramePosition: Vector2 = Vector2(0f,0f)
    private var velocity: Vector2 = Vector2(0f,0f)

    private var jumpState: JumpState = JumpState.FALLING
    private var facing: Direction = Direction.LEFT
    private var walkState: WalkState = WalkState.NOT_WALKING
    private var walkStartTime: Long = TimeUtils.nanoTime()

    private fun respawn() {
        position.set(spawnLocation)
        lastFramePosition.set(spawnLocation)
        velocity.setZero()
        jumpState = JumpState.FALLING
        facing = Direction.RIGHT
        walkState = WalkState.NOT_WALKING

        walkStartTime = TimeUtils.nanoTime()
    }

    fun update(delta: Float, platforms: Array<Platform>) {
        // Save Current(Last Frame) Position
        lastFramePosition.set(position)

        // Add gravity to velocity
        velocity.add(GRAVITY)

        // change New Frame Position
        position.mulAdd(velocity, delta)

        //Gdx.app.log("HERO", "New Lfp = ${lastFramePosition.y} | pos = ${position.y} " )
        checkFallingState(platforms)

        // Check move controls
        if(jumpState == JumpState.GROUNDED) {
            val left: Boolean = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)
            val right: Boolean = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)
            when {
                left && !right -> moveLeft(delta)
                right && !left -> moveRight(delta)
                else -> walkState = WalkState.NOT_WALKING
            }
        }
    }


    fun render(batch: SpriteBatch) {

        val animTime = secondsSince(walkStartTime)

//        if(walkState == WalkState.WALKING)
//            Gdx.app.log("HERO", "WalkState = ${walkState} | facing = $facing | jumpState $jumpState " +
//                    "\n StartTime = ${walkStartTime} | Anim time = $animTime | index = ${Assets.instance.heroAssets.walkAnimation.getKeyFrameIndex(animTime)} " +
//                    "\n pos.x = ${position.x} | pos.y = ${position.y}" )

        val region: TextureRegion = when {
            walkState == WalkState.WALKING && facing == Direction.RIGHT && jumpState == JumpState.GROUNDED -> Assets.instance.heroAssets.walkAnimation.getKeyFrame(animTime)
            walkState == WalkState.WALKING && facing == Direction.LEFT  && jumpState == JumpState.GROUNDED -> {
                val tr = Assets.instance.heroAssets.walkAnimation.getKeyFrame(animTime)
//                tr.flip(false, true)
                tr
            }
            jumpState == JumpState.FALLING && facing == Direction.RIGHT -> Assets.instance.heroAssets.standingRight
            else -> Assets.instance.heroAssets.standingRight
        }

        drawTextureRegion(batch, region, position.x, position.y, HERO_SCALE)

    }

    private fun moveLeft(delta: Float) {
        if(jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING)
            walkStartTime = TimeUtils.nanoTime()
        walkState = WalkState.WALKING
        facing = Direction.LEFT
        position.x -= delta * HERO_SPEED
    }

    private fun moveRight(delta: Float) {
        if(jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING)
            walkStartTime = TimeUtils.nanoTime()
        walkState = WalkState.WALKING
        facing = Direction.RIGHT
        position.x += delta * HERO_SPEED
    }

    private fun checkFallingState(platforms: Array<Platform>) {

        if (jumpState != JumpState.JUMPING) {
            if(jumpState != JumpState.RECOILING)
                jumpState = JumpState.FALLING

            for (platform: Platform in platforms) {
                if (landedOnPlatform(platform)) {
                    jumpState = JumpState.GROUNDED
                    velocity.setZero()
                    position.y = platform.top
                    break
                }
            }
        }

    }

    private fun landedOnPlatform (platform: Platform): Boolean {
        var leftFootIn = false
        var rightFootIn = false
        var straddle = false

        //Gdx.app.log("HERO", "Lfp = ${lastFramePosition.y} | pos = ${position.y} | top = ${platform.top} " )

        if (lastFramePosition.y >= platform.top &&
                position.y < platform.top) {


            val leftFoot = position.x /*- HERO_STANCE_WIDTH / 2*/
            val rightFoot = position.x + HERO_STANCE_WIDTH

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot)
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot)
            straddle = (platform.left > leftFoot && platform.right < rightFoot)
        }
        return leftFootIn || rightFootIn || straddle

    }

    fun  renderTest(sbatch: ShapeRenderer) {
        sbatch.color = Color.RED
        sbatch.rect(position.x, position.y, HERO_STANCE_WIDTH, HERO_HEIGHT)
        sbatch.color = Color.GOLD
        sbatch.rect(position.x-1,position.y-1,2f,2f)
    }
}