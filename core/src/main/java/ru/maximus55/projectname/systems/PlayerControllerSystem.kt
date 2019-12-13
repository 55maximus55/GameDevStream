package ru.maximus55.projectname.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import ru.maximus55.projectname.components.Box2dBodyComponent
import ru.maximus55.projectname.components.PlayerControllerComponent
import ru.maximus55.projectname.components.SpriteComponent

class PlayerControllerSystem : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>

    private val playerControllerMapper = ComponentMapper.getFor(PlayerControllerComponent::class.java)
    private val box2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SpriteComponent::class.java, Box2dBodyComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val playerController = playerControllerMapper.get(i)
            val body = box2dBodyMapper.get(i).body

            val control = Vector2().apply {
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    y += 1f
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    y -= 1f
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    x -= 1f
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    x += 1f
                }
                clamp(0f, 1f)
                scl(7f)
            }
            body.linearVelocity = control
        }
    }

}