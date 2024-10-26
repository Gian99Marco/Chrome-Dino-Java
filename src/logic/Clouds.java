package logic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Resource;

import view.GameWindow;

public class Clouds {
	
	private List<ImageCloud> listCloud;
	private BufferedImage cloud;
	
	private Dino dino;
	
	public Clouds(int width, Dino dino) {
		this.dino = dino;
		cloud = Resource.getResourceImage("data/cloud.png");
		listCloud = new ArrayList<ImageCloud>();
		
		ImageCloud imageCloud = new ImageCloud();
		imageCloud.posX = 0;
		imageCloud.posY = 30;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 150;
		imageCloud.posY = 40;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 300;
		imageCloud.posY = 50;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 450;
		imageCloud.posY = 20;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 600;
		imageCloud.posY = 60;
		listCloud.add(imageCloud);
	}
	
	public void draw(Graphics g) {
		for(ImageCloud imgCloud : listCloud) {
			g.drawImage(cloud, (int) imgCloud.posX, imgCloud.posY, null);
		}
	}
	
	public void update(){
		Iterator<ImageCloud> itr = listCloud.iterator();
		ImageCloud firstElement = itr.next();
		firstElement.posX -= dino.getSpeedX()/8;
		while(itr.hasNext()) {
			ImageCloud element = itr.next();
			element.posX -= dino.getSpeedX()/8;
		}
		if(firstElement.posX < -cloud.getWidth()) {
			listCloud.remove(firstElement);
			firstElement.posX = GameWindow.SCREEN_WIDTH;
			listCloud.add(firstElement);
		}
	}
	
	private class ImageCloud {
		float posX;
		int posY;
	}
}
