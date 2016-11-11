package com.spy.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by qowie on 10/31/2016.
 */
public class Enemy extends Player {
    float[] walkBetweenX;
    protected boolean isGoRight;
    public Enemy(float x1, float x2, World world){
        walkBetweenX = new float[2];
        walkBetweenX[0] = x1;
        walkBetweenX[1] = x2;
        isGoRight = true;
        this.world =world;


    }
    @Override
    public void act(float delta) {
        dimensionsComponent.polygon.setPosition(transformComponent.x,transformComponent.y);
        doGravity(delta);
        rayCastButtom();

        if(isGoRight){
            walkRight(delta);
        }
        if(!isGoRight){
            walkLeft(delta);
        }
        if(transformComponent.x <= walkBetweenX[0]){
            isGoRight = true;
        }
        if(transformComponent.x >= walkBetweenX[1]){
            isGoRight = false;
        }
    }

}
