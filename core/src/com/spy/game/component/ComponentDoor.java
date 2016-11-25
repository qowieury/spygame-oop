package com.spy.game.component;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.spy.game.CollisionListener;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.PolygonComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

import java.util.ArrayList;

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

    public void checktrig(ArrayList<ComponentBase> base , ArrayList<ComponentBox> box,int doorNum){
        CollisionListener collisionListener = new CollisionListener();
        for(int i =0 ;i<base.size();i++){
            for (int j=0;j<box.size();j++){
                if(collisionListener.isCollision(base.get(i).getPolygon(),box.get(j).getPolygon())){
                    if(doorNum == i){
                        checkDoorTrigged();
                    }


                }
            }
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

        }
    }

    public void cancelTrigged(){
        isDoorTrigged = false;
    }
    public void openDoor(){
        if(isEverTriggedBefore && isDoorTrigged) {
            if(transformComponent.y < originY + dimensionsComponent.height/2) {
                System.out.println("DOOOOOOOOOOOOOOOOR TRIGG");
                transformComponent.y += (1);
            }

        }else if(isEverTriggedBefore && !isDoorTrigged){
            if(transformComponent.y > originY){
                transformComponent.y -= (1);
            }

        }
    }

}
