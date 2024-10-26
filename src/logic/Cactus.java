package logic;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Cactus extends Enemy {
	
	public static final int GROUND_POSY = 125;
	
	private int posX;
	private int height;
	private int width;	
	
	private BufferedImage cactusImage;
	
	private Rectangle cactusBound;
	
	private Dino dino;	
	
	public Cactus(Dino dino, int posX, int height, int width, BufferedImage cactusImage) {
		this.posX = posX;
		this.height = height;
		this.width = width;		
		this.cactusImage = cactusImage;		
		cactusBound = new Rectangle();
		this.dino = dino;
	}
	
	public void draw(Graphics g) {
		g.drawImage(cactusImage, posX, GROUND_POSY - cactusImage.getHeight(), null);
	}
	
	public void update() {
		posX -= dino.getSpeedX();
	}
		
	public Rectangle getBound() {
		cactusBound.x = (int) posX + (cactusImage.getWidth() - width) / 2;
		cactusBound.y = GROUND_POSY - cactusImage.getHeight() + (cactusImage.getHeight() - height) / 2;
		cactusBound.width = width;
		cactusBound.height = height;
		
		return cactusBound;
	}

	@Override
	public boolean isOutOfScreen() {
		if(posX < -cactusImage.getWidth()) {
			return true;
		}
		return false;
	}
	
}
