package net.periple.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import Action.*;
import net.periple.character.Kaisirak;
import net.periple.character.Knight;
import net.periple.character.Monster;
import net.periple.character.Personnage;
import net.periple.item.*;
import net.periple.screen.GameScreen;
import net.periple.screen.LoginScreen;

public class Periple extends Game {

	private static Periple game;
	private Emission e;
	private Reception r;
	private Socket socket;

	private LoginScreen loginScreen = new LoginScreen(this);
	private static GameScreen gameScreen = new GameScreen();

	private static boolean changeMap = false;

	private String map;

	private boolean initialized = false;

	private boolean isDisconnect = false;

	private int bLMap = -1;
	private Sprite sprite;
	private Sprite spriteMonster;

	private Personnage player;
	private int x = 0;
	private int y = 0;
	private List<Personnage> playerList = new ArrayList<Personnage>();
	private int num = -1;
	private int numPlayer = 0;
	// private long moveStat[] = new long[10];

	private static AssetManager assets = new AssetManager();

	// private static List<Texture> textures = new ArrayList<Texture>();
	// private static List<Sound> sounds = new ArrayList<Sound>();

	// private boolean ifLobby = false;
	private boolean inFight = false;
	private boolean isTurn = false;
	private long turnTime = 0;

	private List<Item> inventory = new ArrayList<Item>();
	private List<Integer> addInventory = new ArrayList<Integer>();
	private List<Integer> equInventory = new ArrayList<Integer>();
	private Item weapon = null;

	private List<String> friendWaitList = new ArrayList<String>();
	private List<String> friendList = new ArrayList<String>();

	private List<String> teamWaitList = new ArrayList<String>();
	private List<String> teamList = new ArrayList<String>();
	private String leaderName;

	private List<Monster> monsterList = new ArrayList<Monster>();
	private int numMonster = 0;
	// private long moveMonsterStat[] = new long[10];

	private List<Notif> notifs = new ArrayList<Notif>();
	private boolean changeNotif = true;

	private String musicName;
	private String musicFightName;
	private String newMusicFightName;
	private Music music;

	private List<String> monsters = new ArrayList<String>();
	private List<String> actions = new ArrayList<String>();
	// private List<Action> actions = new ArrayList<Action>();

	public void create() {
		System.out.println("start");
		game = this;

		assets.load("bin/img/SkyUppercut.PNG", Texture.class);
		assets.load("bin/img/angela.png", Texture.class);
		assets.load("bin/img/Psychic.png", Texture.class);
		assets.load("bin/img/knight.png", Texture.class);

		assets.load("bin/sound/snd_curtgunshot.ogg", Sound.class);
		assets.load("bin/sound/snd_flameloop.ogg", Sound.class);

		FileHandle folder = Gdx.files.internal("bin/img/Angela");
		listFilesForFolder(folder);
		folder = Gdx.files.internal("bin/img/Knight");
		listFilesForFolder(folder);
		folder = Gdx.files.internal("bin/img/Kaisirak");
		listFilesForFolder(folder);
		folder = Gdx.files.internal("bin/img/Effect");
		listFilesForFolder(folder);
		while (!assets.update()) {
			// System.out.println("Loaded: " + assets.getProgress() *100 + "%");
		}
		/*
		 * textures.add(new Texture("img/SkyUppercut.PNG")); textures.add(new
		 * Texture("img/mami.png")); textures.add(new
		 * Texture("img/Psychic.png")); textures.add(new
		 * Texture("img/angry_wolf.png")); textures.add(new
		 * Texture("img/Lightning.png"));
		 * 
		 * sounds.add(Gdx.audio.newSound(Gdx.files.internal(
		 * "sound/snd_curtgunshot.ogg")));
		 * sounds.add(Gdx.audio.newSound(Gdx.files.internal(
		 * "sound/snd_flameloop.ogg")));
		 */
		
		monsters.add("Knight");
		monsters.add("Kaisirak");

		actions.add("ActionPlayerAttack");
		actions.add("ActionMonsterAttack");
		actions.add("ActionPlayerSuperAttack");
		actions.add("ActionMonsterSuperAttack");
		/*
		 * actions.add(new ActionPlayerAttack("PlayerAttack", true,
		 * Gdx.audio.newSound(Gdx.files.internal("sound/snd_curtgunshot.ogg")),
		 * 0)); actions.add(new ActionMonsterAttack("MonsterAttack", false,
		 * Gdx.audio.newSound(Gdx.files.internal("sound/snd_flameloop.ogg")),
		 * 0)); actions.add(new ActionPlayerSuperAttack("PlayerSuperAttack",
		 * true,
		 * Gdx.audio.newSound(Gdx.files.internal("sound/snd_flameloop.ogg")),
		 * new Texture("img/SkyUppercut.PNG"), new Texture("img/mami.png"),
		 * 3000)); actions.add(new
		 * ActionMonsterSuperAttack("MonsterSuperAttack", false,
		 * Gdx.audio.newSound(Gdx.files.internal("sound/snd_flameloop.ogg")),
		 * new Texture("img/Psychic.png"), new Texture("img/angry_wolf.png"),
		 * 3000, new AttackSprite(500)));
		 */
		// inventory.add(new Sword());
		// inventory.add(new Bread());
		// inventory.add(new Bread());
		setScreen(loginScreen);
	}

	private void listFilesForFolder(FileHandle folder) {
		for (FileHandle file : folder.list()) {
			if (file.isDirectory()) {
				listFilesForFolder(file);
			} else if (file.extension().equals("png")) {
				System.out.println(file.path());
				assets.load(file.path(), Texture.class);
				// System.out.println(assets.get(file.path(),
				// Texture.class).getWidth());
			}
		}
	}

	public static AssetManager getAssets() {
		return assets;
	}

	public void Login() {
		load();
		setScreen(gameScreen);
	}

	public void render() {
		super.render();
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public static Periple getGame() {
		return game;
	}

	private void load() {
		map = "city";
		sprite = new Sprite(new Texture("bin/img/Player.png"));
		spriteMonster = new Sprite(new Texture("bin/img/Monster.png"));
		musicName = "mus_shop.ogg";
		changeMusic(musicName);
	}

	public void setMap(String nameMap, String numEnter) {
		if (teamList.size() == 0) {
			game.setLeaderName(player.getName());
		}
		player = null;
		StringTokenizer mapN = new StringTokenizer(nameMap);
		String mapT = mapN.nextToken();
		String mapName = mapN.nextToken();
		TiledMap mL = new TmxMapLoader().load("bin/maps/" + mapName + ".tmx");

		if (mL.getProperties().containsKey("Music")) {
			if (!inFight) {
				if (!mL.getProperties().get("Music").toString().equals(musicName)) {
					changeMusic(mL.getProperties().get("Music").toString());
				}
			}
			musicName = mL.getProperties().get("Music").toString();
		}

		if (mL.getProperties().containsKey("MusicFight")) {
			newMusicFightName = mL.getProperties().get("MusicFight").toString();
		}

		TiledMapTileLayer cL = (TiledMapTileLayer) mL.getLayers().get(1);
		String turn = "Down";
		int X = 0;
		int Y = 0;
		for (int x = 0; x < cL.getWidth(); x++) {
			for (int y = 0; y < cL.getHeight(); y++) {
				if (cL.getCell(x, y) != null) {
					if (cL.getCell(x, y).getTile().getProperties().containsKey("Enter")
							&& cL.getCell(x, y).getTile().getProperties().get("EnterNum").toString().equals(numEnter)) {
						turn = cL.getCell(x, y).getTile().getProperties().get("Enter").toString();
						X = x;
						Y = y;
						y = 1000;
						x = 1000;
						break;
					}
				}
			}
		}
		int i = 0;
		if (turn.equals("DOWN")) {
			i = 0;
			Y--;
		} else if (turn.equals("UP")) {
			i = 3;
			Y++;
		} else if (turn.equals("RIGHT")) {
			i = 2;
			X++;
		} else if (turn.equals("LEFT")) {
			i = 1;
			X--;
		}
		map = mapName;
		playerList.clear();
		// player.setMove(i, 1, X*32, Y*32);
		num = -1;
		numPlayer = 0;
		// moveStat = new long[10];
		monsterList = new ArrayList<Monster>();
		numMonster = 0;
		// moveMonsterStat = new long[10];
		turnTime = 0;
		setScreen(gameScreen);
		String mapID = null;
		try {
			Scanner sc = null;
			if (mapT.equals("HubMap") || mapT.equals("LobbyMap") || mapT.equals("Hub")) {
				sc = new Scanner(new File("mapID.txt"));
			} else if (mapT.equals("Lobby")) {
				sc = new Scanner(new File("LobbyMapID.txt"));
			}
			while (sc.hasNext()) {
				StringTokenizer line = new StringTokenizer(sc.nextLine());
				if (line.nextToken().equals(mapName)) {
					line.nextToken();
					mapID = line.nextToken();
					break;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("Le fichier n'existe pas !");
		}
		String sendMap = mapT + " " + mapID;
		e.Send(sendMap, Integer.toString(X * 32), Integer.toString(Y * 32));
	}

	public void setBLMap(int b) {
		bLMap = b;
	}

	public int getBLMap() {
		return bLMap;
	}

	public void setLMap(int mapID) {
		String mapName = null;
		try {
			Scanner sc = new Scanner(new File("FindLobbyMapID.txt"));
			while (sc.hasNext()) {
				StringTokenizer line = new StringTokenizer(sc.nextLine());
				if (line.nextToken().equals(Integer.toString(mapID))) {
					line.nextToken();
					mapName = line.nextToken();
					break;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("Le fichier n'existe pas !");
		}

		TiledMap mL = new TmxMapLoader().load("bin/maps/" + mapName + ".tmx");

		if (mL.getProperties().containsKey("Music")) {
			if (!inFight) {
				if (!mL.getProperties().get("Music").toString().equals(musicName)) {
					changeMusic(mL.getProperties().get("Music").toString());
				}
			}
			musicName = mL.getProperties().get("Music").toString();
		}

		if (mL.getProperties().containsKey("MusicFight")) {
			newMusicFightName = mL.getProperties().get("MusicFight").toString();
		}

		TiledMapTileLayer cL = (TiledMapTileLayer) mL.getLayers().get(1);
		String turn = "Down";
		int X = 0;
		int Y = 0;
		for (int x = 0; x < cL.getWidth(); x++) {
			for (int y = 0; y < cL.getHeight(); y++) {
				if (cL.getCell(x, y) != null) {
					if (cL.getCell(x, y).getTile().getProperties().containsKey("Enter")) {
						turn = cL.getCell(x, y).getTile().getProperties().get("Enter").toString();
						X = x;
						Y = y;
						y = 1000;
						x = 1000;
					}
				}
			}
		}
		int i = 0;
		if (turn.equals("DOWN")) {
			i = 0;
			Y--;
		} else if (turn.equals("UP")) {
			i = 3;
			Y++;
		} else if (turn.equals("RIGHT")) {
			i = 2;
			X++;
		} else if (turn.equals("LEFT")) {
			i = 1;
			X--;
		}
		map = mapName;
		player = null;
		playerList.clear();
		// player.setMove(i, 1, X*32, Y*32);
		num = -1;
		numPlayer = 0;
		monsterList = new ArrayList<Monster>();
		numMonster = 0;
		setScreen(gameScreen);
		bLMap = -1;
		// ifLobby = true;
	}

	public String getMap() {
		return map;
	}

	public int getNum() {
		return num;
	}

	public int getX() {
		return x;
	}

	public void setX(int X) {
		x = X;
	}

	public int getY() {
		return y;
	}

	public void setY(int Y) {
		y = Y;
	}

	public Personnage getPlayer() {
		return player;

	}

	public List<Personnage> getPlayerList() {
		return playerList;
	}

	public List<Monster> getMonsterList() {
		return monsterList;
	}

	// public long getMoveStat (int i) {
	// return moveStat[i];
	// }

	// public void setMoveStat (int i, long stat) {
	// moveStat[i] = stat;
	// }

	public int getNumPlayer() {
		return numPlayer;
	}

	public int getNumMonster() {
		return numMonster;
	}

	// public boolean isLobby () {
	// return ifLobby;
	// }

	// synchronized public GameScreen getScreen () {
	// return gameScreen;
	// }

	public void addPlayerList(int n, String name, String X, String Y, String life) {
		// System.out.println(n);
		addPlayer(name, X, Y, life);
		if (n == 1) {
			player = playerList.get(numPlayer - 1);
			x = Integer.parseInt(X);
			y = Integer.parseInt(Y);
			changeMap = false;
			num = numPlayer - 1;
		}
	}

	synchronized public void addPlayer(String name, String X, String Y, String life) {
		playerList.add(new Personnage(sprite, name, Integer.parseInt(life), 50));
		playerList.get(numPlayer).setPos(Integer.parseInt(X), Integer.parseInt(Y));
		if (inFight) {
			playerList.get(numPlayer).animation("FightBegin");
		}
		// playerList.get(numPlayer).setMove(0, 1, Integer.parseInt(X),
		// Integer.parseInt(Y));
		// moveStat[numPlayer] = 0L;
		numPlayer++;
	}

	synchronized public void createInventory(String invS, String equS, String friS) {
		StringTokenizer inv = new StringTokenizer(invS);
		while (inv.hasMoreTokens()) {
			int item = Integer.parseInt(inv.nextToken());
			addInventory.add(item);
		}
		StringTokenizer equ = new StringTokenizer(equS);
		while (equ.hasMoreTokens()) {
			int item = Integer.parseInt(equ.nextToken());
			equInventory.add(item);
		}
		StringTokenizer fri = new StringTokenizer(friS);
		while (fri.hasMoreTokens()) {
			String name = fri.nextToken();
			friendList.add(name);
		}
	}

	public void sendInventory() {
		String invS = "";
		String equS = "";
		for (int i = 0; i < inventory.size(); i++) {
			invS = invS + inventory.get(i).getID() + " ";
			if (inventory.get(i).isEquiped()) {
				equS = equS + i + " ";
			}
		}
		e.SendInventory(invS, equS);
	}

	public List<Integer> getCreateInventory() {
		return addInventory;
	}

	public void addInventory(List<Integer> inv) {
		for (Integer item : inv) {
			if (item == 0) {
				inventory.add(new Sword());
			} else if (item == 1) {
				inventory.add(new Bread());
			}
		}
		addInventory.clear();

		for (Integer item : equInventory) {
			inventory.get(item).setEquiped(true);
		}
		equInventory.clear();
	}

	public List<String> getFriendWait() {
		return friendWaitList;
	}

	public List<String> getTeamWait() {
		return teamWaitList;
	}

	public List<String> getFriend() {
		return friendList;
	}

	public List<String> getTeam() {
		return teamList;
	}

	public void addFriendWait(String name) {
		friendWaitList.add(name);
	}

	public void addTeamWait(String name) {
		teamWaitList.add(name);
	}

	public void addFriend(String name) {
		friendList.add(name);
		sendFriend();
	}

	public void addTeam(String name) {
		teamList.add(name);
	}

	public void removeFriendWait(String name) {
		for (int i = 0; i < friendWaitList.size(); i++) {
			if (friendWaitList.get(i).equals(name)) {
				friendWaitList.remove(i);
				i = friendWaitList.size();
			}
		}
	}

	public void removeTeamWait(String name) {
		for (int i = 0; i < teamWaitList.size(); i++) {
			if (teamWaitList.get(i).equals(name)) {
				teamWaitList.remove(i);
				i = teamWaitList.size();
			}
		}
	}

	public void removeFriend(String name) {
		for (int i = 0; i < friendList.size(); i++) {
			if (friendList.get(i).equals(name)) {
				friendList.remove(i);
				i = friendList.size();
			}
		}
		sendFriend();
	}

	public void removeTeam(String name) {
		for (int i = 0; i < teamList.size(); i++) {
			if (teamList.get(i).equals(name)) {
				teamList.remove(i);
				// i = teamList.size();
			}
		}
	}

	public void removeAllTeam() {
		teamList.clear();
		leaderName = null;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public void sendFriend() {
		String friS = "";
		for (int i = 0; i < friendList.size(); i++) {
			friS = friS + friendList.get(i);
		}
		e.SendFriend(friS);
	}

	public void addMonster(int id, int X, int Y) {
		try {
			Monster monster = (Monster) Class.forName("net.periple.character." + monsters.get(id)).getConstructor(Sprite.class, int.class, int.class).newInstance(spriteMonster, 150, 50);
			//Knight monster = new Knight(spriteMonster, 150, 50);
			monsterList.add(monster);
			monsterList.get(numMonster).setPos(X * 32, Y * 32);
			numMonster++;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	synchronized public void setPos(int i, int a, String msg) {
		// if(player != null){
		if (a == 0) {
			if (playerList.get(i).getPosX() < Integer.parseInt(msg)) {
				playerList.get(i).moveRight(inFight);
			} else {
				playerList.get(i).moveLeft(inFight);
			}
		} else if (a == 1) {
			if (playerList.get(i).getPosY() < Integer.parseInt(msg)) {
				playerList.get(i).moveUp(inFight);
			} else {
				playerList.get(i).moveDown(inFight);
			}
		}
		// }
		/*
		 * if(a == 0){ if(playerList.get(i).getX() < Integer.parseInt(msg)){
		 * playerList.get(i).setMove(3, Integer.parseInt(msg),
		 * (int)playerList.get(i).getY()); }else{ playerList.get(i).setMove(4,
		 * Integer.parseInt(msg), (int)playerList.get(i).getY()); } }else if(a
		 * == 1){ if(playerList.get(i).getY() < Integer.parseInt(msg)){
		 * playerList.get(i).setMove(1, (int)playerList.get(i).getX(),
		 * Integer.parseInt(msg)); }else{ playerList.get(i).setMove(2,
		 * (int)playerList.get(i).getX(), Integer.parseInt(msg)); } }
		 */
		// moveStat[i] = System.currentTimeMillis();
	}

	synchronized public void setPos(int i, int a) {
		playerList.get(i).setDir(a, inFight);
		// if(a == 0){
		// playerList.get(i).setMove(3);
		// }else if(a == 1){
		// playerList.get(i).setMove(0);
		// }else if(a == 2){
		// playerList.get(i).setMove(2);
		// }else if(a == 3){
		// playerList.get(i).setMove(1);
		// }
	}

	synchronized public void deletePlayer(int i) {
		playerList.remove(i);
		numPlayer--;
		if (num > i) {
			num--;
		}
	}

	synchronized public void setMonsterPos(int i, int x, int y) {
		try {
			if (monsterList.get(i).getPosX() < x * 32) {
				monsterList.get(i).moveRight();
			} else if (monsterList.get(i).getPosX() > x * 32) {
				monsterList.get(i).moveLeft();
			} else if (monsterList.get(i).getPosY() < y * 32) {
				monsterList.get(i).moveUp();
			} else if (monsterList.get(i).getPosY() > y * 32){
				monsterList.get(i).moveDown();
			}
		} catch (Exception e) {
			System.out.println("ERREUR : le monstre numéro " + i + " n'existe pas!");
		}

		/*
		 * if(a == 0){ if(monsterList.get(i).getX() < 32*msg){
		 * System.out.println("ok"); monsterList.get(i).setMove(3, 32*msg,
		 * (int)monsterList.get(i).getY()); }else{ monsterList.get(i).setMove(4,
		 * 32*msg, (int)monsterList.get(i).getY()); } }else if(a == 1){
		 * if(monsterList.get(i).getY() < 32*msg){ monsterList.get(i).setMove(1,
		 * (int)monsterList.get(i).getX(), 32*msg); }else{
		 * monsterList.get(i).setMove(2, (int)monsterList.get(i).getX(),
		 * 32*msg); } } moveMonsterStat[i] = System.currentTimeMillis();
		 */
	}

	synchronized public void setMonsterDir(int i, int a) {
		monsterList.get(i).setDir(a - 1);
		/*
		 * if(a == 1){ monsterList.get(i).setMove(7); }else if(a == 2){
		 * monsterList.get(i).setMove(4); }else if(a == 3){
		 * monsterList.get(i).setMove(6); }else if(a == 4){
		 * monsterList.get(i).setMove(5); }
		 */
	}

	synchronized public void setMonsterLife(int i, String l) {
		monsterList.get(i).receiveLife(Integer.parseInt(l));
		/*
		 * if(monsterList.get(i).getLife() <= 0){ monsterList.remove(i);
		 * numMonster--; }
		 */
	}

	public void dispear(boolean isPlayer, int i) {
		if (isPlayer) {
			deletePlayer(i);
		} else {
			monsterList.remove(i);
			numMonster--;
		}
	}

	synchronized public void recieveNotifQuestion(int ID, String type, String name) {
		Notif notif = new Notif(ID, type, name);
		notifs.add(notif);
		changeNotif = true;
	}

	synchronized public void recieveNotifInfo(int ID, String type, String info, String name) {
		Notif notif = new Notif(ID, type, info, name);
		notifs.add(notif);
		changeNotif = true;
	}

	public boolean getChangeMap() {
		return changeMap;
	}

	public void setChangeMap(boolean changeMap) {
		Periple.changeMap = changeMap;
	}

	public List<Item> getInventory() {
		return inventory;
	}

	public void interItem(Item item) {
		if (item.getType().equals("weapon")) {
			if (item.isEquiped()) {
				item.setEquiped(false);
			} else {
				item.setEquiped(true);
				weapon = item;
			}
		} else if (item.getType().equals("food")) {
			player.setLife(item.getFoodValue());
			e.SendLife(Integer.toString(player.getLife()));
			inventory.remove(item);
		}
		sendInventory();
	}

	public void setLife(int i, String l) {
		playerList.get(i).receiveLife(Integer.parseInt(l));
	}

	public boolean isInFight() {
		return inFight;
	}

	public void setInFight(boolean inFight) {
		boolean preInFight = this.inFight;
		this.inFight = inFight;
		if (preInFight) {
			if (inFight) {
				if (newMusicFightName != null && !musicFightName.equals(newMusicFightName)) {
					changeMusic(newMusicFightName);
					musicFightName = newMusicFightName;
				}
			} else {
				for (Personnage p : playerList) {
					p.animation("FightEnd");
				}
				changeMusic(musicName);
			}
		} else {
			if (inFight && newMusicFightName != null) {
				changeMusic(newMusicFightName);
				musicFightName = newMusicFightName;
			}
		}
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
		turnTime = System.currentTimeMillis();
	}

	public void addTurnTime(int time) {
		turnTime += time;
	}

	public long getTurnTime() {
		return turnTime;
	}

	public boolean isDisconnect() {
		return isDisconnect;
	}

	public void setDisconnect(boolean isDisconnect) {
		this.isDisconnect = isDisconnect;
	}

	public void disconnect() {
		/*
		 * isDisconnect = false;
		 * 
		 * player = null; playerList.clear(); num = -1; numPlayer = 0;
		 * monsterList = new ArrayList<Monster>(); numMonster = 0; turnTime = 0;
		 * music.stop(); music = null;
		 * 
		 * System.gc();
		 */

		Gdx.app.exit();
	}

	public List<Notif> getNotifList() {
		return notifs;
	}

	public void suppNotif(int i) {
		notifs.remove(i);
		changeNotif = true;
	}

	public boolean getChangeNotif() {
		return changeNotif;
	}

	public void setChangeNotif() {
		changeNotif = false;
	}

	private void changeMusic(String musicName) {
		if (music != null) {
			music.stop();
			music.dispose();
		}
		music = Gdx.audio.newMusic(Gdx.files.internal("bin/music/" + musicName));
		music.setLooping(true);
		music.play();
	}

	public void action(int idAction, int id, boolean isPlayer) {
		try {
			Action action = (Action) Class.forName("Action." + actions.get(idAction)).getConstructor().newInstance();
			if (isPlayer) {
				action.use(playerList.get(id));
			} else {
				action.use(monsterList.get(id));
			}
			gameScreen.addAction(action);
			addTurnTime(action.getWaitTime());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// actions.get(idAction).use(Integer.parseInt(x), Integer.parseInt(y));
		// game.addActionTurnTime(idAction);
	}

	public Emission getE() {
		return e;
	}

	public void setE(Emission e) {
		this.e = e;
	}

	public Reception getR() {
		return r;
	}

	public void setR(Reception r) {
		this.r = r;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	// public static Texture getTexture (int i) {
	// return textures.get(i);
	// }

	// public static Sound getSound (int i) {
	// return sounds.get(i);
	// }

	// public Item getWeapon() {
	// return weapon;
	// }

	// public void setWeapon(Item weapon) {
	// this.weapon = weapon;
	// }
}
