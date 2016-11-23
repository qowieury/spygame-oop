package com.spy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spy.game.component.*;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;

/**
 * Created by qowie on 11/11/2016.
 */
public class BaseStage extends ApplicationAdapter {
    protected String STAGE_NAME = "MainScene";
    protected final float VIEWPORT_X = 360;
    protected final float VIEWPORT_Y = 200;

    protected SceneLoader sceneLoader;
    protected Player player;
    protected ArrayList<Enemy> enemy = new ArrayList<Enemy>();
    protected ArrayList<ComponentWall> wall = new ArrayList<ComponentWall>();
    protected ArrayList<ComponentDoor> door = new ArrayList<ComponentDoor>();
    protected ArrayList<ComponentBox> box = new ArrayList<ComponentBox>();
    protected ArrayList<ComponentFloor> floor = new ArrayList<ComponentFloor>();
    protected ArrayList<ComponentBase> base = new ArrayList<ComponentBase>();

    protected CollisionListener collisionListener = new CollisionListener();


    protected Viewport viewport;
    protected ItemWrapper root;


    @Override
    public void create() {

        loadSceneAndViewport();
        createPlayer();
        createEnemy();
        initWallToOverride();
        initBoxToOverride();
        initFloorToOverride();
        initDoorToOverride();
        initBaseToOverride();
        addScriptToChildOfRoot();


    }

    protected void initBoxToOverride() {
        initBox(1);
    }

    protected void initWallToOverride() {
        initWall(2);
    }

    protected void initDoorToOverride(){
        initDoor(1);
    }

    protected void initBaseToOverride(){
        initBase(1);
    }

    protected void initFloorToOverride() {
        initFloor(5);
    }


    protected void initBox(int boxCount) {
        for (int i = 0; i < boxCount; i++) {
            box.add(new ComponentBox(sceneLoader.world));
        }

        for (int i = 0; i < boxCount; i++) {
            root.getChild("box" + i).addScript(box.get(i));
        }
    }

    protected void initBase(int baseCount){
        for (int i = 0; i < baseCount; i++) {
            base.add(new ComponentBase());
        }

        for (int i = 0; i < baseCount; i++) {
            root.getChild("base" + i).addScript(base.get(i));
        }

    }

    protected  void initDoor(int doorCount){
        for (int i = 0; i < doorCount; i++) {
        door.add(new ComponentDoor());
    }

        for (int i = 0; i < doorCount; i++) {
            root.getChild("door" + i).addScript(door.get(i));
        }

    }

    protected void initFloor(int floorCount) {

        for (int i = 0; i < floorCount; i++) {
            floor.add(new ComponentFloor());
        }

        for (int i = 0; i < floorCount; i++) {
            root.getChild("floor" + i).addScript(floor.get(i));
        }

    }


    protected void initWall(int wallCount) {

        for (int i = 0; i < wallCount; i++) {
            wall.add(new ComponentWall());
        }

        for (int i = 0; i < wallCount; i++) {
            root.getChild("wall" + i).addScript(wall.get(i));
        }

    }

    protected void addScriptToChildOfRoot() {
        root.getChild("player").addScript(player);
        root.getChild("enemy0").addScript(enemy.get(0));
        root.getChild("enemy1").addScript(enemy.get(1));

    }

    protected void createEnemy() {
        enemy.add(new Enemy(120, 180, sceneLoader.world));
        enemy.add(new Enemy(180, 240, sceneLoader.world));

    }

    protected void createPlayer() {
        player = new Player(sceneLoader.world);
    }

    protected void loadSceneAndViewport() {
        viewport = new FitViewport(VIEWPORT_X, VIEWPORT_Y);
        sceneLoader = new SceneLoader();
        //sceneLoader.loadScene(STAGE_NAME, viewport);
        loadSceneByName();
        root = new ItemWrapper(sceneLoader.getRoot());
    }

    protected void loadSceneByName() {
        sceneLoader.loadScene(STAGE_NAME, viewport);
    }

    @Override
    public void render() {
        loadSceneRender();
        cameraFollowPlayer();
        detectPlayerCollisEnemy();
        detectPlayerCollisWall();
        detectPlayerCollisBox();
        detectPlayerCollisDoor();
        detectBoxCollisBase();
        detectBoxCollisWall();


    }

    protected void detectBoxCollisBase(){
        for(int i =0;i<box.size();i++){
            for (int j=0;j<base.size();j++){
                if(collisionListener.isCollision(box.get(i).getPolygon(),base.get(j).getPolygon())){
                    door.get(j).checkDoorTrigged();

                    System.out.println("box and Base");
                }else{
                    door.get(j).cancelTrigged();
                    System.out.println("canceltriggg");
                }
            }
        }
    }

    protected void detectPlayerCollisDoor(){
        if (player.getPolygon() != null) {
            for (int i = 0; i < door.size(); i++) {
                if (door.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), door.get(i).getPolygon())) {
                        System.out.println("player and door" + i);
                        player.contactWall(door.get(i).getPolygon());
                    }
                }
            }
        }

    }

    protected void detectBoxCollisWall(){
        for(int i =0;i<box.size();i++){
            for (int j=0;j<wall.size();j++){
                if(collisionListener.isCollision(box.get(i).getPolygon(),wall.get(j).getPolygon())){
                    box.get(i).contactWall(wall.get(j).getPolygon());
                    System.out.println("box and wall");
                }
            }
        }
    }

    protected void detectPlayerCollisBox(){
        if (player.getPolygon() != null) {
            for (int i = 0; i < box.size(); i++) {
                if (box.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), box.get(i).getPolygon())) {
                        System.out.println("player and box" + i);
                        box.get(i).contactPlayer(player.getPolygon());
                        player.standOnBox(box.get(i));
                    }
                }
            }
        }

    }

    protected void detectPlayerCollisWall() {
        if (player.getPolygon() != null) {
            for (int i = 0; i < wall.size(); i++) {
                if (wall.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), wall.get(i).getPolygon())) {
                        System.out.println("player and wall" + i); ////////////////
                        player.contactWall(wall.get(i).getPolygon());
                    }
                }
            }
        }
    }

    protected void detectPlayerCollisEnemy() {
        if (player.getPolygon() != null) {
            for (int i = 0; i < enemy.size(); i++) {
                if (enemy.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), enemy.get(i).getPolygon())) {
                        System.out.println("player and enemy " + i);
                    }
                }
            }
        }
    }

    protected void loadSceneRender() {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());
    }

    protected void cameraFollowPlayer() {
        ((OrthographicCamera) viewport.getCamera()).position.set(player.getX() + 100, player.getY() + 70, 0f);

    }

}
