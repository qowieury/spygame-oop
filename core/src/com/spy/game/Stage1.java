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
import com.uwsoft.editor.renderer.Overlap2D;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

/**
 * Created by qowie on 11/7/2016.
 */
public class Stage1 extends BaseStage {
    protected String STAGE_NAME ="MainScene";
    protected final float VIEWPORT_X = 360;
    protected final float VIEWPORT_Y = 200;


    public Stage1(){


    }
    @Override
    protected void addScriptToChildOfRoot(){
        root.getChild("player").addScript(player);
        root.getChild("enemy0").addScript(enemy.get(0));
        root.getChild("enemy1").addScript(enemy.get(1));

    }
    @Override
    protected void createEnemy(){
        enemy.add(new Enemy(120, 180, sceneLoader.world));
        enemy.add(new Enemy(180, 240, sceneLoader.world));

    }
    @Override
    protected void createPlayer(){
        player = new Player(sceneLoader.world);
    }









}
