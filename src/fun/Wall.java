package fun;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.SwingConstants;

public class Wall extends Circle{
	
	public Wall(int x1, int y1, int width, int height) {
		super();
		sizeX = width;
		sizeY = height;
		position[0] = (double) x1 + sizeX/2;
		position[1] = (double) y1 + sizeY/2;
		
		this.setBackground(new Color(100, 100, 100));
		this.setSize(sizeX,sizeY);
		this.setLocation(x1,y1);
		
		this.setVisible(true);
	}
	
	
	
	
	public String toString() {
		return "Size: " + sizeX + "x" + sizeY + ", Center: " + position[0] + "," + position[1]  ;
	}

}
