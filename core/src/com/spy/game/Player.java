package com.spy.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by qowie on 10/23/2016.
 */
public class Player implements IScript {

    private Entity player;
    private TransformComponent tranfromComponent;

    private Vector2 speed;
    private final float gravity = -500f;
    private final float jumpSpeed = 100f;

    @Override
    public void init(Entity entity) {
        player = entity;
        tranfromComponent = ComponentRetriever.get(entity,TransformComponent.class);

        speed = new Vector2(100,0);
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            speed.y = jumpSpeed;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            tranfromComponent.x -= speed.x*delta;
            tranfromComponent.scaleX = -1f;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            tranfromComponent.x += speed.x*delta;
            tranfromComponent.scaleX = 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){

        }
        speed.y += gravity*delta;
        tranfromComponent.y += speed.y*delta;
        if(tranfromComponent.y < 11f){
            speed.y = 0 ;
            tranfromComponent.y = 11f;

        }

    }

    @Override
    public void dispose() {

    }
}
