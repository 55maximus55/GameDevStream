package ru.maximus55.projectname.objects

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.physics.box2d.World
import ru.maximus55.projectname.components.Box2dBodyComponent
import ru.maximus55.projectname.components.SpriteComponent
import ru.maximus55.projectname.utils.createCircleBody

class Player {

    fun create(world: World) : Entity {
        return Entity().apply {
            add(Box2dBodyComponent(createCircleBody(world)))
            add(SpriteComponent(Sprite(Texture("textures/player.png"))))
        }
    }

//    val speed = 7f
//
//    fun update() {
//        val speed = Vector2()
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            speed.y += 1f
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            speed.y -= 1f
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            speed.x -= 1f
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            speed.x += 1f
//        }
//        speed.scl(this.speed)
//
//        body.linearVelocity = speed
//    }

}