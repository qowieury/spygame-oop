package com.spy.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.World;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.PolygonComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by qowie on 11/12/2016.
 */
public class ComponentWall implements IScript {


    protected TransformComponent transformComponent;
    protected DimensionsComponent dimensionsComponent;
    protected AnimationComponent animationComponent;
    protected PolygonComponent polygonComponent;
    protected World world;

    public ComponentWall(){
    }
    public ComponentWall(World world){
        this.world = world;
    }

    @Override
    public void init(Entity entity) {

        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        animationComponent = ComponentRetriever.get(entity, AnimationComponent.class);
        polygonComponent = ComponentRetriever.get(entity, PolygonComponent.class);

        polygonComponent.makeRectangle(dimensionsComponent.width,dimensionsComponent.height);
        dimensionsComponent.setPolygon(polygonComponent);

    }

    @Override
    public void act(float delta) {
        dimensionsComponent.polygon.setPosition(transformComponent.x,transformComponent.y);

    }

    @Override
    public void dispose() {

    }
    public Polygon getPolygon(){
        return dimensionsComponent.polygon;

    }
}
