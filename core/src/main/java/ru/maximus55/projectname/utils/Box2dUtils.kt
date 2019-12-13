package ru.maximus55.projectname.utils

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.*

fun createCircleBody(world: World) : Body {
    val bDef = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        fixedRotation = true
    }
    val circleShape = CircleShape().apply {
        radius = 0.4f
    }
    val fDef = FixtureDef().apply {
        shape = circleShape
    }

    return world.createBody(bDef).apply {
        createFixture(fDef)
    }
}

fun createWallsFromMap(world: World, ppm: Float, map: TiledMap, layerName: String) {
    for (i in map.layers["walls"].objects.getByType(RectangleMapObject::class.java)) {
        val rect = i.rectangle

        val bDef = BodyDef().apply {
            type = BodyDef.BodyType.StaticBody
            position.set(rect.getX() / ppm + rect.getWidth() / 2 / ppm, rect.getY() / ppm + rect.getHeight() / 2 / ppm)
        }
        val polygonShape = PolygonShape().apply {
            setAsBox(rect.getWidth() / 2 / ppm, rect.getHeight() / 2 / ppm)
        }
        val fDef = FixtureDef().apply {
            shape = polygonShape
        }
        world.createBody(bDef).apply {
            createFixture(fDef)
        }
    }
}