package net.periple.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import Action.Action;
import net.periple.character.Monster;
import net.periple.character.Personnage;
import net.periple.game.Notif;
import net.periple.game.Periple;

public class GameScreen implements Screen{
	
	private Periple game;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private boolean cameraInizialised = false;
	
	private static TiledMapTileLayer collisionLayer;
	private static TiledMapTileLayer collisionLayer2;
	private static TiledMapTileLayer collisionLayer3;
	
	private Skin skin;
	
	private Personnage player;
	private int dir = 0;
	private boolean canCam = false;
	private boolean move = false;
	
	private Stage sTalk;
	private TextField talkField;
	private String talk = null;
	
	private Stage sInventory;
	private Window inventory;
	private SpriteBatch itemBatch;
	private boolean inv = false;
	private List<TextField> slots = new ArrayList<TextField>();
	private int ASlot = 0;
	
	private Stage sHUD;
	private Label name;
	private TextField HPContainer;
	private TextField HPBar;
	private Label HP;
	private TextField MPContainer;
	private TextField MPBar;
	private Label MP;
	private Image crown;
	
	private Stage sTeam;
	private Stage sChoose;
	private Stage sSelect;
	private TextField lFriend;
	private TextField lPlayer;
	private WidgetGroup playerSelected;
	private TextButton quitTeam;
	private int friendNum;
	private int playerNum;
	private boolean isTeam = false;
	
	private Stage sNotif;
		
	private List<Stage> sHUDL;
	private List<Image> iconL;
	private List<Label> nameL;
	private List<TextField> HPContainerL;
	private List<TextField> HPBarL;
	private List<Label> HPL;
	private List<TextField> MPContainerL;
	private List<TextField> MPBarL;
	private List<Label> MPL;
	private List<Image> crownL;
	
	private Stage sFight;
	private Label MA;
	private Label SA;
	private Label time;
	
	private static boolean action = false;
	private Stage sFightEffect;
	private static Image fightEffect1;
	private static Image fightEffect2;
	private static Image fightEffectPicture;
	/*private static Stage sMonsterFightEffect;
	private static Image monsterFightEffect1;
	private static Image monsterFightEffect2;
	private static Image monsterFightEffectPicture;*/
	private static boolean isPlayerAction;
	private static long fightEffectTime;
	private static Action actionUseRest;
	//private static int[] actionUseRestArgs = new int[2];
	
	private static List<Action> actions = new ArrayList<Action>();

	@Override
	public void show() {
		game = Periple.getGame();
		map = new TmxMapLoader().load("bin/maps/" + game.getMap() + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		itemBatch = new SpriteBatch();
		
		cameraInizialised = false;
		
		sTalk = new Stage();
		sInventory = new Stage();
		sHUD = new Stage();
		sFight = new Stage();
		sTeam = new Stage();
		sChoose = new Stage();
		sSelect = new Stage();
		sNotif = new Stage();
		sFightEffect = new Stage();
		//sMonsterFightEffect = new Stage();
		
		fightEffect1 = new Image(new Texture("bin/img/Psychic.png"));
		fightEffect1.setRotation(-90);
		fightEffect1.setSize(0, 544);
		fightEffect1.setPosition(0, 500-50);
		fightEffect2 = new Image(new Texture("bin/img/Psychic.png"));
		fightEffect2.setRotation(-90);
		fightEffect2.setSize(0, 544);
		fightEffect2.setPosition(544, 500-50);
		fightEffectPicture = new Image(new Texture("bin/img/angry_wolf.png"));
		fightEffectPicture.setSize(fightEffectPicture.getWidth(), fightEffectPicture.getHeight());
		fightEffectPicture.setPosition(544, 400);
		sFightEffect.addActor(fightEffect1);
		sFightEffect.addActor(fightEffect2);
		sFightEffect.addActor(fightEffectPicture);
		
		/*monsterFightEffect1 = new Image(new Texture("img/Psychic.png"));
		monsterFightEffect1.setRotation(-90);
		monsterFightEffect1.setSize(100, 544);
		monsterFightEffect1.setPosition(0, 150);
		monsterFightEffect2 = new Image(new Texture("img/Psychic.png"));
		monsterFightEffect2.setRotation(-90);
		monsterFightEffect2.setSize(100, 544);
		monsterFightEffect2.setPosition(544, 150);
		sMonsterFightEffect.addActor(monsterFightEffect1);
		sMonsterFightEffect.addActor(monsterFightEffect2);*/
		
		skin = new Skin(Gdx.files.internal("bin/uiskin.json"));
		
		talkField = new TextField("localhost", skin);
		talkField.setSize(544, 50);
		talkField.setPosition(0, 544-talkField.getHeight());
		
		inventory = new Window("Inventory", skin);
		inventory.setSize(544, 225);
		inventory.setPosition(0, 0);
		
		Image icon = new Image(new Texture("bin/img/Shield_and_swords.png"));
		icon.setPosition(0, 544-32);
		
		name = new Label("", skin);
		name.setAlignment(8);
		name.setSize(128, 32);
		name.setPosition(245+16, 544-name.getHeight());
		name.setColor(Color.WHITE);
		name.setFontScale(0.9f);
		
		HPContainer = new TextField("", skin);
		HPContainer.setSize(210, 16);
		HPContainer.setPosition(0+32, 544-HPContainer.getHeight());
		HPContainer.setColor(Color.BLACK);
		
		HPBar = new TextField("", skin);
		HPBar.setSize(200, 12);
		HPBar.setPosition(5+32, 544-HPBar.getHeight()-2);
		HPBar.setColor(Color.RED);
		
		HP = new Label("", skin);
		HP.setAlignment(7);
		HP.setSize(200, 12);
		HP.setPosition(5+32, 544-HP.getHeight()-1);
		HP.setColor(Color.WHITE);
		HP.setFontScale(0.7f);
		
		MPContainer = new TextField("", skin);
		MPContainer.setSize(210, 16);
		MPContainer.setPosition(0+32, 544-MPContainer.getHeight()-16);
		MPContainer.setColor(Color.BLACK);
		
		MPBar = new TextField("", skin);
		MPBar.setSize(200, 12);
		MPBar.setPosition(5+32, 544-MPBar.getHeight()-16-2);
		MPBar.setColor(Color.BLUE);
		
		MP = new Label("", skin);
		MP.setAlignment(7);
		MP.setSize(200, 12);
		MP.setPosition(5+32, 544-MP.getHeight()-16-1);
		MP.setColor(Color.WHITE);
		MP.setFontScale(0.7f);
		
		crown = new Image(new Texture("bin/img/Crown.png"));
		crown.setPosition(245, 544-16-8);
		//crown.setVisible(false);
		
		sTalk.addActor(talkField);
		sInventory.addActor(inventory);
		List<TextField> test = new ArrayList<TextField>();
		for(int iy=136; iy>=0; iy-=68){
			for(int ix=0; ix<544; ix+=68){
				TextField invField = new TextField("", skin);
				invField.setSize(66, 66);
				invField.setPosition(ix, iy);
				invField.setColor(Color.GRAY);
				sInventory.addActor(invField);
				slots.add(invField);
				test.add(invField);
			}
		}
		slots = test;
		sHUD.addActor(icon);
		sHUD.addActor(name);
		sHUD.addActor(HPContainer);
		sHUD.addActor(HPBar);
		sHUD.addActor(HP);
		sHUD.addActor(MPContainer);
		sHUD.addActor(MPBar);
		sHUD.addActor(MP);
		sHUD.addActor(crown);
		
		List<Image> iconLT = new ArrayList<Image>();
		List<Label> nameLT = new ArrayList<Label>();
		List<TextField> HPContainerLT = new ArrayList<TextField>();
		List<TextField> HPBarLT = new ArrayList<TextField>();
		List<Label> HPLT = new ArrayList<Label>();
		List<TextField> MPContainerLT = new ArrayList<TextField>();
		List<TextField> MPBarLT = new ArrayList<TextField>();
		List<Label> MPLT = new ArrayList<Label>();
		List<Stage> sHUDLT = new ArrayList<Stage>();
		List<Image> crownLT = new ArrayList<Image>();
		for(int i=0; i<4; i++){
			Image iconT = new Image(new Texture("bin/img/Shield_and_swords.png"));
			icon.setPosition(0, 544-32);
			iconLT.add(iconT);
			
			Label nameT = new Label("", skin);
			nameT.setAlignment(8);
			nameT.setSize(128, 32);
			nameT.setPosition(245+16, (544-nameT.getHeight()) - (40*(i+1)));
			nameT.setColor(Color.WHITE);
			nameT.setFontScale(0.9f);
			nameLT.add(nameT);
			
			
			TextField HPContainerT = new TextField("", skin);
			HPContainerT.setSize(210, 16);
			HPContainerT.setPosition(0+32, (544-HPContainerT.getHeight()) - (40*(i+1)));
			HPContainerT.setColor(Color.BLACK);
			HPContainerLT.add(HPContainerT);
			
			TextField HPBarT = new TextField("", skin);
			HPBarT.setSize(200, 12);
			HPBarT.setPosition(5+32, (544-HPBarT.getHeight()-2) - (40*(i+1)));
			HPBarT.setColor(Color.RED);
			HPBarLT.add(HPBarT);
			
			Label HPT = new Label("", skin);
			HPT.setAlignment(7);
			HPT.setSize(200, 12);
			HPT.setPosition(5+32, (544-HPT.getHeight()-1) - (40*(i+1)));
			HPT.setColor(Color.WHITE);
			HPT.setFontScale(0.7f);
			HPLT.add(HPT);
			
			TextField MPContainerT = new TextField("", skin);
			MPContainerT.setSize(210, 16);
			MPContainerT.setPosition(0+32, (544-MPContainerT.getHeight()-16) - (40*(i+1)));
			MPContainerT.setColor(Color.BLACK);
			MPContainerLT.add(MPContainerT);
			
			TextField MPBarT = new TextField("", skin);
			MPBarT.setSize(200, 12);
			MPBarT.setPosition(5+32, (544-MPBarT.getHeight()-16-2) - (40*(i+1)));
			MPBarT.setColor(Color.BLUE);
			MPBarLT.add(MPBarT);
			
			Label MPT = new Label("", skin);
			MPT.setAlignment(7);
			MPT.setSize(200, 12);
			MPT.setPosition(5+32, (544-MPT.getHeight()-16-1) - (40*(i+1)));
			MPT.setColor(Color.WHITE);
			MPT.setFontScale(0.7f);
			MPLT.add(MPT);
			
			Image crownT = new Image(new Texture("bin/img/Crown.png"));
			crownT.setPosition(245, 544-16-8);
			//crownT.setVisible(false);
			crownLT.add(crownT);
			
			Stage sHUDT = new Stage();
			sHUDT.addActor(iconT);
			sHUDT.addActor(nameT);
			sHUDT.addActor(HPContainerT);
			sHUDT.addActor(HPBarT);
			sHUDT.addActor(HPT);
			sHUDT.addActor(MPContainerT);
			sHUDT.addActor(MPBarT);
			sHUDT.addActor(MPT);
			sHUDT.addActor(crownT);
			sHUDLT.add(sHUDT);
		}
		iconL = iconLT;
		nameL = nameLT;
		HPContainerL = HPContainerLT;
		HPBarL = HPBarLT;
		HPL = HPLT;
		MPContainerL = MPContainerLT;
		MPBarL = MPBarLT;
		MPL = MPLT;
		crownL = crownLT;
		sHUDL = sHUDLT;
		
		MA = new Label("", skin);
		MA.setAlignment(7);
		MA.setSize(200, 20);
		MA.setPosition(400, 544-MA.getHeight());
		MA.setColor(Color.GREEN);
		
		SA = new Label("", skin);
		SA.setAlignment(7);
		SA.setSize(200, 20);
		SA.setPosition(400, 514-MA.getHeight());
		SA.setColor(Color.YELLOW);
		
		time = new Label("", skin);
		time.setAlignment(7);
		time.setSize(200, 20);
		time.setPosition(400, 30+time.getHeight());
		time.setColor(Color.RED);
		
		sFight.addActor(MA);
		sFight.addActor(SA);
		sFight.addActor(time);
		
		lFriend = new TextField("Liste amis", skin);
		lFriend.setSize(250, 30);
		lFriend.setPosition(22, 450);
		
		lPlayer = new TextField("Liste des joueurs present", skin);
		lPlayer.setSize(250, 30);
		lPlayer.setPosition(272, 450);

		final Table friendScrollTable = new Table();
		friendScrollTable.align(10);

		final ScrollPane friendScrollPane = new ScrollPane(friendScrollTable, skin);
		friendScrollPane.setFadeScrollBars(false);

		final Table friendTable = new Table();
		friendTable.setFillParent(true);
		friendTable.add(friendScrollPane).fill().size(250, 300);
		friendTable.setPosition(-125, 30);
		
		final Table PlayerScrollTable = new Table();
		PlayerScrollTable.align(10);

		final ScrollPane playerScrollPane = new ScrollPane(PlayerScrollTable, skin);
		playerScrollPane.setFadeScrollBars(false);
		
		Table tPlayer = new Table();
		tPlayer.setFillParent(true);
		tPlayer.add(playerScrollPane).fill().size(250, 300);
		tPlayer.setPosition(125, 30);
		
		friendNum = 0;
		playerNum = 0;
		
		playerSelected = new WidgetGroup();
		
		quitTeam = new TextButton("Quitter votre equipe actuelle", skin);
		quitTeam.setSize(250, 35);
		quitTeam.setPosition(272-quitTeam.getWidth()/2, 480);
		quitTeam.setVisible(false);
		quitTeam.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				game.getE().sendDeleteTeam(player.getName());
				quitTeam.setVisible(false);
			}
		});
		
		sTeam.addActor(lFriend);
		sTeam.addActor(lPlayer);
		sTeam.addActor(friendTable);
		sTeam.addActor(tPlayer);
		sTeam.addActor(playerSelected);
		sTeam.addActor(quitTeam);
		
		/*
		notif = new Window("Demande d'amis", skin);
		notif.setSize(500, 120);
		notif.setPosition(272-notif.getWidth()/2, 272-notif.getHeight()/2);
		notif.setTouchable(Touchable.disabled);
		
		notifMessage = new Label("Username vous demande en amis :", skin);
		notifMessage.setSize(500, 100);
		notifMessage.setPosition(22, 245);
		notifMessage.setAlignment(1);
		
		yes = new TextButton("Accepter", skin);
		yes.setSize(200, 50);
		yes.setPosition(300-notif.getWidth()/2, 230);
		
		no = new TextButton("Refuser", skin);
		no.setSize(200, 50);
		no.setPosition(522-notif.getWidth()/2, 230);
		
		sNotif.addActor(notif);
		sNotif.addActor(notifMessage);
		sNotif.addActor(yes);
		sNotif.addActor(no);
		*/
		
		/*
		camera = new OrthographicCamera();
		int width = 272;
		int height = 272;
		collisionLayer = (TiledMapTileLayer) map.getLayers().get(1);
		if(collisionLayer.getWidth() < 17){
			width = collisionLayer.getWidth() * 16;
		}else if(game.getPlayer() != null){
			width = Math.round(game.getPlayer().getX())+16;
		}
		if(collisionLayer.getHeight() < 17){
			height = collisionLayer.getHeight() * 16;
		}else if(game.getPlayer() != null){
			height = Math.round(game.getPlayer().getY())+16;
		}
		camera.position.set(width,height,0);
		camera.update();
		*/
		//if(player != null){
			//player.setMove(0, 1, 32, 32);
			//camera.position.set(player.getX(),player.getY(),0);
		//}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		List<Personnage> teamPlayer = new ArrayList<Personnage>();
		
		if(game.isDisconnect()){
			game.disconnect();
		}
		
		if(game.getBLMap() != -1){
			game.setLMap(game.getBLMap());
		}
		
		if(game.isTurn() && System.currentTimeMillis() - game.getTurnTime() >= 10000){
			game.setTurn(false);
			player.setMoveAction(5);
			player.setSkillAction(10);
		}
		
		if(game.getCreateInventory().size() > 0){
			game.addInventory(game.getCreateInventory());
		}
		
		if(game.getPlayer() != null){
			player = game.getPlayer();
			//System.out.println("Position  :  X = " + player.getPosX() + "  -  Y = " + player.getPosY());
			
			if(Gdx.input.isKeyJustPressed(Keys.O)){
				game.getE().sendDead();
				game.setDisconnect(true);
			}
			
			if(!cameraInizialised){
				//camera = new OrthographicCamera();
				int tileX = (int)(player.getPosX()/32);
				int tileY = (int)(player.getPosY()/32);
				int width = tileX;
				int height = tileY;
				collisionLayer = (TiledMapTileLayer) map.getLayers().get(1);
				if(collisionLayer.getWidth() < 17){
					width = collisionLayer.getWidth() * 16;
				}else{
					boolean isRight = false;
					if(tileX > collisionLayer.getWidth()-9){
						isRight = true;
					}
					if(isRight){
						while(tileX > collisionLayer.getWidth()-9){
							tileX--;
						}
					}else{
						while(tileX < 8){
							tileX++;
						}
					}
					width = tileX*32+16;
				}
				if(collisionLayer.getHeight() < 17){
					height = collisionLayer.getHeight() * 16;
				}else{
					boolean isUP = false;
					if(tileY > collisionLayer.getHeight()-9){
						isUP = true;
					}
					if(isUP){
						while(tileY > collisionLayer.getHeight()-9){
							tileY--;
						}
					}else{
						while(tileY < 8){
							tileY++;
						}
					}
					height = tileY*32+16;
				}
				camera.position.set(width,height,0);
				camera.update();
				cameraInizialised = true;
			}
			renderer.setView(camera);
			renderer.render();
			
			//System.out.println("isMove = " + player.isMove() + "  ||  getChangeMap = " + game.getChangeMap());

			if(player.isMove()){
				if(player.getPosX() < game.getX() && !move){
					game.setX(game.getX()-32);
					move = true;
				}else if(player.getPosX() > game.getX() && !move){
					game.setX(game.getX()+32);
					move = true;
				}
				if(player.getPosY() < game.getY() && !move){
					game.setY(game.getY()-32);
					move = true;
				}else if(player.getPosY() > game.getY() && !move){
					game.setY(game.getY()+32);
					move = true;
				}
			}else{
				move = false;
				canCam = false;
				int tileX = (int)(player.getPosX()/32);
				int tileY = (int)(player.getPosY()/32);
				boolean canMoveUp = true;
				boolean canMoveDown = true;
				boolean canMoveLeft = true;
				boolean canMoveRight = true;
				
				collisionLayer3 = (TiledMapTileLayer) map.getLayers().get(3);
				collisionLayer2 = (TiledMapTileLayer) map.getLayers().get(2);
				collisionLayer = (TiledMapTileLayer) map.getLayers().get(1);
				
				if(collisionLayer.getCell(tileX,tileY) != null){
					if(collisionLayer.getCell(tileX, tileY).getTile().getProperties().containsKey("Map")){
						game.setChangeMap(true);
						game.setMap(collisionLayer.getCell(tileX, tileY).getTile().getProperties().get("Map").toString(), collisionLayer.getCell(tileX, tileY).getTile().getProperties().get("NextNum").toString());
						//cameraInizialised = false;
					}
				}
				
				if(!game.getChangeMap()){
					if(collisionLayer.getCell(tileX,tileY + 1) != null){
						if(collisionLayer.getCell(tileX, tileY+1).getTile().getProperties().containsKey("Block")){
							canMoveUp = false;
						}
					}else{
						canMoveUp = false;
					}
					if(collisionLayer2.getCell(tileX,tileY + 1) != null){
						if(collisionLayer2.getCell(tileX, tileY+1).getTile().getProperties().containsKey("Block")){
							canMoveUp = false;
						}	
					}
					if(collisionLayer3.getCell(tileX,tileY + 1) != null){
						canMoveUp = false;
					}
		
					if(collisionLayer.getCell(tileX,tileY - 1) != null){ 
						if(collisionLayer.getCell(tileX, tileY-1).getTile().getProperties().containsKey("Block")){
							canMoveDown = false;
						}
					}else{
						canMoveDown = false;
					}
					if(collisionLayer2.getCell(tileX,tileY - 1) != null){
						if(collisionLayer2.getCell(tileX, tileY-1).getTile().getProperties().containsKey("Block")){
							canMoveDown = false;
						}
					}
					if(collisionLayer3.getCell(tileX,tileY - 1) != null){
						canMoveDown = false;
					}
		
					if(collisionLayer.getCell(tileX + 1,tileY) != null){
						if(collisionLayer.getCell(tileX+1, tileY).getTile().getProperties().containsKey("Block")){
							canMoveRight = false;
						}
					}else{
						canMoveRight = false;
					}
					if(collisionLayer2.getCell(tileX + 1,tileY) != null){
						if(collisionLayer2.getCell(tileX+1, tileY).getTile().getProperties().containsKey("Block")){
							canMoveRight = false;
						}
					}
					if(collisionLayer3.getCell(tileX + 1,tileY) != null){
						canMoveRight = false;
					}
		
					if(collisionLayer.getCell(tileX - 1,tileY) != null){
						if(collisionLayer.getCell(tileX-1, tileY).getTile().getProperties().containsKey("Block")){
							canMoveLeft = false;
						}
					}else{
						canMoveLeft = false;
					}
					if(collisionLayer2.getCell(tileX - 1,tileY) != null){
						if(collisionLayer2.getCell(tileX - 1, tileY).getTile().getProperties().containsKey("Block")){
							canMoveLeft = false;
						}
					}
					if(collisionLayer3.getCell(tileX - 1,tileY) != null){
						canMoveLeft = false;
					}
		
					int camPosX = (int)camera.position.x / 32;
					int camPosY = (int)camera.position.y / 32;
					
					if(game.isInFight() && player.getMoveAction() <= 0 || game.isInFight() && !game.isTurn() || action || player.getLife() <= 0){
						canMoveUp = false;
						canMoveDown = false;
						canMoveRight = false;
						canMoveLeft = false;
					}
					
					if(game.isInFight() && player.getMoveAction() <= 0 || game.isInFight() && game.isTurn()){
						List<Personnage> playerList = game.getPlayerList();
						List<Monster> monsterList = game.getMonsterList();
						for(Personnage p : playerList){
							if(player.getPosY() + 32 == p.getPosY() && player.getPosX() == p.getPosX()){
								canMoveUp = false;
							}
							if(player.getPosY() - 32 == p.getPosY() && player.getPosX() == p.getPosX()){
								canMoveDown = false;
							}
							if(player.getPosX() + 32 == p.getPosX() && player.getPosY() == p.getPosY()){
								canMoveRight = false;
							}
							if(player.getPosX() - 32 == p.getPosX() && player.getPosY() == p.getPosY()){
								canMoveLeft = false;
							}
						}
						for(Monster m : monsterList){
							if(m.getLife() > 0){
								if(m.isTouch(player.getPosX(), player.getPosY() + 32)){
									canMoveUp = false;
								}
								if(m.isTouch(player.getPosX(), player.getPosY() - 32)){
									canMoveDown = false;
								}
								if(m.isTouch(player.getPosX() + 32, player.getPosY())){
									canMoveRight = false;
								}
								if(m.isTouch(player.getPosX() - 32, player.getPosY())){
									canMoveLeft = false;
								}
							}
						}
					}
					
					//System.out.println("UP:" + canMoveUp + " -- DOWN:" + canMoveDown + " -- RIGHT:" + canMoveRight + " -- LEFT:" + canMoveLeft);
					
					//if(Gdx.input.isKeyPressed(Keys.UP) && System.currentTimeMillis() - game.getMoveStat(game.getNum()) > 100 && talk == null && !inv){
					if(Gdx.input.isKeyPressed(Keys.UP) && !player.isMove() && talk == null && !inv){
						dir = 1;
						if(canMoveUp){
							game.getE().Send(1, Integer.toString((int)player.getPosY()+32));
							player.moveUp(game.isInFight());
							//player.setMove(1, game.getX(), game.getPosY() + 32);
							//game.setMoveStat(game.getNum(), System.currentTimeMillis());
							if(game.isInFight()){
								player.setMoveAction(player.getMoveAction() - 1);
							}
							if(tileY != collisionLayer.getHeight()-9 && camPosY == tileY && collisionLayer.getHeight() > 17){
								canCam = true;
								//camera.translate(0, 32);
								//camera.update();
							}
						}else{
							if(player.getLife() > 0){
								player.setDir(0, game.isInFight());
								game.getE().Send(0);
							}
						}
					}else if(Gdx.input.isKeyJustPressed(Keys.UP) && inv && ASlot > 7){
						ASlot -= 8;
					}
					//if(Gdx.input.isKeyPressed(Keys.DOWN) && System.currentTimeMillis() - game.getMoveStat(game.getNum()) > 100 && talk == null && !inv){
					if(Gdx.input.isKeyPressed(Keys.DOWN) && !player.isMove() && talk == null && !inv){
						dir = 2;
						if(canMoveDown){
							game.getE().Send(1, Integer.toString((int)player.getPosY()-32));
							player.moveDown(game.isInFight());
							//player.setMove(2, game.getX(), game.getPosY() - 32);
							//game.setMoveStat(game.getNum(), System.currentTimeMillis());
							if(game.isInFight()){
								player.setMoveAction(player.getMoveAction() - 1);
							}
							if(tileY != 8 && camPosY == tileY && collisionLayer.getHeight() > 17){
								canCam = true;
								//camera.translate(0, -32);
								//camera.update();
							}
						}else{
							if(player.getLife() > 0){
								player.setDir(1, game.isInFight());
								game.getE().Send(1);
							}
						}
					}else if(Gdx.input.isKeyJustPressed(Keys.DOWN) && inv && ASlot < 16){
						ASlot += 8;
					}
					//if(Gdx.input.isKeyPressed(Keys.RIGHT) && System.currentTimeMillis() - game.getMoveStat(game.getNum()) > 100 && talk == null && !inv){
					if(Gdx.input.isKeyPressed(Keys.RIGHT) && !player.isMove() && talk == null && !inv){
						dir = 3;
						if(canMoveRight){
							game.getE().Send(0, Integer.toString((int)player.getPosX()+32));
							player.moveRight(game.isInFight());
							//player.setMove(3, game.getX() + 32, game.getPosY());
							//game.setMoveStat(game.getNum(), System.currentTimeMillis());
							if(game.isInFight()){
								player.setMoveAction(player.getMoveAction() - 1);
							}
							if(tileX != collisionLayer.getWidth()-9 && camPosX == tileX && collisionLayer.getWidth() > 17){
								canCam = true;
								//camera.translate(+32, 0);
								//camera.update();
							}
						}else{
							if(player.getLife() > 0){
								player.setDir(2, game.isInFight());
								game.getE().Send(2);
							}
						}
					}else if(Gdx.input.isKeyJustPressed(Keys.RIGHT) && inv && ASlot < 23){
						ASlot ++;
					}
					//if(Gdx.input.isKeyPressed(Keys.LEFT) && System.currentTimeMillis() - game.getMoveStat(game.getNum()) > 100 && talk == null && !inv){
					if(Gdx.input.isKeyPressed(Keys.LEFT) && !player.isMove() && talk == null && !inv){
						dir = 4;
						if(canMoveLeft){
							game.getE().Send(0, Integer.toString((int)player.getPosX()-32));
							player.moveLeft(game.isInFight());
							//player.setMove(4, game.getX() - 32, game.getPosY());
							//game.setMoveStat(game.getNum(), System.currentTimeMillis());
							if(game.isInFight()){
								player.setMoveAction(player.getMoveAction() - 1);
							}
							if(tileX != 8 && camPosX == tileX && collisionLayer.getWidth() > 17){
								canCam = true;
								//camera.translate(-32, 0);
								//camera.update();
							}
						}else{
							if(player.getLife() > 0){
								player.setDir(3, game.isInFight());
								game.getE().Send(3);
							}
						}
					}else if(Gdx.input.isKeyJustPressed(Keys.LEFT) && inv && ASlot > 0){
						ASlot --;
					}
				
					game.setX((int)player.getPosX());
					game.setY((int)player.getPosY());
					
					if(game.isInFight() && game.isTurn() && !action && actions.size() == 0 && player.getLife() > 0){
						if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
							game.setTurn(false);
							player.setMoveAction(5);
							player.setSkillAction(10);
							game.getE().SendEndTurn();
						}
					}
					
					if(game.isInFight() && game.isTurn() && !action && actions.size() == 0 && player.getLife() > 0){
						if(Gdx.input.isKeyJustPressed(Keys.NUM_1) && player.getSkillAction() > 0){
							game.getE().sendAction(dir, 0);
							//attackMonster(0);
						}
						if(Gdx.input.isKeyJustPressed(Keys.NUM_2) && player.getSkillAction() > 0){
							game.getE().sendAction(dir, 2);
							//attackMonster(1);
							//action = true;
							//fightEffectTime = System.currentTimeMillis();
						}
					}
					
					if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
						if(talk == null){
							if(dir == 1 && collisionLayer3.getCell(tileX,tileY+1) != null){
								talk = collisionLayer3.getCell(tileX, tileY+1).getTile().getProperties().get("Talk").toString();
							}else if(dir == 2 && collisionLayer3.getCell(tileX,tileY-1) != null){
								talk = collisionLayer3.getCell(tileX, tileY-1).getTile().getProperties().get("Talk").toString();
							}else if(dir == 3 && collisionLayer3.getCell(tileX+1,tileY) != null){
								talk = collisionLayer3.getCell(tileX+1, tileY).getTile().getProperties().get("Talk").toString();
							}else if(dir == 4 && collisionLayer3.getCell(tileX-1,tileY) != null){
								talk = collisionLayer3.getCell(tileX-1, tileY).getTile().getProperties().get("Talk").toString();
							}
						}else{
							talk = null;
						}
					}
					
					if(Gdx.input.isKeyJustPressed(Keys.I)){
						if(inv){
							inv = false;
						}else{
							inv = true;
							isTeam = false;
						}
					}
					
					if(Gdx.input.isKeyJustPressed(Keys.L)){
						//if(dir == 1){
							//Emission.SendLobby(Integer.toString((int)player.getX()), Integer.toString((int)player.getY()+32));
						//}
					}
					
					if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
						if(inv && ASlot < game.getInventory().size()){
							game.interItem(game.getInventory().get(ASlot));
						}
					}
					
					if(Gdx.input.isKeyJustPressed(Keys.F)){
						if(isTeam){
							isTeam = false;
							sChoose = new Stage();
							sSelect = new Stage();
							playerSelected.setVisible(false);
						}else{
							isTeam = true;
							inv = false;
						}
					}
				}
			
			}
			
			List<Integer> playerAtDispear = new ArrayList<Integer>();
			
			for(int i=0; i<game.getNumPlayer(); i++){
				game.getPlayerList().get(i).update(game.isInFight());
				if(game.getPlayerList().get(i).isDispear()){
					playerAtDispear.add(i);
					System.out.println("dispear");
					if(i == game.getNum()){
						System.out.println("you dispear");
						game.setDisconnect(true);
					}
				}
				/*
				if(game.getPlayerList().get(i).isMove()){
					setMove(i, true);
				}
				*/
			}
			for(int i=0; i<playerAtDispear.size(); i++){
				game.dispear(true, i);
			}
			for(int i=0; i<game.getNumMonster(); i++){
				game.getMonsterList().get(i).update();
				if(game.getMonsterList().get(i).isDispear()){
					game.dispear(false, i);
				}
				/*
				if(game.getMonsterList().get(i).isMove()){
					//setMove(i, false);
				}
				*/
			}
			
			renderer.getBatch().begin();
			//player.draw(renderer.getBatch());
			
			//Stage sCase = new Stage();
			for (Personnage other : game.getPlayerList()){
				
				if(game.isInFight() && game.isTurn()){
					other.drawCase(renderer.getBatch(), other.getPosX(), other.getPosY());
					/*Sprite caseSpace = new Sprite(new Texture("bin/img/greenCase.png"));
					for(int y=1; y<=player.getMoveAction(); y++){
						caseSpace.setPosition(player.getPosX(), player.getPosY()+32*y);
						caseSpace.setAlpha(0.25f);
						caseSpace.draw(renderer.getBatch());
						
						caseSpace.setPosition(player.getPosX(), player.getPosY()+32*-y);
						caseSpace.setAlpha(0.25f);
						caseSpace.draw(renderer.getBatch());
						
						caseSpace.setPosition(player.getPosX()+32*y, player.getPosY());
						caseSpace.setAlpha(0.25f);
						caseSpace.draw(renderer.getBatch());
						
						caseSpace.setPosition(player.getPosX()+32*-y, player.getPosY());
						caseSpace.setAlpha(0.25f);
						caseSpace.draw(renderer.getBatch());
						for(int x=player.getMoveAction()-y; x>0; x--){
							caseSpace.setPosition(player.getPosX()+32*x, player.getPosY()+32*y);
							caseSpace.setAlpha(0.25f);
							caseSpace.draw(renderer.getBatch());
							
							caseSpace.setPosition(player.getPosX()+32*-x, player.getPosY()+32*y);
							caseSpace.setAlpha(0.25f);
							caseSpace.draw(renderer.getBatch());
							
							caseSpace.setPosition(player.getPosX()+32*x, player.getPosY()+32*-y);
							caseSpace.setAlpha(0.25f);
							caseSpace.draw(renderer.getBatch());
							
							caseSpace.setPosition(player.getPosX()+32*-x, player.getPosY()+32*-y);
							caseSpace.setAlpha(0.25f);
							caseSpace.draw(renderer.getBatch());
						}
					}*/
				}
				
				other.draw(renderer.getBatch());
			}
			for (Monster other : game.getMonsterList()){
				if(game.isInFight() && game.isTurn()){
					other.drawCase(renderer.getBatch(), other.getPosX(), other.getPosY());
				}
				other.draw(renderer.getBatch());
			}
			for (Action other : actions){
				other.update(renderer.getBatch());
			}
			//sCase.draw();
			renderer.getBatch().end();
			
			for(int i=0; i<actions.size(); i++){
				if(actions.get(i).isFinish()){
					actions.remove(i);
				}
			}
			
			//canCam = false;
			if(canCam && dir == 1){
				camera.translate(0, (player.getY()+16)-camera.position.y);
				camera.update();
			}else if(canCam && dir == 2){
				camera.translate(0, (player.getY()+16)-camera.position.y);
				camera.update();
			}else if(canCam && dir == 3){
				camera.translate((player.getX()+16)-camera.position.x, 0);
				camera.update();
			}else if(canCam && dir == 4){
				camera.translate((player.getX()+16)-camera.position.x, 0);
				camera.update();
			}
			
			HP.setText("HP : "  + player.getLife() + " / " + player.getMaxLife());
			HPBar.setSize(200 * player.getLife()/player.getMaxLife(), 12);
			
			MP.setText("MP : "  + player.getMana() + " / " + player.getMaxMana());
			MPBar.setSize(200 * player.getMana()/player.getMaxMana(), 12);
			
			name.setText(player.getName());
			
			if(game.getLeaderName() != null && game.getLeaderName().equals(player.getName())){
				crown.setVisible(true);
			}else{
				crown.setVisible(false);
			}
			
			if(game.getTeam().size() > 0){
				for(int i=0; i<game.getPlayerList().size(); i++){
					if(i != game.getNum()){
						boolean b = false;
						for(int y=0; y<game.getTeam().size(); y++){
							if(game.getPlayerList().get(i).getName().equals(game.getTeam().get(y))){
								b = true;
								y = game.getTeam().size();
							}
						}
						if(b){
							teamPlayer.add(game.getPlayerList().get(i));
						}
					}
				}
			}
			
			for(int i=0; i<teamPlayer.size(); i++){
				HPL.get(i).setText("HP : "  + teamPlayer.get(i).getLife() + " / " + teamPlayer.get(i).getMaxLife());
				HPBarL.get(i).setSize(200 * teamPlayer.get(i).getLife()/teamPlayer.get(i).getMaxLife(), 12);
				
				MPL.get(i).setText("MP : "  + teamPlayer.get(i).getMana() + " / " + teamPlayer.get(i).getMaxMana());
				MPBarL.get(i).setSize(200 * teamPlayer.get(i).getMana()/teamPlayer.get(i).getMaxMana(), 12);
				
				nameL.get(i).setText(teamPlayer.get(i).getName());
				
				if(game.getLeaderName() != null && game.getLeaderName().equals(teamPlayer.get(i).getName())){
					crownL.get(i).setVisible(true);
				}else{
					crownL.get(i).setVisible(false);
				}
			}
			
			if(game.isInFight()){
				MA.setText("MA : " + player.getMoveAction());
				SA.setText("SA : " + player.getSkillAction());
				if(game.isTurn()){
					if(!action && actions.size() == 0){
						time.setText(Long.toString(10 - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - game.getTurnTime())));
					}
				}else{
					time.setText("0");
				}
			}

		}
		if(talk != null){
			talkField.setText(talk);
			sTalk.draw();
		}
		
		if(inv){
			sInventory.draw();
			itemBatch.begin();
			int iX = 1;
			int iY = 137;
			for(int i=0; i<24; i++){
				if(ASlot == i){
					slots.get(i).setColor(Color.GREEN);
				}else{
					slots.get(i).setColor(Color.GRAY);
				}
				
				if(i < game.getInventory().size()){
					game.getInventory().get(i).setPos(iX, iY);
					game.getInventory().get(i).draw(itemBatch);
				
					if(game.getInventory().get(i).isEquiped()){
						slots.get(i).setColor(Color.RED);
					}
				}
				
				if(iX == 136){
					iX = 1;
					iY -= 68;
				}else{
					iX += 68;
				}
			}
			itemBatch.end();
		}
		
		if(talk == null && !isTeam){
			sHUD.draw();
		}
		
		if(isTeam){
			if(game.getFriend().size() != friendNum){
				friendNum = 0;
				final Table FriendScrollTable = new Table();
				int i = 0;
				for(final String name : game.getFriend()){
					final int z = i;
					i++;
					final Label l = new Label(name, skin);
					l.setScaleX(500);
					l.addListener(new ClickListener() {
						@Override
						public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
							select(22, 450-28-(25*z), 1);
						}
						
						public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
							select(0, 0, 2);
						}
						
						public void clicked (InputEvent e, float x, float y) {
							select(22, 450-28-(25*z), 3);
							click(z, name);
						}
					});
					FriendScrollTable.add(l);
					FriendScrollTable.row();
					friendNum++;
				}
				FriendScrollTable.align(10);
				
				final ScrollPane friendScrollPane = new ScrollPane(FriendScrollTable, skin);
				friendScrollPane.setFadeScrollBars(false);
				
				Table tFriend = new Table();
				tFriend.setFillParent(true);
				tFriend.add(friendScrollPane).fill().size(250, 300);
				tFriend.setPosition(-125, 30);
				
				sTeam.addActor(tFriend);
			}
			
			if(game.getPlayerList().size() != playerNum){
				playerNum = game.getPlayerList().size();
				final Table PlayerScrollTable = new Table();
				int i = 0;
				for(Personnage p : game.getPlayerList()){
					if(!p.getName().equals(player.getName())){
						final int z = i;
						final String name = p.getName(); 
						i++;
						final Label l = new Label(name, skin);
						l.setScaleX(500);
						l.addListener(new ClickListener() {
							@Override
							public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
								select(272, 450-28-(25*z), 1);
							}
							
							public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
								select(0, 0, 2);
							}
							
							public void clicked (InputEvent e, float x, float y) {
								select(272, 450-28-(25*z), 3);
								click(z, name);
							}
						});
						PlayerScrollTable.add(l);
						PlayerScrollTable.row();
					}
				}
				PlayerScrollTable.align(10);
				
				final ScrollPane playerScrollPane = new ScrollPane(PlayerScrollTable, skin);
				playerScrollPane.setFadeScrollBars(false);
				
				Table tPlayer = new Table();
				tPlayer.setFillParent(true);
				tPlayer.add(playerScrollPane).fill().size(250, 300);
				tPlayer.setPosition(125, 30);
				
				sTeam.addActor(tPlayer);
			}
			
			sTeam.act(delta);
			sTeam.draw();
			sChoose.draw();
			sSelect.draw();
		}
		
		if(game.getNotifList().size() > 0){
			if(game.getChangeNotif()){
				sChoose = new Stage();
				sSelect = new Stage();
				playerSelected.setVisible(false);
				
				game.setChangeNotif();
				sNotif = new Stage();
				Gdx.input.setInputProcessor(sNotif);
				
				int id = 0;
				for(final Notif notif : game.getNotifList()){
					final int i = id;
					if(notif.getType().equals("friend")){
						Window notifW = null;
						notifW = new Window("Demande d'amis", skin);
						notifW.setSize(500, 120);
						notifW.setPosition(272-notifW.getWidth()/2, 272-notifW.getHeight()/2);
						notifW.setTouchable(Touchable.disabled);
				
						Label notifMessage = new Label(notif.getName() + " vous demande en amis :", skin);
						notifMessage.setSize(500, 100);
						notifMessage.setPosition(22, 245);
						notifMessage.setAlignment(1);
				
						TextButton yes = new TextButton("Accepter", skin);
						yes.setSize(200, 50);
						yes.setPosition(300-notifW.getWidth()/2, 230);
						yes.addListener(new ClickListener() {
							@Override
							public void clicked (InputEvent e, float x, float y) {
								game.addFriend(notif.getName());
								game.getE().sendFriendReply("yes", notif.getName());
								game.getE().sendDeleteNotif(notif.getID());
								game.suppNotif(i);
							}
						});
				
						TextButton no = new TextButton("Refuser", skin);
						no.setSize(200, 50);
						no.setPosition(522-notifW.getWidth()/2, 230);
						no.addListener(new ClickListener() {
							@Override
							public void clicked (InputEvent e, float x, float y) {
								game.getE().sendFriendReply("no", notif.getName());
								game.getE().sendDeleteNotif(notif.getID());
								game.suppNotif(i);
							}
						});
				
						sNotif.addActor(notifW);
						sNotif.addActor(notifMessage);
						sNotif.addActor(yes);
						sNotif.addActor(no);
					}else if(notif.getType().equals("friendReply")){
						Window notifW = null;
						notifW = new Window("Reponse a votre demande d'amis", skin);
						notifW.setSize(500, 120);
						notifW.setPosition(272-notifW.getWidth()/2, 272-notifW.getHeight()/2);
						notifW.setTouchable(Touchable.disabled);
				
						Label notifMessage = null;
						if(notif.getReply().equals("yes")){
							notifMessage = new Label(notif.getName() + " a accepte(e) votre demande d'amis", skin);
							game.addFriend(notif.getName());
						}else{
							notifMessage = new Label(notif.getName() + " a refuse(e) votre demande d'amis", skin);
						}
						notifMessage.setSize(500, 100);
						notifMessage.setPosition(22, 245);
						notifMessage.setAlignment(1);
				
						TextButton ok = new TextButton("OK", skin);
						ok.setSize(200, 50);
						ok.setPosition(422-notifW.getWidth()/2, 230);
						ok.addListener(new ClickListener() {
							@Override
							public void clicked (InputEvent e, float x, float y) {
								game.getE().sendDeleteNotif(notif.getID());
								game.suppNotif(i);
							}
						});
				
						sNotif.addActor(notifW);
						sNotif.addActor(notifMessage);
						sNotif.addActor(ok);
						game.removeFriendWait(notif.getName());
					}else if(notif.getType().equals("friendDelete")){
						game.removeFriend(notif.getName());
						Window notifW = null;
						notifW = new Window("Suppression d'amis", skin);
						notifW.setSize(500, 120);
						notifW.setPosition(272-notifW.getWidth()/2, 272-notifW.getHeight()/2);
						notifW.setTouchable(Touchable.disabled);
				
						Label notifMessage = null;
						notifMessage = new Label(notif.getName() + " vous a supprime(e) de ses amis", skin);
						notifMessage.setSize(500, 100);
						notifMessage.setPosition(22, 245);
						notifMessage.setAlignment(1);
				
						TextButton ok = new TextButton("OK", skin);
						ok.setSize(200, 50);
						ok.setPosition(422-notifW.getWidth()/2, 230);
						ok.addListener(new ClickListener() {
							@Override
							public void clicked (InputEvent e, float x, float y) {
								game.getE().sendDeleteNotif(notif.getID());
								game.suppNotif(i);
							}
						});
				
						sNotif.addActor(notifW);
						sNotif.addActor(notifMessage);
						sNotif.addActor(ok);
					}else if(notif.getType().equals("team")){
						Window notifW = null;
						notifW = new Window("Demande de rejoindre une equipe", skin);
						notifW.setSize(500, 120);
						notifW.setPosition(272-notifW.getWidth()/2, 272-notifW.getHeight()/2);
						notifW.setTouchable(Touchable.disabled);
				
						Label notifMessage = new Label(notif.getName() + " vous demande de rejoindre son equipe :", skin);
						notifMessage.setSize(500, 100);
						notifMessage.setPosition(22, 245);
						notifMessage.setAlignment(1);
				
						TextButton yes = new TextButton("Accepter", skin);
						yes.setSize(200, 50);
						yes.setPosition(300-notifW.getWidth()/2, 230);
						yes.addListener(new ClickListener() {
							@Override
							public void clicked (InputEvent e, float x, float y) {
								//game.addTeam(notif.getName());
								game.getE().sendTeamReply("yes", notif.getName());
								game.getE().sendDeleteNotif(notif.getID());
								game.suppNotif(i);
							}
						});
				
						TextButton no = new TextButton("Refuser", skin);
						no.setSize(200, 50);
						no.setPosition(522-notifW.getWidth()/2, 230);
						no.addListener(new ClickListener() {
							@Override
							public void clicked (InputEvent e, float x, float y) {
								game.getE().sendTeamReply("no", notif.getName());
								game.getE().sendDeleteNotif(notif.getID());
								game.suppNotif(i);
							}
						});
				
						sNotif.addActor(notifW);
						sNotif.addActor(notifMessage);
						sNotif.addActor(yes);
						sNotif.addActor(no);
					}else if(notif.getType().equals("teamReply")){
						Window notifW = null;
						notifW = new Window("Reponse a votre demande de rejoindre votre equipe", skin);
						notifW.setSize(500, 120);
						notifW.setPosition(272-notifW.getWidth()/2, 272-notifW.getHeight()/2);
						notifW.setTouchable(Touchable.disabled);
				
						Label notifMessage = null;
						if(notif.getReply().equals("yes")){
							notifMessage = new Label(notif.getName() + " a accepte(e) votre demande de rejoindre votre equipe", skin);
							//game.addTeam(notif.getName());
						}else{
							notifMessage = new Label(notif.getName() + " a refuse(e) votre demande de rejoindre votre equipe", skin);
						}
						notifMessage.setSize(500, 100);
						notifMessage.setPosition(22, 245);
						notifMessage.setAlignment(1);
				
						TextButton ok = new TextButton("OK", skin);
						ok.setSize(200, 50);
						ok.setPosition(422-notifW.getWidth()/2, 230);
						ok.addListener(new ClickListener() {
							@Override
							public void clicked (InputEvent e, float x, float y) {
								game.getE().sendDeleteNotif(notif.getID());
								game.suppNotif(i);
							}
						});
				
						sNotif.addActor(notifW);
						sNotif.addActor(notifMessage);
						sNotif.addActor(ok);
						game.removeTeamWait(notif.getName());
					}
					id++;
				}
			}
			sNotif.draw();
		}else{
			Gdx.input.setInputProcessor(sTeam);
		}
				
		if(game.getTeam().size() > 0 && talk == null && !isTeam){
			for(int i=0; i<teamPlayer.size(); i++){
				iconL.get(i).setPosition(0, (544-HPContainerL.get(i).getHeight()-16) - (40*(i+1)));
				nameL.get(i).setPosition(245+16, (544-nameL.get(i).getHeight()) - (40*(i+1)));
				HPContainerL.get(i).setPosition(0+32, (544-HPContainerL.get(i).getHeight()) - (40*(i+1)));
				HPBarL.get(i).setPosition(5+32, (544-HPBarL.get(i).getHeight()-2) - (40*(i+1)));
				HPL.get(i).setPosition(5+32, (544-HPL.get(i).getHeight()-1) - (40*(i+1)));
				MPContainerL.get(i).setPosition(0+32, (544-MPContainerL.get(i).getHeight()-16) - (40*(i+1)));
				MPBarL.get(i).setPosition(5+32, (544-MPBarL.get(i).getHeight()-16-2) - (40*(i+1)));
				MPL.get(i).setPosition(5+32, (544-MPL.get(i).getHeight()-16-1) - (40*(i+1)));
				crownL.get(i).setPosition(245, (544-16-8) - (40*(i+1)));
				sHUDL.get(i).draw();
			}
		}
		
		if(game.getTeam().size() > 0 || (game.getLeaderName() != null && game.getLeaderName().equals(player.getName()))){
			quitTeam.setVisible(true);
		}
		
		if(game.isInFight()){
			sFight.draw();
		}
		//System.currentTimeMillis() - fightEffectTime < 2000
		if(action){
			sFightEffect.draw();
			if(isPlayerAction){
				fightEffect1.setX(fightEffect1.getX()-15);
				fightEffect2.setX(fightEffect2.getX()-15);
				if(fightEffect1.getX() < -544){
					fightEffect1.setX(530);
				}
				if(fightEffect2.getX() < -544){
					fightEffect2.setX(530);
				}
				if(System.currentTimeMillis() - fightEffectTime < 2000){
					if(fightEffect1.getWidth() < 100){
						fightEffect1.setWidth(fightEffect1.getWidth()+4);
						fightEffect1.setY(fightEffect1.getY()+2);
					}
					if(fightEffect2.getWidth() < 100){
						fightEffect2.setWidth(fightEffect2.getWidth()+4);
						fightEffect2.setY(fightEffect2.getY()+2);
					}else if(fightEffectPicture.getX() > 50){
						fightEffectPicture.setX(fightEffectPicture.getX() - 20);
					}else if(System.currentTimeMillis() - fightEffectTime > 1500 && fightEffectPicture.getX() > -fightEffectPicture.getWidth()){
						fightEffectPicture.setX(fightEffectPicture.getX() - 20);
					}
				}else if(System.currentTimeMillis() - fightEffectTime < 3000){
					if(fightEffect1.getWidth() > 0){
						fightEffect1.setWidth(fightEffect1.getWidth()-4);
						fightEffect1.setY(fightEffect1.getY()-2);
					}
					if(fightEffect2.getWidth() > 0){
						fightEffect2.setWidth(fightEffect2.getWidth()-4);
						fightEffect2.setY(fightEffect2.getY()-2);
					}
				}else{
					fightEffectPicture.setX(544);
					action = false;
					/*
					if(actionUseRest != null){
						actionUseRest.useEffect();
						actionUseRest = null;
					}
					*/
				}
			}else{
				fightEffect1.setX(fightEffect1.getX()+15);
				fightEffect2.setX(fightEffect2.getX()+15);
				if(fightEffect1.getX() > 544){
					fightEffect1.setX(-530);
				}
				if(fightEffect2.getX() > 544){
					fightEffect2.setX(-530);
				}
				if(System.currentTimeMillis() - fightEffectTime < 2000){
					if(fightEffect1.getWidth() < 100){
						fightEffect1.setWidth(fightEffect1.getWidth()+4);
						fightEffect1.setY(fightEffect1.getY()+2);
					}
					if(fightEffect2.getWidth() < 100){
						fightEffect2.setWidth(fightEffect2.getWidth()+4);
						fightEffect2.setY(fightEffect2.getY()+2);
					}else if(fightEffectPicture.getX() < 544-50-fightEffectPicture.getWidth()){
						fightEffectPicture.setX(fightEffectPicture.getX() + 20);
					}else if(System.currentTimeMillis() - fightEffectTime > 1500 && fightEffectPicture.getX() > fightEffectPicture.getWidth()){
						fightEffectPicture.setX(fightEffectPicture.getX() + 20);
					}
				}else if(System.currentTimeMillis() - fightEffectTime < 3000){
					if(fightEffect1.getWidth() > 0){
						fightEffect1.setWidth(fightEffect1.getWidth()-4);
						fightEffect1.setY(fightEffect1.getY()-2);
					}
					if(fightEffect2.getWidth() > 0){
						fightEffect2.setWidth(fightEffect2.getWidth()-4);
						fightEffect2.setY(fightEffect2.getY()-2);
					}
				}else{
					fightEffectPicture.setX(-544);
					action = false;
					/*
					if(actionUseRest != null){
						actionUseRest.useEffect();
						actionUseRest = null;
					}
					*/
				}
			}
		}/*
		
		if(false){
			sMonsterFightEffect.draw();
			monsterFightEffect1.setX(monsterFightEffect1.getX()-15);
			monsterFightEffect2.setX(monsterFightEffect2.getX()-15);
			if(monsterFightEffect1.getX() < -544){
				monsterFightEffect1.setX(530);
			}
			if(monsterFightEffect2.getX() < -544){
				monsterFightEffect2.setX(530);
			}
		}*/
	}
	
	private void attackMonster(int actionID){
		/*
		int skillAct = 0;
		int life = 0;
		if(actionID == 0){
			skillAct = 1;
			life = 10;
		}else if(actionID == 1){
			skillAct = 5;
			life = 50;
		}
		
		List<Personnage> playerList = game.getPlayerList();
		List<Monster> monsterList = game.getMonsterList();
		player.setSkillAction(player.getSkillAction() - skillAct);
		int mID = 0;
		for(Monster m : monsterList){
			if(dir == 1 && player.getY() + 32 == m.getY() && player.getX() == m.getX()){
				m.setLife(-life);
				Emission.SendMonsterLife(dir, Integer.toString(m.getLife()));
				if(m.getLife() <= 0){
					game.setMonsterLife(mID, "0");
				}
				break;
			}else if(dir == 2 && player.getY() - 32 == m.getY() && player.getX() == m.getX()){
				m.setLife(-life);
				Emission.SendMonsterLife(dir, Integer.toString(m.getLife()));
				if(m.getLife() <= 0){
					game.setMonsterLife(mID, "0");
				}
				break;
			}else if(dir == 3 && player.getX() + 32 == m.getX() && player.getY() == m.getY()){
				m.setLife(-life);
				Emission.SendMonsterLife(dir, Integer.toString(m.getLife()));
				if(m.getLife() <= 0){
					game.setMonsterLife(mID, "0");
				}
				break;
			}else if(dir == 4 && player.getX() - 32 == m.getX() && player.getY() == m.getY()){
				m.setLife(-life);
				Emission.SendMonsterLife(dir, Integer.toString(m.getLife()));
				if(m.getLife() <= 0){
					game.setMonsterLife(mID, "0");
				}
				break;
			}
			mID++;
		}
		*/
	}
	
	private void select (float x, float y, int mode){
		if(mode == 1){
			TextArea b = new TextArea("", skin);
			b.setColor(0,0,1,0.5f);
			b.setSize(250, 30);
			b.setPosition(x, y);
			sChoose = new Stage();
			sChoose.addActor(b);
		}else if(mode == 2){
			sChoose = new Stage();
		}else{
			TextArea b = new TextArea("", skin);
			b.setColor(0,1,0,0.5f);
			b.setSize(250, 30);
			b.setPosition(x, y);
			sSelect = new Stage();
			sSelect.addActor(b);
		}
	}
	
	private void click (int i, String name){		
		Window wSelected = new Window("Joueur selectionne", skin);
		wSelected.setSize(500, 100);
		wSelected.setPosition(22, 52);
		wSelected.setTouchable(Touchable.disabled);
		
		Label namePlayer = new Label(name, skin);
		namePlayer.setAlignment(10);
		namePlayer.setSize(200, 40);
		namePlayer.setPosition(50, 70);
		
		//TextButton addTeam = new TextButton("Inviter dans l'équipe", skin);
		//addTeam.setSize(250, 50);
		//addTeam.setPosition(250, 70);
		
		playerSelected.addActor(wSelected);
		playerSelected.addActor(namePlayer);
		
		final String n = name;
		
		boolean inTeam = false;
		for(String p : game.getTeam()){
			if(p.equals(name)){
				inTeam = true;
			}
		}
		boolean inTeamWait = false;
		for(String p : game.getTeamWait()){
			if(p.equals(name)){
				inTeamWait = true;
			}
		}
		
		boolean inFriend = false;
		for(String p : game.getFriend()){
			if(p.equals(name)){
				inFriend = true;
			}
		}
		boolean inFriendWait = false;
		for(String p : game.getFriendWait()){
			if(p.equals(name)){
				inFriendWait = true;
			}
		}
		
		if(!inTeam){
			if(inTeamWait){
				TextButton addTeamWait = new TextButton("En attente de reponse", skin);
				addTeamWait.setSize(250, 35);
				addTeamWait.setPosition(250, 95);
				addTeamWait.clearListeners();
				playerSelected.addActor(addTeamWait);
			}else{
				TextButton addTeam = new TextButton("Ajouter dans votre equipe", skin);
				addTeam.setSize(250, 35);
				addTeam.setPosition(250, 95);
				addTeam.addListener(new ClickListener() {
					@Override
					public void clicked (InputEvent e, float x, float y) {
						game.addTeamWait(n);
						game.getE().SendRequestTeam(n);
						if(game.getTeam().size() == 0){
							game.setLeaderName(player.getName());
						}
						sChoose = new Stage();
						sSelect = new Stage();
						playerSelected.setVisible(false);
					}
				});
				playerSelected.addActor(addTeam);
			}
		}else if(game.getTeam().size() > 0 && game.getLeaderName().equals(player.getName())){
			TextButton suppTeam = new TextButton("Supprimer de votre équipe", skin);
			suppTeam.setSize(250, 35);
			suppTeam.setPosition(250, 95);
			suppTeam.addListener(new ClickListener() {
				@Override
				public void clicked (InputEvent e, float x, float y) {
					game.getE().sendDeleteTeam(n);
					//game.removeFriend(n);
					sChoose = new Stage();
					sSelect = new Stage();
					playerSelected.setVisible(false);
				}
			});
			playerSelected.addActor(suppTeam);
		}
		
		if(!inFriend){
			if(inFriendWait){
				TextButton addFriendWait = new TextButton("En attente de reponse", skin);
				addFriendWait.setSize(250, 35);
				addFriendWait.setPosition(250, 55);
				addFriendWait.clearListeners();
				playerSelected.addActor(addFriendWait);
			}else{
				TextButton addFriend = new TextButton("Envoyer une demande d'amis", skin);
				addFriend.setSize(250, 35);
				addFriend.setPosition(250, 55);
				addFriend.addListener(new ClickListener() {
					@Override
					public void clicked (InputEvent e, float x, float y) {
						game.addFriendWait(n);
						game.getE().SendRequestFriend(n);
						sChoose = new Stage();
						sSelect = new Stage();
						playerSelected.setVisible(false);
					}
				});
				playerSelected.addActor(addFriend);
			}
		}else{
			TextButton suppFriend = new TextButton("Supprimer de votre liste d'amis", skin);
			suppFriend.setSize(250, 35);
			suppFriend.setPosition(250, 55);
			suppFriend.addListener(new ClickListener() {
				@Override
				public void clicked (InputEvent e, float x, float y) {
					game.getE().sendDeleteFriend(n);
					game.removeFriend(n);
					sChoose = new Stage();
					sSelect = new Stage();
					playerSelected.setVisible(false);
				}
			});
			playerSelected.addActor(suppFriend);
		}
		playerSelected.setVisible(true);
	}
	
	public static void fightEffect(Texture fightEffect, Texture fightPicture, boolean playerAction){
		fightEffect1.setDrawable(new SpriteDrawable(new Sprite(fightEffect)));
		fightEffect2.setDrawable(new SpriteDrawable(new Sprite(fightEffect)));
		fightEffectPicture.setDrawable(new SpriteDrawable(new Sprite(fightPicture)));
		fightEffectPicture.setSize(fightPicture.getWidth(), 100);
		
		isPlayerAction = playerAction;
		if(playerAction){
			fightEffect1.setPosition(0, 500-50);
			fightEffect2.setPosition(544, 500-50);
			fightEffectPicture.setPosition(544, 400);
		}else{
			fightEffect1.setPosition(0, 144-50);
			fightEffect2.setPosition(-544, 144-50);
			fightEffectPicture.setPosition(-544, 44);
		}
		
		action = true;
		fightEffectTime = System.currentTimeMillis();
	}
	
	public static void fightEffect(Texture fightEffect, Texture fightPicture, boolean playerAction, Action actionUse){
		fightEffect1.setDrawable(new SpriteDrawable(new Sprite(fightEffect)));
		fightEffect2.setDrawable(new SpriteDrawable(new Sprite(fightEffect)));
		fightEffectPicture.setDrawable(new SpriteDrawable(new Sprite(fightPicture)));
		fightEffectPicture.setSize(fightPicture.getWidth(), 100);
		
		isPlayerAction = playerAction;
		if(playerAction){
			fightEffect1.setPosition(0, 500-50);
			fightEffect2.setPosition(544, 500-50);
			fightEffectPicture.setPosition(544, 400);
		}else{
			fightEffect1.setPosition(0, 144-50);
			fightEffect2.setPosition(-544, 144-50);
			fightEffectPicture.setPosition(-544, 44);
		}
		
		action = true;
		fightEffectTime = System.currentTimeMillis();
		actionUseRest = actionUse;
	}

	@Override
	public void resize(int width, int height) {
		//if(cameraInizialised){
			camera.viewportWidth = width;
			camera.viewportHeight = height;
			camera.update();
		//}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();

	}
	
	@Override
	public void dispose() {
		if(player != null){
			//map.dispose();
			//renderer.dispose();
		}
	}
	
	/*
	private void setMove (int i, boolean isPlayer){
		//System.out.println(game.getMoveStat(i) + " : " + System.currentTimeMillis());
		//if(System.currentTimeMillis() - game.getMoveStat(i) > 100){
			//game.setMoveStat(i, System.currentTimeMillis());
			if(isPlayer){
				game.getPlayerList().get(i).makeMove();
			}else{
				game.getMonsterList().get(i).makeMove();
			}
		//}
	}
	*/
	
	public static boolean isBlock (int tileX, int tileY) {
		boolean b = false;
		if(collisionLayer.getCell(tileX,tileY) != null){
			if(collisionLayer.getCell(tileX, tileY).getTile().getProperties().containsKey("Block")){
				b = true;
			}
		}else{
			b = true;
		}
		if(collisionLayer2.getCell(tileX,tileY) != null){
			if(collisionLayer2.getCell(tileX, tileY).getTile().getProperties().containsKey("Block")){
				b = true;
			}	
		}
		if(collisionLayer3.getCell(tileX,tileY) != null){
			b = true;
		}
		return b;
	}
	
	public void addAction (Action action) {
		actions.add(action);
	}
	
	/*
	public static void addActionEffect (Action actionEffect) {
		actionsEffect.add(actionEffect);
		//if(gameScreen.isBlock((int)spriteAttack.getX()/32, (int)spriteAttack.getY()/32)){
			//spritesAttack.add(spriteAttack);
		//}
	}
	*/
}

