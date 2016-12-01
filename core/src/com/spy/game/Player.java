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
import com.spy.game.component.ComponentItem;
import com.sun.glass.ui.View;
import com.uwsoft.editor.renderer.Overlap2D;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.PolygonComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.components.spriter.SpriterComponent;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.systems.render.Overlap2dRenderer;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import java.util.ArrayList;

/**
 * Created by qowie on 10/23/2016.
 */
public class Player implements IScript {


    protected TransformComponent transformComponent;
    protected DimensionsComponent dimensionsComponent;

    protected AnimationComponent animationComponent;
    protected SpriterComponent spriterComponent;


    private PolygonComponent polygonComponent;


    protected World world;
    protected Vector2 speed;
    protected Vector2 jumpSideSpeed = new Vector2(-20, 0);
    protected final float gravity = -500f;
    protected float jumpSpeed = 200f;
    protected boolean isJumping;

    private ArrayList<ComponentItem> item =new ArrayList<ComponentItem>();
    public boolean isHiding =false;

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
        spriterComponent = ComponentRetriever.get(entity,SpriterComponent.class);

        polygonComponent.makeRectangle(dimensionsComponent.width,dimensionsComponent.height);
        dimensionsComponent.setPolygon(polygonComponent);

        item.add(new ComponentItem(0));
        item.add(new ComponentItem(1));
        item.add(new ComponentItem(2));
        speed = new Vector2(80, 0);
    }

    @Override
    public void act(float delta) {
        setPolygonPostion();
        getInput(delta);
        doGravity(delta);
        rayCastButtom();
        checkItemActive();



    }



    @Override
    public void dispose() {

    }

    protected void checkItemActive(){
        if(item.get(0).isActive){ //up spped
            speed.x = 160;
            item.get(0).ranTime();

        }else{
            speed.x = 80;
        }
        if(item.get(1).isActive){ //high jump
            this.jumpSpeed = 350f;
            item.get(1).ranTime();
        }else{
            this.jumpSpeed = 200f;
        }
        if(item.get(2).isActive){ // ghost mode
            this.isHiding = true;
            item.get(2).ranTime();

        }else{
            this.isHiding = false;
        }
    }

    public boolean collectItem(ComponentItem item){
        if(this.item.get(item.getId()).isHave == false){
            this.item.get(item.getId()).isHave = true;
            return true;
        }else {
            return false;
        }

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
        }else{
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                walkRight(delta);

            }else{
                if(spriterComponent != null){
                    spriterComponent.player.setAnimation("stand");
                }
            }



        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {

        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            if(item.get(0).isHave){
                item.get(0).use();

            }

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            if(item.get(1).isHave){
                item.get(1).use();
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            if(item.get(2).isHave){
                item.get(2).use();
            }
        }
    }

    protected void walkLeft(float delta) {
        if (isJumping == false) {
            transformComponent.x -= speed.x * delta;
        } else {
            transformComponent.x -= (speed.x + jumpSideSpeed.x) * delta;
        }

        transformComponent.scaleX = -1f;

        if(spriterComponent != null){
            spriterComponent.player.setAnimation("walkleft");

        }


    }

    protected void walkRight(float delta) {
        if (isJumping == false) {
            transformComponent.x += speed.x * delta;
        } else {
            transformComponent.x += (speed.x + jumpSideSpeed.x) * delta;
        }
        transformComponent.scaleX = 1f;

        if(spriterComponent != null){
            spriterComponent.player.setAnimation("walkright");

        }

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
