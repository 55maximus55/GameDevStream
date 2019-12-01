package ru.maximus55.projectname.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.app.KtxScreen
import ktx.scene2d.defaultStyle
import ktx.scene2d.label
import ktx.scene2d.table
import ru.maximus55.projectname.App

class GameScreen(val stage: Stage, val app: App) : KtxScreen {

    val view = table {
        setFillParent(true)

        center()

        label(text = "X", style = defaultStyle)
    }

    override fun show() {
        stage.addActor(view)
        createWalls()
    }


    val map = TmxMapLoader().load("maps/testMap.tmx")
    val mapRenderer = IsometricTiledMapRenderer(map, app.context.inject<Batch>())


    val world = createWorld()
    val worldDebug = Box2DDebugRenderer()


    val camera = OrthographicCamera()

    var x = 0f
    var y = 0f

    val speed = 4f

    val tileWidth = 48f
    val tileHeight = 24f


    val playerBody = createPlayer()


    override fun render(delta: Float) {
        val velocity = Vector2(0f, 0f)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.x -= 1f
            velocity.y += 1f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x -= 1f
            velocity.y -= 1f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.x += 1f
            velocity.y -= 1f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x += 1f
            velocity.y += 1f
        }
        velocity.clamp(0f, 1f).scl(speed)
        playerBody.linearVelocity = velocity






        world.step(delta, 10, 10)

        camera.position.apply {
            camera.position.x = playerBody.position.x * 24f + playerBody.position.y * 24f
            camera.position.y = playerBody.position.y * 12f - playerBody.position.x * 12f
        }
        camera.update()

        mapRenderer.setView(camera)
        mapRenderer.render()

        camera.position.apply {
            camera.position.x = playerBody.position.x
            camera.position.y = playerBody.position.y
        }
        camera.rotate(-45f)
        camera.zoom /= 34f
        camera.viewportHeight *= 2f
        camera.update()
        worldDebug.render(world, camera.combined)
        camera.rotate(45f)
        camera.viewportHeight /= 2f
        camera.zoom *= 34f
    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = 128f * Gdx.graphics.width / Gdx.graphics.height
        camera.viewportHeight = 128f
    }

    override fun hide() {
        view.remove()
    }

    private fun createWorld(): World {
        val world = World(Vector2.Zero, true)

//        for (i in map.layers.get("walls").objects.getByType(RectangleMapObject::class.java)) {
//            val rect = i.rectangle
//            bDef.type = BodyDef.BodyType.StaticBody
//            bDef.position.set((rect.getX() + rect.getWidth() / 2) / tileWidth, (rect.getY() + rect.getHeight() / 2) / tileWidth)
//
//            body = world.createBody(bDef)
//
//            shape.setAsBox(rect.getWidth() / 2 / tileWidth, rect.getHeight() / 2 / tileWidth)
//            fDef.shape = shape
//            fDef.friction = 0f
//            fDef.restitution = 0f
//            fDef.density = 1f
//            body.createFixture(fDef)
//            body.userData = "wall"
//        }


        return world
    }

    private fun createWalls() {
        val bodyDef = BodyDef().apply {
            type = BodyDef.BodyType.StaticBody
            position.set(0f, 1f)
        }
        val shape = PolygonShape().apply {
            setAsBox(0.5f, 0.5f)
        }
        val fixtureDef = FixtureDef().apply {
            friction = 0f
            restitution = 0f
            density = 1f
            this.shape = shape
        }
        val body = world.createBody(bodyDef)
        body.createFixture(fixtureDef)
    }

    private fun createPlayer(): Body {
        val bodyDef = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            position.set(0f, 0f)
        }
        val shape = CircleShape().apply {
            radius = 0.5f
        }
        val fixtureDef = FixtureDef().apply {
            friction = 0f
            restitution = 0f
            density = 1f
            this.shape = shape
        }

        val body = world.createBody(bodyDef)
        body.createFixture(fixtureDef)

        return body
    }


}