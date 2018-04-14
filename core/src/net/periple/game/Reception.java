package net.periple.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Reception implements Runnable {

	private Periple game;
	private BufferedReader in;
	private int t;
	private int i;
	private int a;
	private String r;
	private boolean run = true;
	public int num = 0;
	
	public Reception(BufferedReader in){
		this.in = in;
		this.game = Periple.getGame();
	}
	
	public void run() {
		while(run){
	        try {
	        	t = in.read();
				if(t == 1){
	        		i = in.read();
	        		a = in.read();
	        		r = in.readLine();
	        		game.setPos(i, a, r);
	        	}else if(t == 0){
	        		String name = in.readLine();
	        		String x = in.readLine();
	        		String y = in.readLine();
	        		String life = in.readLine();
	        		game.addPlayer(name, x, y, life);
	        	}else if(t == 3){
	        		boolean isFight = Boolean.parseBoolean(in.readLine());
	        		re(false, isFight);
	        	}else if(t == 2){
	        		i = in.read();
	        		a = in.read();
	        		game.setPos(i, a);
	        	}else if(t == 6){
	        		i = in.read();
	        		game.deletePlayer(i);
	        	}else if(t == 7){
	        		boolean isFight = Boolean.parseBoolean(in.readLine());
	        		re(true, isFight);
	        	}else if(t == 8){
	        		i = in.read();
	        		String l = in.readLine();
	        		game.setLife(i, l);
	        	}else if(t == 9){
	        		game.setTurn(true);
	        	}else if(t == 12){
	        		i = in.read();
	        		int x = in.read();
	        		int y = in.read();
	        		game.setMonsterPos(i, x, y);
	        	}else if(t == 13){
	        		i = in.read();
	        		a = in.read();
	        		game.setMonsterDir(i, a);
	        	}else if(t == 14){
	        		i = in.read();
	        		String l = in.readLine(); 
	        		game.setMonsterLife(i, l);
	        	}else if(t == 15){
	        		game.setInFight(false);
	        	}else if(t == 16){
	        		System.out.println("receive");
	        		int ID = in.read();
	        		String type = in.readLine();
	        		String name = in.readLine(); 
	        		game.recieveNotifQuestion(ID, type, name);
	        	}else if(t == 17){
	        		int ID = in.read();
	        		String type = in.readLine();
	        		String info = in.readLine();
	        		String name = in.readLine();
	        		game.recieveNotifInfo(ID, type, info, name);
	        	}else if(t == 18){
	        		String name = in.readLine();
	        		game.addFriendWait(name);
	        	}else if(t == 19){
	        		String name = in.readLine();
	        		game.addTeam(name);
	        	}else if(t == 20){
	        		while(t == 20){
	        			String name = in.readLine();
	        			game.addTeam(name);
	        			t = in.read();
	        		}
	        		String leaderName = in.readLine();
        			game.setLeaderName(leaderName);
	        	}else if(t == 22){
	        		String name = in.readLine();
	        		game.removeTeam(name);
	        	}else if(t == 23){
	        		game.removeAllTeam();
	        	}else if(t == 24){
	        		String newLeaderName = in.readLine();
	        		game.setLeaderName(newLeaderName);
	        	}else if(t == 25){
	        		int idAction = in.read();
	        		int id = in.read();
	        		boolean isPlayer = Boolean.parseBoolean(in.readLine());
	        		game.action(idAction, id, isPlayer);
	        	}
			} catch (IOException e) {
				if(run){
					game.setDisconnect(true);
				}
				run = false;
			}
	    }
	}
	
	public void re (boolean map, boolean isFight) {
		try {
			if(map){
				int mapID = in.read();
				game.setBLMap(mapID);
			}
			
			while(game.getBLMap() != -1){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(isFight){
				game.setInFight(true);
			}

			int r = in.read();
			while(r == 4){
				int n = in.read();
				String name = in.readLine();
				String x = in.readLine();
				String y = in.readLine();
				String life = in.readLine();
				game.addPlayerList(n, name, x, y, life);
				if(n == 1 && !game.isInitialized()){
					String inv = in.readLine();
					String equ = in.readLine();
					String fri = in.readLine();
					game.createInventory(inv, equ, fri);
					game.setInitialized(true);
				}
				r = in.read();
			}
			
			if(isFight){
				int b = in.read();
	        	while(b == 10){
	        		int id = in.read();
	        		int x = in.read();
					int y = in.read();
					game.addMonster(id, x, y);
					b = in.read();
	        	}
			}else{
				game.setInFight(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
