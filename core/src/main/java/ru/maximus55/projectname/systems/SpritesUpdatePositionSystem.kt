package ru.maximus55.projectname.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import ru.maximus55.projectname.components.Box2dBodyComponent
import ru.maximus55.projectname.components.SpriteComponent

class SpritesUpdatePositionSystem(val ppm: Float) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>

    private val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)
    private val box2dBodyMapper = ComponentMapper.getFor(Box2dBodyComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SpriteComponent::class.java, Box2dBodyComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val sprite = spriteMapper.get(i).sprite
            val body = box2dBodyMapper.get(i).body

            sprite.setPosition(body.position.x * ppm - sprite.width / 2, body.position.y * ppm - sprite.height / 2)
        }
    }

}