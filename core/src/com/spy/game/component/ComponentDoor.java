package com.spy.game.component;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.PolygonComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by qowie on 11/18/2016.
 */
public class ComponentDoor extends ComponentWall{
    protected boolean isDoorTrigged = false;
    protected boolean isEverTriggedBefore = false;
    protected float originY;
    protected boolean isOriginYSet = false;

    @Override
    public void act(float delta) {
        setPolygonPosition();
        setOriginY();

        openDoor();
    }
    protected void setOriginY(){
        if(!isOriginYSet){
            originY = transformComponent.y;
            isOriginYSet = true;
        }
    }


    public void checkDoorTrigged(){

        if(isDoorTrigged == false){

            isDoorTrigged = true;
            isEverTriggedBefore = true;
        }else{
            if(isEverTriggedBefore){
                isDoorTrigged = true;
            }
            System.out.println("DOOOOOOOOOOOOOOOOR TRIGG");
        }
    }

    public void cancelTrigged(){
        isDoorTrigged = false;
    }
    public void openDoor(){
        if(isEverTriggedBefore && isDoorTrigged) {
            if(transformComponent.y < originY + dimensionsComponent.height/2) {
                transformComponent.y += (1);
            }

        }else if(isEverTriggedBefore && !isDoorTrigged){
            if(transformComponent.y > originY){
                transformComponent.y -= (1);
            }

        }
    }

}
