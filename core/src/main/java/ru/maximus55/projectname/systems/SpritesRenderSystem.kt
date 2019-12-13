package ru.maximus55.projectname.systems

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.Batch
import ktx.app.use
import ru.maximus55.projectname.components.OrthographicCameraComponent
import ru.maximus55.projectname.components.SpriteComponent

class SpritesRenderSystem(val batch: Batch, val cameraEntity: Entity) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>

    private val spriteMapper = ComponentMapper.getFor(SpriteComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(SpriteComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        batch.apply {
            projectionMatrix = cameraEntity.getComponent(OrthographicCameraComponent::class.java).camera.combined
            use {
                for (i in entities) {
                    val sprite = spriteMapper.get(i).sprite
                    sprite.draw(batch)
                }
            }
        }
    }

}