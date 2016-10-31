package com.spy.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.glass.ui.View;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by qowie on 10/23/2016.
 */
public class Player implements IScript {

    protected Entity player;
    protected TransformComponent transformComponent;
    protected DimensionsComponent dimensionsComponent;

    protected AnimationComponent animationComponent;
    protected World world;


    protected Vector2 speed;
    protected final Vector2 jumpSideSpeed = new Vector2(-20, 0);

    protected final float gravity = -500f;
    protected final float jumpSpeed = 200f;

    protected boolean isJumping;
    public Player(){

    }

    public Player(World world) {
        this.world = world;
    }

    @Override
    public void init(Entity entity) {
        player = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        animationComponent = ComponentRetriever.get(entity, AnimationComponent.class);

        speed = new Vector2(80, 0);
    }

    @Override
    public void act(float delta) {

        getInput(delta);
        doGravity(delta);
        rayCastButtom();
    }

    protected void rayCastButtom() {
        float rayGap = dimensionsComponent.height / 2;
        float raySize = -(speed.y) * Gdx.graphics.getDeltaTime();

        if (speed.y > 0) return;

        Vector2 rayFrom = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(), (transformComponent.y + rayGap) * PhysicsBodyLoader.getScale());
        Vector2 rayTo = new Vector2((transformComponent.x + dimensionsComponent.width / 2) * PhysicsBodyLoader.getScale(), (transformComponent.y - raySize) * PhysicsBodyLoader.getScale());

        world.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                isJumping = false;
                speed.y = 0;

                transformComponent.y = point.y / PhysicsBodyLoader.getScale() + 0.01f;
                return 0;
            }
        }, rayFrom, rayTo);
    }

    @Override
    public void dispose() {

    }

    protected void doGravity(float delta) {
        speed.y += gravity * delta;
        transformComponent.y += speed.y * delta;
    }

    protected void getInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && isJumping == false) {
            jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            walkLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            walkRight(delta);

        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {

        }
    }
    protected void walkLeft(float delta){
        if (isJumping == false) {
            transformComponent.x -= speed.x * delta;
        } else {
            transformComponent.x -= (speed.x + jumpSideSpeed.x) * delta;
        }

        transformComponent.scaleX = -1f;


    }
    protected void walkRight(float delta){
        if (isJumping == false) {
            transformComponent.x += speed.x * delta;
        } else {
            transformComponent.x += (speed.x + jumpSideSpeed.x) * delta;
        }
        transformComponent.scaleX = 1f;

    }
    protected void jump(){
        speed.y = jumpSpeed;
        isJumping = true;

    }

    public float getSpeedX() {
        return speed.x;
    }

    public float getSpeedY() {
        return speed.y;
    }

    public float getX() {
        return transformComponent.x;
    }

    public float getY() {
        return transformComponent.y;
    }
}
