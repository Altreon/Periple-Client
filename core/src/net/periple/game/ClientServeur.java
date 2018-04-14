package net.periple.game;

import java.io.*;
import java.net.*;

public class ClientServeur implements Runnable {
	private Periple game;
	
	private Socket socket;
	private DatagramSocket Dsocket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private Thread t3, t4, t5;
	private InetAddress ip = null;
	private int port;
	
	public ClientServeur(Socket s, Periple game){
		socket = s;
		this.game = game;
		game.setSocket(socket);
		/*
		ip = socket.getInetAddress();
		port = socket.getPort();
		try {
			Dsocket = new DatagramSocket(2001);
		} catch (SocketException e) {
			//e.printStackTrace();
			try {
				Dsocket = new DatagramSocket(2002);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
		}
		*/
	}
	
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Emission e = new Emission(out);
		t4 = new Thread(e);
		t4.start();
		game.setE(e);
		Reception r = new Reception(in);
		t3 = new Thread(r);
		t3.start();
		game.setR(r);
		/*
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		t5 = new Thread(new ReceptionTCP(in));
		t5.start();
		t4 = new Thread(new Emission(Dsocket, ip, port));
		t4.start();
		t3 = new Thread(new Reception(Dsocket));
		t3.start();
		*/
	}

}
