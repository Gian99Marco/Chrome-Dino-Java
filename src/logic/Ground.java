package logic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import utils.Resource;

public class Ground {
	
	public static final int GROUND_POSY = 103;
	
	private List<ImageGround> listGround;
	private BufferedImage ground1;
	private BufferedImage ground2;
	private BufferedImage ground3;
	
	private Dino dino;
	
	public Ground(int width, Dino dino) {
		this.dino = dino;
		ground1 = Resource.getResourceImage("data/ground1.png");
		ground2 = Resource.getResourceImage("data/ground2.png");
		ground3 = Resource.getResourceImage("data/ground3.png");
		
		int numberOfImageGround = width / ground1.getWidth() + 2;
		listGround = new ArrayList<ImageGround>();
		for(int i = 0; i < numberOfImageGround; i++) {
			ImageGround imageGround = new ImageGround();
			imageGround.posX = i * ground1.getWidth();
			setImageGround(imageGround);
			listGround.add(imageGround);
		}
	}
	
	public void draw(Graphics g) {
		for(ImageGround imgGround : listGround) {
			g.drawImage(imgGround.image, (int) imgGround.posX, GROUND_POSY, null);
		}
	}
	
	public void update(){
		Iterator<ImageGround> itr = listGround.iterator();
		ImageGround firstElement = itr.next();
		firstElement.posX -= dino.getSpeedX();
		float previousPosX = firstElement.posX;
		while(itr.hasNext()) {
			ImageGround element = itr.next();
			element.posX = previousPosX + ground1.getWidth();
			previousPosX = element.posX;
		}
		if(firstElement.posX < -ground1.getWidth()) {
			listGround.remove(firstElement);
			firstElement.posX = previousPosX + ground1.getWidth();
			setImageGround(firstElement);
			listGround.add(firstElement);
		}
	}
	
	private void setImageGround(ImageGround imgGround) {
		int typeGround = getTypeOfGround();
		if(typeGround == 1) {
			imgGround.image = ground1;
		} else if(typeGround == 3) {
			imgGround.image = ground3;
		} else {
			imgGround.image = ground2;
		}
	}
	
	private int getTypeOfGround() {
		Random rand = new Random();
		int type = rand.nextInt(10);
		if(type == 1) {
			return 1;
		} else if(type == 9) {
			return 3;
		} else {
			return 2;
		}
	}
	
	private class ImageGround {
		float posX;
		BufferedImage image;
	}
	
}
