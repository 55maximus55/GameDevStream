package ru.maximus55.projectname.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import ru.maximus55.projectname.components.OrthographicCameraComponent

class RenderMapSystem(val cameraEntity: Entity, map: TiledMap) : EntitySystem() {

    val mapRenderer = OrthogonalTiledMapRenderer(map)

    override fun update(deltaTime: Float) {
        mapRenderer.setView(cameraEntity.getComponent(OrthographicCameraComponent::class.java).camera)
        mapRenderer.render()
    }
}