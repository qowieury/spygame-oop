package com.spy.game.component;

import com.badlogic.gdx.Gdx;

/**
 * Created by qowie on 11/23/2016.
 */
public class ComponentBase extends ComponentWall {

    public void trigTheDoor(ComponentDoor door){
        door.checkDoorTrigged();
        door.transformComponent.enableTransform();
        door.checkDoorTrigged();
        door.act(Gdx.graphics.getDeltaTime());
    }

}
