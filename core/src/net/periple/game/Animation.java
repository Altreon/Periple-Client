package net.periple.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.sun.prism.TextureMap;

public class Animation {
	
	private Sprite sprite;
	private List<Integer> progress = new ArrayList<Integer>();
	private List<Texture> frames = new ArrayList<Texture>();
	private List<Integer> milis = new ArrayList<Integer>();
	private List<Integer[]> framesHotSpot = new ArrayList<Integer[]>();
	private float totalMilis;
	//private float timeInterval;
	private long timeOld = 0;
	private int nbFrame = 0;
	private int frame = 0;
	
	private String name;
	
	public Animation (String nameFolder, String name, Sprite sprite) {
		this.name = name;
		FileHandle folder = Gdx.files.internal("bin/img/" + nameFolder + "/" + name);
		int n = 0;
		for(FileHandle file : folder.list()){
			if(file.extension().equals("png")){
				frames.add(Periple.getAssets().get("bin/img/" + nameFolder + "/" + name + "/" + n + ".png", Texture.class));
				n++;
			}
		}
		try {
			Scanner sc;
			sc = new Scanner(new File("bin/img/" + nameFolder + "/" + name + "/animation.txt"));
			StringTokenizer info = new StringTokenizer(sc.nextLine());
			sc.nextLine();
			while(sc.hasNext()){
				//String line = sc.nextLine();
				//StringTokenizer info = new StringTokenizer(line);
				int mili = sc.nextInt();
				milis.add(mili);
				Integer[] hotSpot = {sc.nextInt(), sc.nextInt()};
				framesHotSpot.add(hotSpot);
			}
			while(info.hasMoreTokens()){
				int f = Integer.parseInt(info.nextToken());
				progress.add(f);
				totalMilis += milis.get(f);
				nbFrame++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("animation.txt non trouvé pour l'animation \"" + name + "\" de \"" + nameFolder + "\"");
		} finally {
			for(int i=0; i<frames.size(); i++){
				progress.add(0);
				milis.add(100);
				Integer[] hotSpot = {0, 0};
				framesHotSpot.add(hotSpot);
			}
		}
		//timeInterval = time/nbFrame;
		this.sprite = sprite;
	}
	
	public void play(float x, float y){
		//if(direct || System.currentTimeMillis() - timeOld >= timeInterval){
			frame = 0;
			sprite.set(new Sprite(frames.get(progress.get(frame))));
			sprite.setPosition(x + framesHotSpot.get(progress.get(frame))[0], y + framesHotSpot.get(progress.get(frame))[1]);
		//}
			timeOld = System.currentTimeMillis();
	}
	
	public void update (float x, float y) {
		if(System.currentTimeMillis() - timeOld >= milis.get(progress.get(frame))){
			timeOld = System.currentTimeMillis() - ((System.currentTimeMillis() - timeOld) - milis.get(progress.get(frame)));
			nextFrame();
			//timeOld = System.currentTimeMillis() - ((System.currentTimeMillis() - timeOld) - milis.get(progress.get(frame)));
			//timeOld = System.currentTimeMillis();
		}
		sprite.setPosition(x + framesHotSpot.get(progress.get(frame))[0], y + framesHotSpot.get(progress.get(frame))[1]);
	}
	
	public void nextFrame() {
		if(frame >= nbFrame-1){
			//frame = 0;
		}else{
			frame++;
		}
		sprite.set(new Sprite(frames.get(progress.get(frame))));
	}
	
	public int getNbFrame() {
		return nbFrame;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public float getTotalMilis() {
		return totalMilis;
	}

	public boolean isFinish() {
		if(frame >= nbFrame-1 && System.currentTimeMillis() - timeOld >= milis.get(progress.get(nbFrame-1))){
			return true;
		}else{
			return false;
		}
	}
}
