package com.spy.game.component;

import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.PolygonComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by qowie on 11/22/2016.
 */
public class ComponentFloor extends ComponentWall {

    @Override
    public void init(Entity entity) {

        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        animationComponent = ComponentRetriever.get(entity, AnimationComponent.class);
        polygonComponent = ComponentRetriever.get(entity, PolygonComponent.class);

        polygonComponent.makeRectangle(dimensionsComponent.width,dimensionsComponent.height);
        dimensionsComponent.setPolygon(polygonComponent);

    }
}
