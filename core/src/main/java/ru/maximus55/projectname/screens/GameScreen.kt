package ru.maximus55.projectname.screens

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.app.KtxScreen
import ktx.app.use
import ru.maximus55.projectname.App
import ru.maximus55.projectname.objects.Player

class GameScreen(val stage: Stage, val app: App) : KtxScreen {

    val ppm = 16f
    val camera = OrthographicCamera().apply {
        setToOrtho(false)
        zoom /= 2f
    }
    val viewport = ScreenViewport(camera)

    val map = TmxMapLoader().load("maps/topDownTestMap.tmx")
    val mapRenderer = OrthogonalTiledMapRenderer(map)


    val world = World(Vector2.Zero, true)
    val worldDebugView = Box2DDebugRenderer()


    val player = Player(Texture("textures/player.png"), createCircleBody(), ppm)


    override fun show() {
        createWalls()
    }

    override fun render(delta: Float) {
        player.update()
        world.step(delta, 10, 10)

        camera.apply {
            position.x = player.x
            position.y = player.y
            update()
        }
        mapRenderer.setView(camera)
        mapRenderer.render()

        app.context.inject<Batch>().apply {
            projectionMatrix = camera.combined
            use {
                player.draw(app.context.inject())
            }
        }

        //box2d debug
        apply {
            camera.zoom /= ppm
            camera.position.x /= ppm
            camera.position.y /= ppm
            camera.update()
            worldDebugView.render(world, camera.combined)
            camera.zoom *= ppm
            camera.position.x *= ppm
            camera.position.y *= ppm
            camera.update()
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    private fun createCircleBody() : Body {
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

    private fun createWalls() {
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

}