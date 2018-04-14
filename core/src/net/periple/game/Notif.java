package net.periple.game;

public class Notif {
	int ID;
	String type;
	String reply;
	String name;
	
	public Notif(int ID, String type, String name){
		this.ID = ID;
		this.type = type;
		this.name = name;
	}
	
	public Notif(int ID, String type, String reply, String name){
		this.ID = ID;
		this.type = type;
		this.reply = reply;
		this.name = name;
	}
	
	public int getID () {
		return ID;
	}
	
	public String getType () {
		return type;
	}
	
	public String getReply () {
		return reply;
	}
	
	public String getName () {
		return name;
	}
}
