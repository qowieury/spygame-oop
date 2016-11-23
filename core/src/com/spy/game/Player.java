package com.spy.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.brashmonkey.spriter.Box;
import com.spy.game.component.ComponentBox;
import com.sun.glass.ui.View;
import com.uwsoft.editor.renderer.Overlap2D;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.PolygonComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.systems.render.Overlap2dRenderer;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by qowie on 10/23/2016.
 */
public class Player implements IScript {


    protected TransformComponent transformComponent;
    protected DimensionsComponent dimensionsComponent;

    protected AnimationComponent animationComponent;


    private PolygonComponent polygonComponent;


    protected World world;
    protected Vector2 speed;
    protected final Vector2 jumpSideSpeed = new Vector2(-20, 0);
    protected final float gravity = -500f;
    protected final float jumpSpeed = 200f;
    protected boolean isJumping;

    public Player() {

    }

    public Player(World world) {
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


        speed = new Vector2(80, 0);
    }

    @Override
    public void act(float delta) {
        setPolygonPostion();
        getInput(delta);
        doGravity(delta);
        rayCastButtom();



    }



    @Override
    public void dispose() {

    }

    protected void setPolygonPostion(){
        dimensionsComponent.polygon.setPosition(transformComponent.x,transformComponent.y);
    }

    public void contactWall(Polygon polygon){
        if (transformComponent.x < polygon.getX()){
            transformComponent.x -= speed.x*Gdx.graphics.getDeltaTime();
        }else {
            transformComponent.x += speed.x*Gdx.graphics.getDeltaTime();
        }

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

    public Polygon getPolygon(){
        return dimensionsComponent.polygon;
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

    protected void walkLeft(float delta) {
        if (isJumping == false) {
            transformComponent.x -= speed.x * delta;
        } else {
            transformComponent.x -= (speed.x + jumpSideSpeed.x) * delta;
        }

        transformComponent.scaleX = -1f;


    }

    protected void walkRight(float delta) {
        if (isJumping == false) {
            transformComponent.x += speed.x * delta;
        } else {
            transformComponent.x += (speed.x + jumpSideSpeed.x) * delta;
        }
        transformComponent.scaleX = 1f;

    }

    protected void jump() {
        speed.y = jumpSpeed;
        isJumping = true;

    }

    public void standOnBox(ComponentBox box){

            if(transformComponent.y > box.transformComponent.y+(box.dimensionsComponent.height)/1.2f ){
                isJumping = false;
                speed.y = 0;
                transformComponent.y = (box.dimensionsComponent.height)+box.transformComponent.y;

                System.out.println("Player stand on box");

            }

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

    public Rectangle getBoundbox(){
        return dimensionsComponent.boundBox;
    }
}
