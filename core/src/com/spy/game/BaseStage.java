package com.spy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spy.game.component.ComponentBox;
import com.spy.game.component.ComponentDoor;
import com.spy.game.component.ComponentWall;
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
        addScriptToChildOfRoot();




    }
    protected  void  initBoxToOverride(){
        initBox(1);
    }
    protected void initWallToOverride(){
        initWall(2);
    }


    protected  void initBox(int boxCount){
        for(int i=0;i<boxCount;i++) {
           box.add(new ComponentBox(sceneLoader.world));
        }

        for(int i=0;i<boxCount;i++){
            root.getChild("box"+i).addScript(box.get(i));
        }
    }



    protected void initWall(int wallCount){

        for(int i=0;i<wallCount;i++) {
            wall.add(new ComponentWall());
        }

        for(int i=0;i<wallCount;i++){
            root.getChild("wall"+i).addScript(wall.get(i));
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
    protected void loadSceneByName(){
        sceneLoader.loadScene(STAGE_NAME,viewport);
    }

    @Override
    public void render() {
        loadSceneRender();
        cameraFollowPlayer();
        detectPlayerCollisEnemy();
        detectPlayerCollisWall();


}

    protected  void detectPlayerCollisWall(){
        if (player.getPolygon() != null) {
            for (int i = 0; i < wall.size(); i++) {
                if (wall.get(i).getPolygon() != null) {
                    if(collisionListener.isCollision(player.getPolygon(),wall.get(i).getPolygon())){
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
