package ru.maximus55.projectname.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.physics.box2d.World

class Box2dWorldSystem(val world: World) : EntitySystem() {

    override fun update(deltaTime: Float) {
        world.step(deltaTime, 10, 10)
    }

}