package ru.maximus55.projectname

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxGame
import ktx.assets.toInternalFile
import ktx.inject.Context
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.defaultStyle
import ktx.style.label
import ktx.style.skin
import ktx.style.textButton
import ru.maximus55.projectname.Screens.GameScreen

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class App : KtxGame<Screen>() {

    val context = Context()

    override fun create() {
        context.register {
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton<Viewport>(ScreenViewport())
            bindSingleton(Stage(inject(), inject()))
            bindSingleton(createSkin())
            bindSingleton(this@App)
            Scene2DSkin.defaultSkin = inject()

            bindSingleton(GameScreen(inject(), inject()))
        }

        addScreen(context.inject<GameScreen>())

        setScreen<GameScreen>()
    }

    fun createSkin(): Skin = skin { skin ->
        add(defaultStyle, FreeTypeFontGenerator("PressStart2P-Regular.ttf".toInternalFile()).generateFont(
                FreeTypeFontGenerator.FreeTypeFontParameter().apply {
                    size = 32
                    color = Color.WHITE
                }
        ))

        label {
            font = skin.getFont(defaultStyle)
        }
        textButton {
            font = skin.getFont(defaultStyle)
            fontColor = Color.GREEN
            downFontColor = Color.BLUE
        }
    }

    override fun render() {
        super.render()
        context.inject<Stage>().apply {
            act()
            draw()
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        context.inject<Stage>().viewport.update(width, height, true)
    }
}