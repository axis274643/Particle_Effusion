package fun;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

public class Circle extends JLabel{
	public static int SPEED = 1;
	public static ArrayList<Circle> allCircles = new ArrayList<Circle>();
	public static ArrayList<Circle> allPlayers = new ArrayList<Circle>();
	public static int particleSize = 5;
	
	//random speed from -100 to 100;
	public Double[] velocity = new Double[2];
	//number from 0 to 1000, center of circle
	public Double[] position = new Double[2];
	
	public double angle = 0;
	
	public int sizeX = particleSize;
	public int sizeY = particleSize;
	public int infected = 0;
	
	public int angleOfDetection;
	
	public int ID;
	
	public boolean wall = false;
	
	
	public Circle(double pX, double pY, double vX, double vY, int inf) {
		this();
		position[0] = pX;
		position[1] = pY;
		velocity[0] = vX;
		velocity[1] = vY;
		infected = inf;
		if(infected == 1) {
			this.setBackground(new Color(155,0,0));
		}
		
		this.setSize(sizeX,sizeY);
		this.setLocation((int)(position[0] - sizeX/2), (int)(position[1] - sizeY/2));
		
		this.setVisible(true);
		
		Thread thread = new Thread() {
			public void run() {
				move();
			}
		};
		
		thread.start();
	}
	
	public Circle() {
		position[0] = 0.0;
		position[1] = 0.0;
		velocity[0] = 0.0;
		velocity[1] = 0.0;
		
		allCircles.add(this);
		ID = allCircles.size();
		
		this.setText(String.valueOf(ID));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setOpaque(true);
		this.setBackground(new Color(0,0,0));
		this.setForeground(new Color(255,255,255));
	}
	
	public boolean touching(Circle c) {
		boolean overlapx = ((position[0] - sizeX/2) <= (c.position[0] + c.sizeX/2)) && ((position[0] + sizeX/2) >= (c.position[0] - c.sizeX/2));
		boolean overlapy = ((position[1] - sizeY/2) <= (c.position[1] + c.sizeY/2)) && ((position[1] + sizeY/2) >= (c.position[1] - c.sizeY/2));
		if(overlapx && overlapy) {
			return true;
		}
		return false;
	}
	
	public void bounceRectangle(Circle theBounced, Circle theBouncer) {
		double relativeAngle = radianToDegrees(getAngle(theBouncer.position[0] - theBounced.position[0],theBounced.position[1] - theBouncer.position[1]));
		double angle = radianToDegrees(getAngleOfDetection(theBouncer));
		//top
		if(relativeAngle >= angle && relativeAngle < 180 - angle) {
			theBounced.velocity[1] = Math.abs(theBounced.velocity[1]);
		}
		//left
		else if(relativeAngle >= 180 - angle && relativeAngle < 180 + angle) {
			theBounced.velocity[0] = Math.abs(theBounced.velocity[0]);
		}
		//bottom
		else if(relativeAngle >= 180 + angle && relativeAngle < 360 - angle) {
			theBounced.velocity[1] = -Math.abs(theBounced.velocity[1]);
		}
		//right
		else if(relativeAngle >= 360 - angle || relativeAngle < angle) {
			theBounced.velocity[0] = -Math.abs(theBounced.velocity[0]);
		}
	}
	
	public double getAngleOfDetection(Circle rectangle) {
		return Math.atan((double)rectangle.sizeY/rectangle.sizeX);
	}
	
	public void move() {
		while(true) {
			ArrayList<Circle> temp = new ArrayList<Circle>();
			for(int i = 0; i < allCircles.size(); i++) {
				temp.add(allCircles.get(i));
			}
			for(Circle c:temp) {
				if(!this.equals(c) && touching(c)) {
					if(c.infected == 1) {
						this.infected = 1;
						this.setBackground(new Color(255, 0, 0));
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
	
	public static void remove(Circle c) {
		c.setVisible(false);
		allCircles.remove(c);
	}
	
	public void AddKeyControl() {
        InputMap im = getInputMap();
        ActionMap am = getActionMap();
        if(allPlayers.size() > 0) {
        	im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "w");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "a");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "s");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "d");
            am.put("w", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    allPlayers.get(0).velocity[1] = (double) -5;
                    allPlayers.get(0).velocity[0] = (double) 0;
                }
            });
            am.put("a", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    allPlayers.get(0).velocity[0] = (double) -5;
                    allPlayers.get(0).velocity[1] = (double) 0;
                }
            });
            am.put("s", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    allPlayers.get(0).velocity[1] = (double) 5;
                    allPlayers.get(0).velocity[0] = (double) 0;
                }
            });
            am.put("d", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    allPlayers.get(0).velocity[0] = (double) 5;
                    allPlayers.get(0).velocity[1] = (double) 0;
                }
            });
        }
        
        if(allPlayers.size()>1) {
        	im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");
            am.put("up", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	allPlayers.get(1).velocity[1] = (double) -5;
                	allPlayers.get(1).velocity[0] = (double) 0;
                }
            });
            am.put("left", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	allPlayers.get(1).velocity[0] = (double) -5;
                	allPlayers.get(1).velocity[1] = (double) 0;
                }
            });
            am.put("down", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	allPlayers.get(1).velocity[1] = (double) 5;
                	allPlayers.get(1).velocity[0] = (double) 0;
                }
            });
            am.put("right", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	allPlayers.get(1).velocity[0] = (double) 5;
                	allPlayers.get(1).velocity[1] = (double) 0;
                }
            });
        }
        setFocusable(true);
        requestFocusInWindow();        
    }
	
	public static double radianToDegrees(double rad) {
		return rad * 180/Math.PI;
	}
	
	public static double getAngle(double x, double y) {
		double hyp = Math.sqrt(x*x + y*y);
		
		if(x >= 0 && y > 0) {
			return(Math.asin(y/hyp));
			
		}else if(x < 0 && y > 0) {
			return(Math.PI - Math.asin(y/hyp));
			
		}else if(x < 0 && y <= 0) {
			return((Math.atan(y/x) + Math.PI));
			
		}else if(x > 0 && y < 0){
			return(Math.PI*2 - Math.acos(x/hyp));
			
		}else if(x == 0 && y < 0){
			
			return(Math.acos(x/hyp) + Math.PI);
			
		}else {
			return(0);
		}
		
	}
	
	public String toString() {
		return ("Infected = " + infected);
	}

}
