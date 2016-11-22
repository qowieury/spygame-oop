package com.spy.game.component;

import com.badlogic.gdx.physics.box2d.World;
import com.spy.game.Player;

/**
 * Created by qowie on 11/22/2016.
 */
public class ComponentBox extends Player {

    public ComponentBox(){

    }
    public ComponentBox(World world){
        this.world = world;
    }

    @Override
    public void act(float delta) {
        setPolygonPostion();
        getInput(delta);
        doGravity(delta);
        rayCastButtom();



    }
}
