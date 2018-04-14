package net.periple.screen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.periple.game.ClientServeur;
import net.periple.game.Periple;

public class LoginScreen implements Screen {
	
	private Periple g;
	int nu = 0;
	private Stage sServerList;
	private List<WidgetGroup> serverList = new ArrayList<WidgetGroup>();
	private TextButton addServer;
	private Slider slider;
	
	private Stage sConnexion;
	private TextButton btnLogin;
	private TextField username;
	private TextField password;
	
	private boolean sl = true;
	private Label error;
	
	//private boolean firstLogin = true;
	
	private static Socket socket;
	private static PrintWriter out;
	private static BufferedReader in;
	
	public static Thread t2;
	
	public LoginScreen (Periple game) {
		g = game;
	}
	
	@Override
	public void show() {
		sServerList = new Stage();
		sConnexion = new Stage();
		
		final Skin skin = new Skin(Gdx.files.internal("bin/uiskin.json"));
		
		slider = new Slider(0, 0, 1, true, skin);
		slider.setValue(0);
		slider.setSize(50, 440);
		slider.setPosition(505, 100);
		
		error = new Label("", skin);
		error.setColor(new Color(255, 0, 0, 1));
		error.setAlignment(1);
		error.setPosition(272-error.getWidth()/2, 115);
		
		try {
			Scanner sc;
			sc = new Scanner(new File("ServerList.txt"));
			int num = 1;
			while(sc.hasNext()){
				String line = sc.nextLine();
				StringTokenizer l = new StringTokenizer(line);
				final String ip = l.nextToken();
				final String p = l.nextToken();
				TextField serverBack = new TextField("", skin);
				serverBack.setSize(515, 50);
				serverBack.setPosition(0, 0);
				serverBack.setDisabled(true);
				serverBack.setName(Integer.toString(nu));
				
				final Label add = new Label("adresse : ", skin);
				add.setSize(100, 20);
				add.setPosition(0, 13);
				
				final TextField addEdit = new TextField(ip, skin);
				addEdit.setSize(130, 20);
				addEdit.setPosition(70, 13);
				addEdit.setMaxLength(15);
				addEdit.setName("ip");
				
				final Label po = new Label("port : ", skin);
				po.setSize(100, 20);
				po.setPosition(210, 13);
				
				final TextField poEdit = new TextField(p, skin);
				poEdit.setSize(70, 20);
				poEdit.setTextFieldFilter(new TextField.TextFieldFilter() {
					
					@Override
					public boolean acceptChar(TextField textField, char c) {
						char[] accepted = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
						for (char a : accepted)
					        if (a == c) return true;
					    return false;
					}
				});
				poEdit.setPosition(250, 13);
				poEdit.setMaxLength(6);
				poEdit.setName("port");
				
				final TextButton connexion = new TextButton("Connexion", skin);
				connexion.setSize(90, 40);
				connexion.setPosition(330, 5);
				
				final TextButton suppr = new TextButton("Supprimer", skin);
				suppr.setSize(90, 40);
				suppr.setPosition(420, 5);
								
				connexion.addListener(new ClickListener() {
					@Override
					public void clicked (InputEvent e, float x, float y) {
						Connect(addEdit.getText(), poEdit.getText());
					}
				});
				
				final int nuF = nu;
				suppr.addListener(new ClickListener() {
					@Override
					public void clicked (InputEvent e, float x, float y) {
						supprServer(Integer.toString(nuF));
					}
				});
				
				nu++;
				
				WidgetGroup server = new WidgetGroup();
				server.addActor(serverBack);
				server.addActor(add);
				server.addActor(addEdit);
				server.addActor(po);
				server.addActor(poEdit);
				server.addActor(connexion);
				server.addActor(suppr);
				server.setPosition(0, 544+(-50*num));
				sServerList.addActor(server);
				serverList.add(server);
				num++;
				
				if(serverList.size()>8){
					slider.setVisible(true);
					slider.setRange(0, serverList.size()-8);
					slider.setValue(serverList.size()-8);
					for(int i = 8; i < serverList.size(); i++){
						serverList.get(i).setVisible(false);
					}
				}
				if(serverList.size()>8){
					slider.setVisible(true);
					slider.setRange(0, serverList.size()-8);
					slider.setValue(serverList.size()-8);
					for(int i = 8; i < serverList.size(); i++){
						serverList.get(i).setVisible(false);
					}
				}
				if(serverList.size()<9){
					slider.setVisible(false);
				}
			}
			sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		addServer = new TextButton("Ajouter un serveur", skin);
		addServer.setSize(150, 50);
		addServer.setPosition(272-addServer.getWidth()/2, 50);
		addServer.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				int num = serverList.size()+1;
				final String ip = "";
				final String p = "";
				TextField serverBack = new TextField("", skin);
				serverBack.setSize(515, 50);
				serverBack.setPosition(0, 0);
				serverBack.setDisabled(true);
				serverBack.setName(Integer.toString(nu));
				
				final Label add = new Label("adresse : ", skin);
				add.setSize(100, 20);
				add.setPosition(0, 13);
				
				final TextField addEdit = new TextField(ip, skin);
				addEdit.setSize(130, 20);
				addEdit.setPosition(70, 13);
				addEdit.setMaxLength(15);
				addEdit.setName("ip");
				
				final Label po = new Label("port : ", skin);
				po.setSize(100, 20);
				po.setPosition(210, 13);
				
				final TextField poEdit = new TextField(p, skin);
				poEdit.setSize(70, 20);
				poEdit.setTextFieldFilter(new TextField.TextFieldFilter() {
					
					@Override
					public boolean acceptChar(TextField textField, char c) {
						char[] accepted = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
						for (char a : accepted)
					        if (a == c) return true;
					    return false;
					}
				});
				poEdit.setPosition(250, 13);
				poEdit.setMaxLength(6);
				poEdit.setName("port");
				
				final TextButton connexion = new TextButton("Connexion", skin);
				connexion.setSize(90, 40);
				connexion.setPosition(330, 5);
				
				final TextButton suppr = new TextButton("Supprimer", skin);
				suppr.setSize(90, 40);
				suppr.setPosition(420, 5);
								
				connexion.addListener(new ClickListener() {
					@Override
					public void clicked (InputEvent e, float x, float y) {
						Connect(addEdit.getText(), poEdit.getText());
					}
				});
				
				final int nuF = nu;
				suppr.addListener(new ClickListener() {
					@Override
					public void clicked (InputEvent e, float x, float y) {
						supprServer(Integer.toString(nuF));
					}
				});
				
				WidgetGroup server = new WidgetGroup();
				server.addActor(serverBack);
				server.addActor(add);
				server.addActor(addEdit);
				server.addActor(po);
				server.addActor(poEdit);
				server.addActor(connexion);
				server.addActor(suppr);
				server.setPosition(0, 544+(-50*num));
				if(serverList.size()>7){
					server.setVisible(false);
				}
				sServerList.addActor(server);
				serverList.add(server);
				nu++;
				
				if(serverList.size()>8){
					slider.setVisible(true);
					slider.setRange(0, slider.getMaxValue()+1);
					slider.setValue(slider.getValue()+1);
				}
				save();
			}
		});
		sServerList.addActor(addServer);
		
		sServerList.addActor(slider);
		sServerList.addActor(error);
		
		username = new TextField("Username", skin);
		username.setSize(200, 40);
		username.setPosition(272-username.getWidth()/2, 400-username.getHeight()/2);
		
		password = new TextField("Password", skin);
		password.setPasswordMode(true);
		password.setPasswordCharacter('*');
		password.setSize(200, 40);
		password.setPosition(272-password.getWidth()/2, 350-password.getHeight()/2);
		
		btnLogin = new TextButton("Login", skin);
		btnLogin.setSize(200, 40);
		btnLogin.setPosition(272-btnLogin.getWidth()/2, 200-btnLogin.getHeight()/2);
				
		btnLogin.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent e, float x, float y) {
				Login();
			}
		});
		
		sConnexion.addActor(username);
		sConnexion.addActor(password);
		sConnexion.addActor(btnLogin);
	}
	
	public void Connect (String ip, String p) {
		save();
		error.setText("Connexion en cours...");
		error.setPosition(272-error.getWidth()/2, 115);
		
		boolean canConnect = true;
		if(ip.equals("")){
			error.setText("Saisisez une adresse");
			error.setPosition(272-error.getWidth()/2, 115);
			canConnect = false;
		}else if(p.equals("")){
			error.setText("Saisisez un port");
			error.setPosition(272-error.getWidth()/2, 115);
			canConnect = false;
		}
		
		if(canConnect){
			try {
				//if(firstLogin){
					socket = new Socket(ip, Integer.parseInt(p));
					out = new PrintWriter(socket.getOutputStream());
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//firstLogin = false;
					sl = false;
					sConnexion.addActor(error);
					Gdx.input.setInputProcessor(sConnexion);
				//}
			} catch (UnknownHostException e) {
				error.setText("Adresse invalide");
				error.setPosition(272-error.getWidth()/2, 115);
				//e.printStackTrace();
			} catch (IOException e) {
				error.setText("Connexion impossible");
				error.setPosition(272-error.getWidth()/2, 115);
				//e.printStackTrace();
			} finally {
			}
		}
	}
	
	public void Login () {
		int mess = 0;
		out.println(username.getText());
		out.println(password.getText());
		out.flush();
		try {
			if(in.readLine().equals("connecte")){
				mess = 1;
			}else{
				mess = 2;
			}
			if(mess == 1){
				g.Login();
				//System.out.println("Connecté");
				t2 = new Thread(new ClientServeur(socket, g));
				t2.start();
				//Stats stats = new Stats(username.getText(), password.getText());
				//firstLogin = true;
			}else if(mess == 2){
				//socket.close();
				//out.close();
				//in.close();
				System.out.println("ok");
				error.setText("Mot de passe incorrect");
				error.setPosition(272-error.getWidth()/2, 115);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT );
		if(sl){
			Gdx.input.setInputProcessor(sServerList);
		}else{
			Gdx.input.setInputProcessor(sConnexion);
		}
		
		if(sl){
			if(slider.isDragging()){
				for(int i=0; i < serverList.size(); i++){
					serverList.get(i).setPosition(0, (544-(50*i)-50)+((slider.getMaxValue()-slider.getValue())*50));
					if(i-(slider.getMaxValue()-slider.getValue()) < 8 && i-(slider.getMaxValue()-slider.getValue()) > -1){
						serverList.get(i).setVisible(true);
					}else{
						serverList.get(i).setVisible(false);
					}
				}
			}
		
			sServerList.draw();
		}else{
			sConnexion.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
	
	public void addServer() {
		
	}
	
	public void supprServer(String name) {
		int serverID = 0;
		for(int z = 0; z < serverList.size(); z++){
			if(serverList.get(z).findActor(name) != null){
				serverID = z;
			}
		}
		serverList.remove(serverID);
		sServerList.clear();
		for(int z = 0; z < serverList.size(); z++){
			WidgetGroup server = serverList.get(z);
			if(z >= serverID){
				server.setPosition(server.getX(), server.getY()+50);
			}
			if(z < 8){
				server.setVisible(true);
			}
			sServerList.addActor(server);
		}
		sServerList.addActor(addServer);
		sServerList.addActor(slider);
		
		if(serverList.size()<9){
			if(serverList.size()==8){
				System.out.println("ok");
				slider.setRange(0, slider.getMaxValue()-1);
				for(int i=0; i < serverList.size(); i++){
					serverList.get(i).setPosition(0, (544-(50*i)-50)+((slider.getMaxValue()-slider.getValue())*50));
					if(i-(slider.getMaxValue()-slider.getValue()) < 8 && i-(slider.getMaxValue()-slider.getValue()) > -1){
						serverList.get(i).setVisible(true);
					}else{
						serverList.get(i).setVisible(false);
					}
				}
			}
			slider.setVisible(false);
		}else{
			slider.setRange(0, slider.getMaxValue()-1);
			for(int i=0; i < serverList.size(); i++){
				serverList.get(i).setPosition(0, (544-(50*i)-50)+((slider.getMaxValue()-slider.getValue())*50));
				if(i-(slider.getMaxValue()-slider.getValue()) < 8 && i-(slider.getMaxValue()-slider.getValue()) > -1){
					serverList.get(i).setVisible(true);
				}else{
					serverList.get(i).setVisible(false);
				}
			}
		}
		save();
	}
	
	public void save() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("ServerList.txt", "UTF-8");
			for(WidgetGroup server : serverList){
				TextField ip = server.findActor("ip");
				TextField port = server.findActor("port");
				if(!ip.getText().equals("") && !port.getText().equals("")){
					writer.println(ip.getText() + " " + port.getText());
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
