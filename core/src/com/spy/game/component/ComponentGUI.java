package com.spy.game.component;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.spy.game.Player;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.PolygonComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.components.spriter.SpriterComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
import sun.security.pkcs11.P11TlsKeyMaterialGenerator;

/**
 * Created by qowie on 12/2/2016.
 */
public class ComponentGUI extends Player {
    private Player player;
    private int ID;
    private boolean isHave;

    public ComponentGUI(int id, Player player){
        ID = id;
        this.player = player;
        isHave = false;
    }

    @Override
    public void init(Entity entity) {

        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);

        //spriterComponent = ComponentRetriever.get(entity,SpriterComponent.class);


    }



    @Override
    public void act(float delta) {
       if(ID <3){
           transformComponent.y =player.getY()+150;
           transformComponent.x = player.getX();
           int xStart = 190;
           if(ID == 0){
               transformComponent.x = player.getX()+xStart;
           }
           if(ID == 1){
               transformComponent.x = player.getX()+xStart+30;
           }
           if(ID == 2){
               transformComponent.x = player.getX()+xStart+60;
           }
       }

    }





}
