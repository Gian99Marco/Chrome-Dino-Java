package logic;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import utils.Animation;
import utils.AudioPlayer;
import utils.Resource;

public class Dino {

	public static final int GROUND_POSY = 80;
	public static final float GRAVITY = 0.4f;
	
	private static final int RUN = 0;
	private static final int JUMPING = 1;
	private static final int DOWN_RUN = 2;
	private static final int DEATH = 3;
	
	private int state = RUN;
	
	private float posX;
	private float posY;
	private float speedX;
	private float speedY;
	
	private Rectangle dinoBound;
	
	public int score = 0;
	
	private Animation runAnim;
	private Animation downAnim;
	
	private BufferedImage jump;
	private BufferedImage dead;
	
	private AudioPlayer audioJump;
	private AudioPlayer audioDead;
	private AudioPlayer audioScore;
	

	public Dino() throws FileNotFoundException, UnsupportedAudioFileException, IOException {
		posX = 50;
		posY = GROUND_POSY;
		dinoBound = new Rectangle();
		runAnim = new Animation(90);
		runAnim.addFrame(Resource.getResourceImage("data/dino1.png"));
		runAnim.addFrame(Resource.getResourceImage("data/dino2.png"));
		jump = Resource.getResourceImage("data/dino3.png");
		dead = Resource.getResourceImage("data/dino4.png");
		downAnim = new Animation(90);
		downAnim.addFrame(Resource.getResourceImage("data/dino5.png"));
		downAnim.addFrame(Resource.getResourceImage("data/dino6.png"));		
		audioJump = new AudioPlayer("data/jump.wav");
		audioDead = new AudioPlayer("data/dead.wav");
		audioScore = new AudioPlayer("data/scoreUp.wav");
	}
	
	public float getSpeedX() {
		return speedX;
	}
	
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void draw(Graphics g) {
		switch(state) {
			case RUN:
				g.drawImage(runAnim.getFrame(), (int) posX, (int) posY, null);
				break;
			case JUMPING:
				g.drawImage(jump, (int) posX, (int) posY, null);
				break;
			case DOWN_RUN:
				g.drawImage(downAnim.getFrame(), (int) posX, (int) (posY + 20), null);
				break;
			case DEATH:
				g.drawImage(dead, (int) posX, (int) posY, null);
				break;
		}
	}
	
	public void update() {
		runAnim.updateFrame();
		downAnim.updateFrame();
		if(posY >= GROUND_POSY) {
			posY = GROUND_POSY;
			if(state != DOWN_RUN) {
				state = RUN;
			}
		} else {
			speedY += GRAVITY;
			posY += speedY;
		}
	}
	
	public Rectangle getBound() {
		if(state == DOWN_RUN) {
			dinoBound.x = (int) posX + 5;
			dinoBound.y = (int) posY + 20;
			dinoBound.width = downAnim.getFrame().getWidth() - 10;
			dinoBound.height = downAnim.getFrame().getHeight();
		} else {
			dinoBound.x = (int) posX + 5;
			dinoBound.y = (int) posY;
			dinoBound.width = runAnim.getFrame().getWidth() - 10;
			dinoBound.height = runAnim.getFrame().getHeight();
		}
		return dinoBound;
	}
	
	public void jump() throws FileNotFoundException, UnsupportedAudioFileException, IOException {
		if(posY >= GROUND_POSY) {
			audioJump.play();
			speedY = -7.5f;
			posY += speedY;
			state = JUMPING;
		}
	}
	
	public void down(boolean isDown) {
		if(state == JUMPING) {
			return;
		}
		if(isDown) {
			state = DOWN_RUN;
		} else {
			state = RUN;
		}
	}
	
	public void dead(boolean isDead) {
		if(isDead) {
			state = DEATH;
		} else {
			state = RUN;
		}
	}
	
	public void upScore() {
		score += 20;
		if(score % 100 == 0) {
			audioScore.play();
		}
	}
	
	public void playDeadSound() {
		audioDead.play();
	}
	
	public void reset() {
		posY = GROUND_POSY;
		score = 0;
	}
	
}
