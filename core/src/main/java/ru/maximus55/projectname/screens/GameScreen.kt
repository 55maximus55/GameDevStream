package ru.maximus55.projectname.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.app.KtxScreen
import ru.maximus55.projectname.App
import ru.maximus55.projectname.components.UpdateCameraSystem
import ru.maximus55.projectname.objects.Camera
import ru.maximus55.projectname.objects.Player
import ru.maximus55.projectname.systems.*
import ru.maximus55.projectname.utils.createWallsFromMap

class GameScreen(val stage: Stage, val app: App) : KtxScreen {

    val ppm = 16f
    val viewport = ScreenViewport()

    val map = TmxMapLoader().load("maps/topDownTestMap.tmx")

    val worldDebugView = Box2DDebugRenderer()

    val ecs = Engine()


    override fun show() {
        ecs.apply {
            addSystem(Box2dWorldSystem(World(Vector2.Zero, true)))

            val player = Player().create(ecs.getSystem(Box2dWorldSystem::class.java).world)
            val camera = Camera().create(player, viewport)
            addEntity(player)
            addEntity(camera)

            addSystem(PlayerControllerSystem())
            addSystem(UpdateCameraSystem(ppm))
            addSystem(RenderMapSystem(camera, map))
            addSystem(SpritesUpdatePositionSystem(ppm))
            addSystem(SpritesRenderSystem(app.context.inject(), camera))
        }

        createWallsFromMap(ecs.getSystem(Box2dWorldSystem::class.java).world, ppm, map, "walls")
    }

    override fun render(delta: Float) {
        ecs.update(delta)

        //box2d debug
//        apply {
//            camera.zoom /= ppm
//            camera.position.x /= ppm
//            camera.position.y /= ppm
//            camera.update()
//            worldDebugView.render(ecs.getSystem(Box2dWorldSystem::class.java).world, camera.combined)
//            camera.zoom *= ppm
//            camera.position.x *= ppm
//            camera.position.y *= ppm
//            camera.update()
//        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

}