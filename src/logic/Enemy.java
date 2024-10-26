package logic;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
	
	public abstract void draw(Graphics g);
	public abstract void update();	
	public abstract Rectangle getBound();
	public abstract boolean isOutOfScreen();
	
}
