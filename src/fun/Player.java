package fun;

import java.awt.Color;
import java.util.ArrayList;


public class Player extends Circle{

	//precondition: only two players
	public Player(double pX, double pY, int inf) {
		super();
		
		position[0] = pX;
		position[1] = pY;
		velocity[0] = 0.0;
		velocity[1] = 0.0;
		infected = inf;
		
		if(infected==0) {
			this.setBackground(new Color(0,255,255));
		}else {
			this.setBackground(new Color(255,0,255));
		}
		this.setSize(sizeX,sizeY);
		this.setLocation((int)(position[0] - sizeX/2), (int)(position[1] - sizeY/2));
		
		this.setVisible(true);
		
		allPlayers.add(this);
		move(this);
	}
	
	public void move(Player me) {
		Thread thread = new Thread() {
			public void run() {
				while(me.getParent()==null) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				me.AddKeyControl();
				while(true) {
					ArrayList<Circle> temp = new ArrayList<Circle>();
					for(int i = 0; i < allCircles.size(); i++) {
						temp.add(allCircles.get(i));
					}
					for(Circle c:temp) {
						if(!me.equals(c) && touching(c)) {
							if(c.infected == 1) {
								me.infected = 1;
								me.setBackground(new Color(255, 255, 0));
							}
							bounceRectangle(me, c);
						}
					}
					me.angle = getAngle(velocity[0],velocity[1]);
					me.position[0] = (me.position[0] + me.velocity[0]);
					me.position[1] = (me.position[1] + me.velocity[1]);
					me.setLocation((int)(me.position[0] - me.sizeX/2), (int)(me.position[1] - me.sizeY/2));
					try {
						Thread.sleep(SPEED);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.start();
	}
	

}
