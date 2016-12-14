package com.spy.game.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by qowie on 11/30/2016.
 */
public class ComponentItem extends ComponentBox {
    protected int id;
    protected float timeToRun; //run time of item in second
    protected float ranTime = 0;// time that item has been ran

    public boolean isHave;
    public boolean isActive;

    public ComponentItem(int id){
        this(id,new World(new Vector2(0,0),false));
    }

    public ComponentItem(int id, World world) {
        this.id = id;
        if (id == 0) {
            this.timeToRun = 10;
        } else if (id == 1) {
            this.timeToRun = 10;
        } else if (id == 2) {
            this.timeToRun = 10;
        }
        isActive = false;
        isHave = false;
        this.world = world;
    }

    @Override
    public void act(float delta) {

        setPolygonPostion();
        //doGravity(delta);
        //rayCastButtom();



    }
    public void psudoDelete(){
        transformComponent.y = -9999;
        transformComponent.disableTransform();
    }

    public void use() {
        isHave = false;
        isActive = true;
    }

    public void ranTime() {
        if (ranTime > timeToRun) {
            isActive = false;
            ranTime = 0;

        }
        if (isActive == true) {
            ranTime += Gdx.graphics.getDeltaTime();
            System.out.println("rantime : " + ranTime);
        }


    }


    public int getId() {
        return this.id;
    }


}
