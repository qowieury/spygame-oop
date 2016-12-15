package com.spy.game.component;

import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by qowie on 12/15/2016.
 */
public class GameOverScene implements IScript{

    public TransformComponent transformComponent;

    @Override
    public void init(Entity entity) {
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void dispose() {

    }
}
