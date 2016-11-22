package com.spy.game.component;

/**
 * Created by qowie on 11/18/2016.
 */
public class ComponentDoor extends ComponentWall{
    protected boolean isDoorTrigged = false;

    public void checkDoorTrigged(){
        if(isDoorTrigged){
            openDoor();
        }
    }
    protected void openDoor(){
        transformComponent.y += 50;
    }

}
