package fun;

import java.awt.*;  
import javax.swing.*;

public class Graph extends JFrame{
	
	public static int originX = 500;
	public static int originY = 500;
	
	public static int x;
	public static int y;
	public static Color color;
	public static boolean newPoint = false;
	
	public Graph(){
        setTitle("Drawing a graph");
        setSize(1000, 1000);
        setVisible(true);
        setLocation(2000,0);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        for(double x = 0; x < originX*2; x+=0.5) {
        	g2d.drawOval((int)x, originY, 2, 2);
        }
        for(double y = 0; y < originY*2; y+=0.5) {
        	g2d.drawOval(originX, (int)y, 2, 2);
        }
        
        while(true) {
        	while(!newPoint) {
        		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	g2d.setColor(color);
        	g2d.drawOval(x + originX, 500 - y, 5, 5);
        	newPoint = false;
        }
    }
    
    public void point(int xx, int yy, Color colorcolor) {
    	x = xx;
    	y = yy;
    	color = colorcolor;
    	newPoint = true;
    	
    }
    
    
    

    public static void main(String[] args) {
       new Graph();
    }

}
