package ru.maximus55.projectname.objects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

class Player(texture: Texture, val body: Body, val scale: Float) : Sprite(texture) {

    val speed = 7f

    fun update() {
        val speed = Vector2()
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speed.y += 1f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speed.y -= 1f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speed.x -= 1f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speed.x += 1f
        }
        speed.scl(this.speed)

        body.linearVelocity = speed

        x = body.position.x * scale - width / 2
        y = body.position.y * scale - height / 2
    }

}