package com.spy.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.uwsoft.editor.renderer.components.PolygonComponent;
import com.uwsoft.editor.renderer.scripts.IScript;

/**
 * Created by qowie on 11/8/2016.
 */
public class CollisionListener {

    public CollisionListener(){
    }

    public boolean isCollision(Polygon polygon1,Polygon polygon2){

        return polygon1.getBoundingRectangle().overlaps(polygon2.getBoundingRectangle());

    }


}
