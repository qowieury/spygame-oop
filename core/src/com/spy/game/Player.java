package com.spy.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BooleanArray;
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
    private Vector2 acceleration;
    private final float gravity = -500f;
    private final float jumpSpeed = 200f;

    private boolean isJumping;

    @Override
    public void init(Entity entity) {
        player = entity;
        tranfromComponent = ComponentRetriever.get(entity,TransformComponent.class);
        acceleration = new Vector2(0,0);
        speed = new Vector2(80,0);
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.W) && isJumping == false){
            speed.y = jumpSpeed;
            isJumping = true;
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                acceleration.x = -80;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                acceleration.x = 80;
            }

        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            if(isJumping == false) {
                tranfromComponent.x -= speed.x * delta;
            }
            if(acceleration.x > -20){
                acceleration.x -= 0.5f;
            }
            tranfromComponent.scaleX = -1f;

        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            if(isJumping == false) {
                tranfromComponent.x += speed.x * delta;
            }
            if(acceleration.x < 20){
                acceleration.x += 0.5f;
            }
            tranfromComponent.scaleX = 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){

        }

        tranfromComponent.x += acceleration.x*delta;
        if(acceleration.x != 0) {
            if(acceleration.x < 0) {
                acceleration.x += 0.1f;
            }else{
                acceleration.x -= 0.1f;
            }
        }

        speed.y += gravity*delta;
        tranfromComponent.y += speed.y*delta;
        if(tranfromComponent.y < 11f){
            speed.y = 0 ;
            tranfromComponent.y = 11f;
            if(isJumping == true){
                acceleration.x = 10*tranfromComponent.scaleX;
                isJumping = false;
            }


        }

    }

    @Override
    public void dispose() {

    }
}
