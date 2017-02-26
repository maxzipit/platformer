package com.maxzipit.platformer

import com.badlogic.gdx.Game
import com.udacity.gamedev.gigagal.GameplayScreen

class Platformer : Game() {

    override fun create() {
        setScreen(GameplayScreen())
    }
}
