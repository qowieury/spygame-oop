package com.spy.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.World;
import com.spy.game.Player;


/**
 * Created by qowie on 11/22/2016.
 */
public class ComponentBox extends Player {

    public ComponentBox() {

    }

    public ComponentBox(World world) {
        this.world = world;
    }

    @Override
    public void act(float delta) {
        setPolygonPostion();

        doGravity(delta);
        rayCastButtom();


    }

    public void contactPlayer(Polygon polygon) {
        if (transformComponent.y >= polygon.getY()-polygon.getScaleY()/2) {
            if (polygon.getX() < transformComponent.x) {
                walkRight(Gdx.graphics.getDeltaTime());
            } else {
                walkLeft(Gdx.graphics.getDeltaTime());
            }
        }
    }
}
