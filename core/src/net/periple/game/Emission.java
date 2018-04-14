package net.periple.game;

import java.io.PrintWriter;

public class Emission implements Runnable {

	private static PrintWriter out;
	private static String message;
	
	public Emission(PrintWriter out) {
		Emission.out = out;
	}
	
	public void run() {	
		
	}
	
	public void Send (int a, String m){
		message = m;
		out.write(0);
		out.write(a);
		out.println(message);
		out.flush();
	}
	
	public void Send (int a){
		out.write(1);
		out.write(a);
		out.flush();
	}
	
	public void Send (String map, String x, String y){
		out.write(2);
		out.println(map);
		out.println(x);
		out.println(y);
		out.flush();
	}
	
	//synchronized public static void SendLobby (String x, String y){
		//out.write(3);
		//out.println(x);
		//out.println(y);
		//out.flush();
	//}
	
	public void SendLife (String l){
		out.write(4);
		out.println(l);
		out.flush();
	}
	
	public void SendEndTurn (){
		out.write(5);
		out.flush();
	}
	
	public void sendAction (int dir, int action){
		out.write(6);
		out.write(dir);
		out.write(action);
		out.flush();
	}
	
	public void SendInventory (String invS, String equS){
		out.write(7);
		out.println(invS);
		out.println(equS);
		out.flush();
	}

	public void SendFriend (String friS) {
		out.write(8);
		out.println(friS);
		out.flush();
	}
	
	public void SendRequestFriend (String name) {
		out.write(9);
		out.println(name);
		out.flush();
	}

	public void sendFriendReply(String reply, String name) {
		out.write(10);
		out.println(reply);
		out.println(name);
		out.flush();
		
	}
	
	public void sendDeleteFriend(String name) {
		out.write(11);
		out.println(name);
		out.flush();
	}
	
	public void sendDeleteNotif(int id) {
		out.write(12);
		out.write(id);
		out.flush();
	}
	
	public void SendRequestTeam (String name) {
		out.write(13);
		out.println(name);
		out.flush();
	}
	
	public void sendTeamReply(String reply, String name) {
		out.write(14);
		out.println(reply);
		out.println(name);
		out.flush();
	}
	
	public void sendDeleteTeam(String name) {
		out.write(15);
		out.println(name);
		out.flush();
	}
	
	public void sendDead() {
		out.write(16);
		out.flush();
	}
}
