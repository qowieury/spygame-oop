package com.spy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spy.game.component.*;

import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by qowie on 11/11/2016.
 */
public class BaseStage extends ApplicationAdapter {
    protected String STAGE_NAME = "Stage1";

    private int playerCurrentStage =1;
    //protected final float VIEWPORT_X = 360;
    //protected final float VIEWPORT_Y = 200;

    protected final float VIEWPORT_X = 540;
    protected final float VIEWPORT_Y = 300;

    protected SceneLoader sceneLoader;
    protected Player player;
    protected ArrayList<Enemy> enemy = new ArrayList<Enemy>();
    protected ArrayList<ComponentWall> wall = new ArrayList<ComponentWall>();
    protected ArrayList<ComponentDoor> door = new ArrayList<ComponentDoor>();
    protected ArrayList<ComponentBox> box = new ArrayList<ComponentBox>();
    protected ArrayList<ComponentFloor> floor = new ArrayList<ComponentFloor>();
    protected ArrayList<ComponentBase> base = new ArrayList<ComponentBase>();
    protected ArrayList<ComponentItem> item = new ArrayList<ComponentItem>();

    protected ArrayList<ComponentComputer> com = new ArrayList<ComponentComputer>();

    protected ArrayList<ComponentGUI> GUI = new ArrayList<ComponentGUI>();

    protected CollisionListener collisionListener = new CollisionListener();


    protected Viewport viewport;
    protected ItemWrapper root;

    private float currentTime =0;
    private boolean isStarted = false;

    //protected float enemy

    private SaveFileIO saveFileIO;

    private GameOverScene gameOverScene;
    private boolean isDie = false;
    private WinScene winScene;
    private boolean isWin = false;


    @Override
    public void create() {
        try {
            saveFileIO = new SaveFileIO();
        }catch (IOException ex){
            throw new RuntimeException();


        }


        loadSceneAndViewport();



        createPlayer();
        createEnemy();
        initWallToOverride();
        initBoxToOverride();
        initFloorToOverride();
        initDoorToOverride();
        initBaseToOverride();
        initItemToOverride();

        initComToOverride();

        initGUI();

        addScriptToChildOfRoot();



        gameOverScene = new GameOverScene();
        root.getChild("gameover").addScript(gameOverScene);
        winScene = new WinScene();
        root.getChild("youwin").addScript(winScene);

        //enableTransForm();

    }

    private void enableTransForm(){
        player.transformComponent.enableTransform();
        for(int i=0;i<enemy.size();i++){
            enemy.get(i).transformComponent.enableTransform();
        }
        for(int i=0;i<box.size();i++){
            box.get(i).transformComponent.enableTransform();
        }
        for(int i=0;i<item.size();i++){
            item.get(i).transformComponent.enableTransform();
        }
    }

    protected void initGUI(){
        for (int i =0;i<3;i++){
            GUI.add(new ComponentGUI(i,player));
            System.out.println("GUI "+i);
            root.getChild("guiItem"+i).addScript(GUI.get(i));
        }


    }

    protected void initComToOverride(){initCom(3);}

    protected void initBoxToOverride() {
        initBox(1 + 6+4);
    }

    protected void initWallToOverride() {
        initWall(16 + 11+13);
    }

    protected void initDoorToOverride(){
        initDoor(0 + 5+4);
    }

    protected void initBaseToOverride(){
        initBase(0 + 5+4);
    }

    protected void initFloorToOverride() {
        initFloor(26 +43+42);
    }

    protected void initItemToOverride(){
        initItem(1+1+1 ,1+0+1,0 +1+1);
    }

    protected void initCom(int count){
        for (int i = 0; i < count; i++) {
            com.add(new ComponentComputer(9,sceneLoader.world));
        }

        for (int i = 0; i < count; i++) {
            System.out.println("com "+i);
            root.getChild("com" + i).addScript(com.get(i));
        }

    }

    protected void initItem(int a,int b,int c){
        for (int i =0;i<a;i++){
            item.add(new ComponentItem(0,sceneLoader.world));
            System.out.println("item0-"+i);
            root.getChild("itemID0-"+(i)).addScript(item.get(item.size()-1));
        }
        for (int i =0;i<b;i++){
            item.add(new ComponentItem(1,sceneLoader.world));
            System.out.println("item1- "+i);
            root.getChild("itemID1-"+(i)).addScript(item.get(item.size()-1));
        }
        for (int i =0;i<c;i++){
            item.add(new ComponentItem(2,sceneLoader.world));
            System.out.println("item2- "+i);
            root.getChild("itemID2-"+(i)).addScript(item.get(item.size()-1));
        }
    }

    protected void initBox(int boxCount) {
        for (int i = 0; i < boxCount; i++) {
            box.add(new ComponentBox(sceneLoader.world));
        }

        for (int i = 0; i < boxCount; i++) {
            System.out.println("box "+i);
            root.getChild("box" + i).addScript(box.get(i));
        }
    }

    protected void initBase(int baseCount){
        for (int i = 0; i < baseCount; i++) {
            base.add(new ComponentBase());
        }

        for (int i = 0; i < baseCount; i++) {
            //System.out.println("base "+i);
            root.getChild("base" + i).addScript(base.get(i));
        }

    }

    protected  void initDoor(int doorCount){
        for (int i = 0; i < doorCount; i++) {
        door.add(new ComponentDoor());
    }

        for (int i = 0; i < doorCount; i++) {
            System.out.println("door "+i);
            root.getChild("door" + i).addScript(door.get(i));
        }

    }

    protected void initFloor(int floorCount) {

        for (int i = 0; i < floorCount; i++) {
            floor.add(new ComponentFloor());
        }

        for (int i = 0; i < floorCount; i++) {
            System.out.println("floor "+i);
            root.getChild("floor" + i).addScript(floor.get(i));
        }

    }

    protected void initWall(int wallCount) {

        for (int i = 0; i < wallCount; i++) {
            wall.add(new ComponentWall());
        }

        for (int i = 0; i < wallCount; i++) {
           System.out.println("wall "+i);
            root.getChild("wall" + i).addScript(wall.get(i));
        }

    }

    protected void addScriptToChildOfRoot() {
        root.getChild("player").addScript(player);
        for(int i=0;i<enemy.size();i++){
            System.out.println("enemy "+i);
            root.getChild("enemy"+i).addScript(enemy.get(i));
        }

        //root.getChild("enemy1").addScript(enemy.get(1));

    }

    protected void createEnemy() {

        enemy.add(new Enemy(465, 755, sceneLoader.world));//0
        enemy.add(new Enemy(1185, 1250, sceneLoader.world));//1
        enemy.add(new Enemy(1055, 1120, sceneLoader.world));//2
        enemy.add(new Enemy(1090, 1155, sceneLoader.world));//3
        enemy.add(new Enemy(990, 1370, sceneLoader.world));//4
        enemy.add(new Enemy(1640, 1705, sceneLoader.world));//5
        enemy.add(new Enemy(1690, 1760, sceneLoader.world));//6
        enemy.add(new Enemy(2027, 2092, sceneLoader.world));//7
        enemy.add(new Enemy(3300, 3680, sceneLoader.world));//8
        enemy.add(new Enemy(3200, 4600, sceneLoader.world));//9
        enemy.add(new Enemy(3078, 3142, sceneLoader.world));//10
        enemy.add(new Enemy(2840, 3202, sceneLoader.world));//11
        //-------------------------end stage1----------------

        enemy.add(new Enemy(7417, 7570, sceneLoader.world));//12
        enemy.add(new Enemy(7618, 7950, sceneLoader.world));////13
        enemy.add(new Enemy(8290, 8360, sceneLoader.world));//14
        enemy.add(new Enemy(8186, 8255, sceneLoader.world));//15
        enemy.add(new Enemy(8096, 8156, sceneLoader.world));//16
        enemy.add(new Enemy(8005, 8073, sceneLoader.world));//17
        enemy.add(new Enemy(7949, 7964, sceneLoader.world));//18
        enemy.add(new Enemy(8440, 8549, sceneLoader.world));//19
        enemy.add(new Enemy(8549, 8668, sceneLoader.world));//20
        enemy.add(new Enemy(8668, 8773, sceneLoader.world));//21
        enemy.add(new Enemy(8773, 8870, sceneLoader.world));///-----22
        enemy.add(new Enemy(8870, 8980, sceneLoader.world));//23

        enemy.add(new Enemy(8980, 9082, sceneLoader.world));//24
        enemy.add(new Enemy(9082, 9159, sceneLoader.world));//25
        enemy.add(new Enemy(9159, 9257, sceneLoader.world));//26
        enemy.add(new Enemy(9400, 9600, sceneLoader.world));//27
        enemy.add(new Enemy(9919, 10035, sceneLoader.world));//28
        enemy.add(new Enemy(10630, 10800, sceneLoader.world));//29
        enemy.add(new Enemy(11415, 11589, sceneLoader.world));//30

        enemy.add(new Enemy(12128, 12447, sceneLoader.world));//31
        enemy.add(new Enemy(12525, 12720, sceneLoader.world));//32
        enemy.add(new Enemy(13123, 13241, sceneLoader.world));//33
        //---------------------end stage2--------------------


        enemy.add(new Enemy(16022, 16210, sceneLoader.world));//34
        enemy.add(new Enemy(15656, 15725, sceneLoader.world));//35
        enemy.add(new Enemy(15598, 15682, sceneLoader.world));//36
        enemy.add(new Enemy(15475, 15584, sceneLoader.world));//37
        enemy.add(new Enemy(15615, 15773, sceneLoader.world));//38
        enemy.add(new Enemy(15657, 15726, sceneLoader.world));//39
        enemy.add(new Enemy(15487, 15580, sceneLoader.world));//40
        enemy.add(new Enemy(16407, 15580, sceneLoader.world));//41
        enemy.add(new Enemy(16407, 16637, sceneLoader.world));//42
        enemy.add(new Enemy(16899, 167650, sceneLoader.world));//43
        enemy.add(new Enemy(16785, 16919, sceneLoader.world));///-----44
        enemy.add(new Enemy(18440, 18574, sceneLoader.world));//45

        enemy.add(new Enemy(18224, 18322, sceneLoader.world));//46
        enemy.add(new Enemy(17188, 17499, sceneLoader.world));//47
        //enemy.add(new Enemy(15580, 15778, sceneLoader.world));//48
        //---------------------end stage3--------------------


    }

    protected void createPlayer() {
        player = new Player(sceneLoader.world);
    }

    protected void loadSceneAndViewport() {
        viewport = new FitViewport(VIEWPORT_X, VIEWPORT_Y);
        sceneLoader = new SceneLoader();
        //sceneLoader.loadScene(STAGE_NAME, viewport);
        loadSceneByName();
        root = new ItemWrapper(sceneLoader.getRoot());
    }

    protected void loadSceneByName() {
        sceneLoader.loadScene(STAGE_NAME, viewport);
    }

    @Override
    public void render() {
        loadSceneRender();
        cameraFollowPlayer();
        detectPlayerCollisEnemy();
        detectPlayerCollisWall();
        detectPlayerCollisBox();
        detectPlayerCollisDoor();
        detectPlayerCollisItem();
        detectBoxCollisBase();
        detectBoxCollisWall();

        try {
            detectPlayerCollisCom();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isStarted) {
            if(!isDie && !isWin) {
                currentTime = currentTime + Gdx.graphics.getDeltaTime();
                System.out.println(currentTime);
            }
        }else{
            if(Gdx.input.isKeyPressed(Input.Keys.W) ||Gdx.input.isKeyPressed(Input.Keys.A) ||Gdx.input.isKeyPressed(Input.Keys.S) ||Gdx.input.isKeyPressed(Input.Keys.D) ){
                isStarted = true;
            }
        }

        if(isWin){
            winScene.transformComponent.x = player.getX()+10;
            winScene.transformComponent.y = player.getY()+10;
        }
        if(isDie){
            gameOverScene.transformComponent.x = player.getX()+10;
            gameOverScene.transformComponent.y = player.getY()+10;
        }
        if(player.transformComponent.y <-2080){
            die();
        }

    }
    protected void detectPlayerCollisCom() throws IOException {
        if (player.getPolygon() != null) {
            for (int i = 0; i < com.size(); i++) {
                if (com.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), com.get(i).getPolygon())) {
                        //System.out.println("player and com" + i);
                        if(playerCurrentStage == 1 ){
                            playerCurrentStage++;
                            player.transformComponent.x = 7200;
                            player.transformComponent.y = 100;
                        }else if(playerCurrentStage == 2){
                            playerCurrentStage++;
                            player.transformComponent.x = 15400;
                            player.transformComponent.y = -400;

                        }else if(playerCurrentStage ==3){
                            win();

                        }



                    }
                }
            }
        }

    }

    protected void detectPlayerCollisItem(){
        if (player.getPolygon() != null) {
            for (int i = 0; i < item.size(); i++) {
                if (item.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), item.get(i).getPolygon())) {
                        //System.out.println("player and item" + i);
                        if(player.collectItem(item.get(i))){
                            item.get(i).psudoDelete();
                        }

                    }
                }
            }
        }
    }

    protected void detectBoxCollisBase(){
        for (int i =0;i<door.size();i++){
            door.get(i).checktrig(base,box,i);
        }

        /*
        for(int i =0;i<base.size();i++){
            for (int j=0;j<box.size();j++){
                if(collisionListener.isCollision(box.get(j).getPolygon(),base.get(i).getPolygon())){
                    try {
                        //door.get(i).checkDoorTrigged();
                        base.get(i).trigTheDoor(door.get(i));
                        System.out.println("door "+i+ " trig");
                    }catch (IndexOutOfBoundsException outOfbound){
                       // System.out.println("outofbound");
                    }

                    System.out.println("box and Base Box "+i +" Base "+j );
                }else{
                    try {
                        door.get(i).cancelTrigged();
                    }catch (IndexOutOfBoundsException outOfBound){
                      //  System.out.println("outofbound");
                    }


                   // System.out.println("canceltriggg");
                }
            }
        }
        */
    }

    protected void detectPlayerCollisDoor(){
        if (player.getPolygon() != null) {
            for (int i = 0; i < door.size(); i++) {
                if (door.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), door.get(i).getPolygon())) {
                       // System.out.println("player and door" + i);
                        player.contactWall(door.get(i).getPolygon());
                    }
                }
            }
        }

    }

    protected void detectBoxCollisWall(){
        for(int i =0;i<box.size();i++){
            for (int j=0;j<wall.size();j++){
                if(collisionListener.isCollision(box.get(i).getPolygon(),wall.get(j).getPolygon())){
                    box.get(i).contactWall(wall.get(j).getPolygon());
                   // System.out.println("box and wall");
                }
            }
        }
    }

    protected void detectPlayerCollisBox(){
        if (player.getPolygon() != null) {
            for (int i = 0; i < box.size(); i++) {
                if (box.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), box.get(i).getPolygon())) {
                        //System.out.println("player and box" + i);
                        box.get(i).contactPlayer(player.getPolygon());
                        player.standOnBox(box.get(i));
                    }
                }
            }
        }

    }

    protected void detectPlayerCollisWall() {
        if (player.getPolygon() != null) {
            for (int i = 0; i < wall.size(); i++) {
                if (wall.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), wall.get(i).getPolygon())) {
                        //System.out.println("player and wall" + i); ////////////////
                        player.contactWall(wall.get(i).getPolygon());
                    }
                }
            }
        }
    }

    protected void detectPlayerCollisEnemy() {
        if (player.getPolygon() != null) {
            for (int i = 0; i < enemy.size(); i++) {
                if (enemy.get(i).getPolygon() != null) {
                    if (collisionListener.isCollision(player.getPolygon(), enemy.get(i).getPolygon())) {
                        if(!player.isHiding){
                            //System.out.println("player and enemy " + i);

                            die();
                        }
                    }
                }
            }
        }
    }



    protected void loadSceneRender() {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        //GL20.GL_
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());
    }

    protected void cameraFollowPlayer() {
        ((OrthographicCamera) viewport.getCamera()).position.set(player.getX() + 100, player.getY() + 70, 0f);

    }

    private void win() throws IOException {
        isWin = true;
            winScene.transformComponent.x = player.getX()+100;
            winScene.transformComponent.y = player.getY()+70;
        saveFileIO.saveScore((int)currentTime);
        player.transformComponent.disableTransform();

    }
    private void die(){
       isDie = true;
            gameOverScene.transformComponent.x = player.getX()+100;
            gameOverScene.transformComponent.y = player.getY()+70;
        player.transformComponent.disableTransform();


    }

}
