package com.spy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.glass.ui.Application;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

/**
 * Created by qowie on 11/7/2016.
 */
public class Stage1 extends ApplicationAdapter {
    private final String STAGE_NAME ="MainScene";
    private final float VIEWPORT_X = 360;
    private final float VIEWPORT_Y = 200;

    SceneLoader sceneLoader;
    private Player player;
    private Enemy enemy1;
    private Enemy enemy2;
    private CollisionListener collisionListener = new CollisionListener();


    private Viewport viewport;
    private ItemWrapper root;

    public Stage1(){


    }

    @Override
    public void create() {
        viewport = new FitViewport(VIEWPORT_X,VIEWPORT_Y);
        sceneLoader = new SceneLoader();
        sceneLoader.loadScene(STAGE_NAME, viewport);

        root = new ItemWrapper(sceneLoader.getRoot());

        player = new Player(sceneLoader.world);
        enemy1 = new Enemy(120,180,sceneLoader.world);
        enemy2 = new Enemy(180,240,sceneLoader.world);
        root.getChild("player").addScript(player);
        root.getChild("enemy1").addScript(enemy1);
        root.getChild("enemy2").addScript(enemy2);



    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());
        ((OrthographicCamera) viewport.getCamera()).position.set(player.getX() + 100, player.getY() + 70, 0f);

        if(player.getPolygon() == null){
            System.out.println("player1 polygon null");
        }
        if(player.getPolygon() != null && enemy1.getPolygon() != null){
            System.out.println("!= null");
            if(collisionListener.isCollision(player.getPolygon(),enemy1.getPolygon())){
              System.out.println("player and enemy1");
           }
        }

    }





}
