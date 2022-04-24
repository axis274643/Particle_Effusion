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
		Thread thread = new Thread() {
			public void run() {
				move();
			}
		};
	}
	
	public void move() {
		while(this.getParent()==null) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.AddKeyControl();
		while(true) {
			ArrayList<Circle> temp = new ArrayList<Circle>();
			for(int i = 0; i < allCircles.size(); i++) {
				temp.add(allCircles.get(i));
			}
			for(Circle c:temp) {
				if(!this.equals(c) && touching(c)) {
					if(c.infected == 1) {
						this.infected = 1;
						this.setBackground(new Color(255, 255, 0));
					}
					bounceRectangle(this, c);
				}
			}
			this.angle = getAngle(velocity[0],velocity[1]);
			this.position[0] = (this.position[0] + this.velocity[0]);
			this.position[1] = (this.position[1] + this.velocity[1]);
			this.setLocation((int)(this.position[0] - this.sizeX/2), (int)(this.position[1] - this.sizeY/2));
			try {
				Thread.sleep(SPEED);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
