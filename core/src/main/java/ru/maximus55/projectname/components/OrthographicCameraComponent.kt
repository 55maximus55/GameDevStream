package ru.maximus55.projectname.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.OrthographicCamera

class OrthographicCameraComponent : Component {
    val camera = OrthographicCamera().apply {
        setToOrtho(false)
    }
}