package fun;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graph extends JPanel{
	
	public static int originX = 200;
	public static int originY = 600;
	
	public static String axisTitleX = "";
	public static String axisTitleY = "";
	
	public static double axisScaleX = 0;
	public static double axisScaleY = 0;
	
	public ArrayList<ArrayList<Point>> lines = new ArrayList<ArrayList<Point>>();
	public Color color;
	
	public Graph(){
		setLayout(null);
        setVisible(true);
    }
	
	public Graph(String titleX, String titleY){
		 axisTitleX = titleX;
	     axisTitleY = titleY;
		
	     setLayout(null);
	     setVisible(true);
    }
	
	public Graph(String titleX, String titleY, double scaleX, double scaleY){
		 axisTitleX = titleX;
	     axisTitleY = titleY;
	     axisScaleX = scaleX;
	     axisScaleY = scaleY;
		
	     setLayout(null);
	     setVisible(true);
   }
	
	public void addLine(ArrayList<Point> l) {
		lines.add(l);
	}
	
	public void updateLine(int index, ArrayList<Point> line) {
		lines.set(index,  line);
	}

	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawLine(0,originY, 1800, originY);
        g2d.drawLine(originX, 0, originX, 1800);
        g2d.drawString(axisTitleX, 1600, originY - 50);
        g2d.drawString(axisTitleY, originX + 50, 100);
        
        if(axisScaleX != 0 && axisScaleY != 0) {
        	for(int i = 0; i < 1800; i+= 100) {
        		g2d.drawLine(i, originY - 10, i, originY + 10);
        		g2d.drawString(String.valueOf(axisScaleX * (i - originX)/100), i - 5, originY + 30);
        	}
        	for(int i = 0; i < 1000; i+= 100) {
        		g2d.drawLine(originX - 10, i, originX + 10, i);
        		g2d.drawString(String.valueOf(axisScaleY * (originY - i)/100), originX - 50, i + 5);
        	}
        }
        
        for(int i = 0; i < lines.size(); i++) {
        	Point previous = lines.get(i).get(0);
        	if(i == 0) {
        		g2d.setColor(new Color(255, 0, 0));
        	}else if(i == 1) {
        		g2d.setColor(new Color(0, 0, 255));
        	}
            for(Point p : lines.get(i)) {
            	g2d.drawLine(previous.x + originX, originY - previous.y, p.x + originX, originY - p.y);
            	previous = p;
            }
        }
        
    }
	
	public static void main(String[] args) {
		ArrayList<Point> meow = new ArrayList<Point>();
		meow.add(new Point(0,500));
		meow.add(new Point(200,600));
		meow.add(new Point(800,300));
		ArrayList<Point> meow2 = new ArrayList<Point>();
		meow2.add(new Point(0,400));
		meow2.add(new Point(300,600));
		meow2.add(new Point(600,200));
		JFrame frame = new JFrame();
		frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setLocation(2000,0);
		Graph graph = new Graph();
		graph.addLine(meow);
		graph.addLine(meow2);
		frame.add(graph);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		meow.add(new Point((int)(Math.random()*500),(int)(Math.random() * 500)));
		
		meow.add(new Point((int)(Math.random()*500),(int)(Math.random() * 500)));
		
		frame.repaint();
	}
    

}
