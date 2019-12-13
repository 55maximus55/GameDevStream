package ru.maximus55.projectname.components

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray

class UpdateCameraSystem(val ppm: Float) : EntitySystem() {

    private lateinit var entities: ImmutableArray<Entity>

    private val cameraMapper = ComponentMapper.getFor(OrthographicCameraComponent::class.java)
    private val transformMapper = ComponentMapper.getFor(TransformComponent::class.java)
    private val targetMapper = ComponentMapper.getFor(TargetEntityComponent::class.java)

    override fun addedToEngine(engine: Engine?) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(OrthographicCameraComponent::class.java, TransformComponent::class.java, TargetEntityComponent::class.java).get())
        }
    }

    override fun update(deltaTime: Float) {
        for (i in entities) {
            val camera = cameraMapper.get(i)
            val transform = transformMapper.get(i)
            val target = targetMapper.get(i)

            transform.position = target.targetEntity.getComponent(Box2dBodyComponent::class.java).body.position.cpy().scl(ppm)

            camera.camera.position.x = transform.position.x
            camera.camera.position.y = transform.position.y
            camera.camera.update()
        }
    }

}