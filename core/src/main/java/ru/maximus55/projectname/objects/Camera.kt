package ru.maximus55.projectname.objects

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.viewport.Viewport
import ru.maximus55.projectname.components.OrthographicCameraComponent
import ru.maximus55.projectname.components.TargetEntityComponent
import ru.maximus55.projectname.components.TransformComponent

class Camera {

    fun create(targetEntity: Entity, viewport: Viewport) : Entity {
        return Entity().apply {
            add(TargetEntityComponent(targetEntity))
            add(TransformComponent())

            val cameraComponent = OrthographicCameraComponent()
            viewport.camera = cameraComponent.camera
            add(cameraComponent)
        }
    }

}