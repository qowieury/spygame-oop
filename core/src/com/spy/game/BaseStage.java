package com.spy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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

    protected CollisionListener collisionListener = new CollisionListener();


    protected Viewport viewport;
    protected ItemWrapper root;


    @Override
    public void create() {

        loadSceneAndViewport();
        createPlayer();
        createEnemy();
        addScriptToChildOfRoot();






    }
    protected void addScriptToChildOfRoot(){
        root.getChild("player").addScript(player);
        root.getChild("enemy1").addScript(enemy.get(0));
        root.getChild("enemy2").addScript(enemy.get(1));

    }
    protected void createEnemy(){
        enemy.add(new Enemy(120, 180, sceneLoader.world));
        enemy.add(new Enemy(180, 240, sceneLoader.world));

    }
    protected void createPlayer(){
        player = new Player(sceneLoader.world);
    }

    protected void loadSceneAndViewport() {
        viewport = new FitViewport(VIEWPORT_X, VIEWPORT_Y);
        sceneLoader = new SceneLoader();
        sceneLoader.loadScene(STAGE_NAME, viewport);
        root = new ItemWrapper(sceneLoader.getRoot());
    }

    @Override
    public void render() {
        loadSceneRender();
        cameraFollowPlayer();
        detectPlayerCollisEnemy();


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
