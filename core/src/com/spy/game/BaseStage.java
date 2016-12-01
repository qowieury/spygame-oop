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
    protected ArrayList<ComponentItem> item = new ArrayList<ComponentItem>();

    protected CollisionListener collisionListener = new CollisionListener();


    protected Viewport viewport;
    protected ItemWrapper root;

    private float currentTime =0;


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
        initItemToOverride();

        addScriptToChildOfRoot();


    }

    protected void initBoxToOverride() {
        initBox(4);
    }

    protected void initWallToOverride() {
        initWall(1);
    }

    protected void initDoorToOverride(){
        initDoor(3);
    }

    protected void initBaseToOverride(){
        initBase(1);
    }

    protected void initFloorToOverride() {
        initFloor(5);
    }

    protected void initItemToOverride(){
        initItem(1,1,1);
    }

    protected void initItem(int a,int b,int c){
        for (int i =0;i<a;i++){
            item.add(new ComponentItem(0,sceneLoader.world));
            root.getChild("itemID0-"+(i)).addScript(item.get(item.size()-1));
        }
        for (int i =0;i<b;i++){
            item.add(new ComponentItem(1,sceneLoader.world));
            root.getChild("itemID1-"+(i)).addScript(item.get(item.size()-1));
        }
        for (int i =0;i<c;i++){
            item.add(new ComponentItem(2,sceneLoader.world));
            root.getChild("itemID2-"+(i)).addScript(item.get(item.size()-1));
        }
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
        detectPlayerCollisItem();
        detectBoxCollisBase();
        detectBoxCollisWall();

        currentTime = currentTime + Gdx.graphics.getDeltaTime();
        System.out.println(currentTime);


    }

    protected void detectPlayerCollisItem(){
        if (player.getPolygon() != null) {
            for (int i = 0; i < item.size(); i++) {
                if (item.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), item.get(i).getPolygon())) {
                        System.out.println("player and item" + i);
                        if(player.collectItem(item.get(i))){
                            item.get(i).psudoDelete();
                        }

                    }
                }
            }
        }
    }

    protected void detectBoxCollisBase(){
        for (int i =0;i<door.size();i++){
            door.get(i).checktrig(base,box,i);
        }

        /*
        for(int i =0;i<base.size();i++){
            for (int j=0;j<box.size();j++){
                if(collisionListener.isCollision(box.get(j).getPolygon(),base.get(i).getPolygon())){
                    try {
                        //door.get(i).checkDoorTrigged();
                        base.get(i).trigTheDoor(door.get(i));
                        System.out.println("door "+i+ " trig");
                    }catch (IndexOutOfBoundsException outOfbound){
                       // System.out.println("outofbound");
                    }

                    System.out.println("box and Base Box "+i +" Base "+j );
                }else{
                    try {
                        door.get(i).cancelTrigged();
                    }catch (IndexOutOfBoundsException outOfBound){
                      //  System.out.println("outofbound");
                    }


                   // System.out.println("canceltriggg");
                }
            }
        }
        */
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
                        if(!player.isHiding){
                            System.out.println("player and enemy " + i);
                        }
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
