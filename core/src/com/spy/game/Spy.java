package com.spy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

public class Spy extends ApplicationAdapter {

    SceneLoader sceneLoader;

    private Player player;
    private Viewport viewport;
    private ItemWrapper root;

    @Override
    public void create() {

        viewport = new FitViewport(360, 200);
        sceneLoader = new SceneLoader();
        sceneLoader.loadScene("MainScene", viewport);

        root = new ItemWrapper(sceneLoader.getRoot());


        player = new Player(sceneLoader.world);
        Enemy enemy1 = new Enemy(120,180,sceneLoader.world);
        Enemy enemy2 = new Enemy(180,240,sceneLoader.world);
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
    }
}
