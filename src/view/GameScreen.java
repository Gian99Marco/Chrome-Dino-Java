package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import logic.Dino;
import logic.EnemiesManager;
import logic.Ground;
import logic.Clouds;

import utils.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener {

	private static final int START_GAME_STATE = 0;
	private static final int GAME_PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;
	
	private Dino dino;
	private EnemiesManager enemiesManager;
	private Ground ground;
	private Clouds clouds;
	
	private Thread thread;

	private boolean isKeyPressed;

	private int gameState = START_GAME_STATE;
	
	private BufferedImage gameOver;
	private BufferedImage replay;
	private BufferedImage chromeDino;
	private BufferedImage pressSpace;
	
	private boolean running;
	private final double updateRate = 1.0d/60.0d;
	private long nextStartTime;
	private int fps;

	public GameScreen() throws FileNotFoundException, UnsupportedAudioFileException, IOException {
		dino = new Dino();
		dino.setSpeedX(4);
		enemiesManager = new EnemiesManager(dino);
		ground = new Ground(GameWindow.SCREEN_WIDTH, dino);
		clouds = new Clouds(GameWindow.SCREEN_WIDTH, dino);
		gameOver = Resource.getResourceImage("data/gameOver.png");
		replay = Resource.getResourceImage("data/replay.png");		
		chromeDino = Resource.getResourceImage("data/chromeDino.png");
		pressSpace = Resource.getResourceImage("data/pressSpace.png");
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
	}

	public void gameUpdate() {
		if (gameState == GAME_PLAYING_STATE) {
			dino.update();
			enemiesManager.update();
			ground.update();
			clouds.update();
			if (enemiesManager.hasCollided()) {
				dino.playDeadSound();
				gameState = GAME_OVER_STATE;
				dino.dead(true);
			}
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.decode("#f7f7f7"));
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {
		case START_GAME_STATE:
			dino.draw(g);
			g.drawImage(chromeDino, 55, 30, null);
			g.drawImage(pressSpace, 170, 80, null);
			break;
		case GAME_PLAYING_STATE:
		case GAME_OVER_STATE:
			ground.draw(g);
			clouds.draw(g);
			dino.draw(g);
			enemiesManager.draw(g);			
			g.setColor(Color.BLACK);
			g.drawString("HI " + dino.score, 500, 20);
			if (gameState == GAME_OVER_STATE) {
				g.drawImage(gameOver, 200, 30, null);
				g.drawImage(replay, 283, 50, null);				
			}
			break;
		}
	}

	@Override
	public void run() {
		running = true;
		double accumulator = 0;
		long currentTime, lastUpdate = System.currentTimeMillis();
		nextStartTime = System.currentTimeMillis() + 1000;
		
		while(running) {
			currentTime = System.currentTimeMillis();
			double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
			accumulator += lastRenderTimeInSeconds;
			lastUpdate = currentTime;
			
			while(accumulator > updateRate) {
				gameUpdate();
				repaint();
				accumulator -= updateRate;
			}
			fps++;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyPressed) {
			isKeyPressed = true;
			switch (gameState) {
			case START_GAME_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = GAME_PLAYING_STATE;
				}
				break;
			case GAME_PLAYING_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					try {
						dino.jump();
					} catch (UnsupportedAudioFileException | IOException e1) {
						e1.printStackTrace();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					dino.down(true);
				}
				break;
			case GAME_OVER_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = GAME_PLAYING_STATE;
					resetGame();
				}
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
		if (gameState == GAME_PLAYING_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				dino.down(false);
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		//
	}

	private void resetGame() {
		enemiesManager.reset();
		dino.dead(false);
		dino.reset();		
	}
		
}
