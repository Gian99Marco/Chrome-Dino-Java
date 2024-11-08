package view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
	
	public static final int SCREEN_WIDTH = 600;
	private GameScreen gameScreen;
	
	public GameWindow() throws FileNotFoundException, UnsupportedAudioFileException, IOException {
		super("Chrome Dino");
		setSize(SCREEN_WIDTH, 175);
		setLocation(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		gameScreen = new GameScreen();
		addKeyListener(gameScreen);
		add(gameScreen);
	}
	
	public void startGame() {
		setVisible(true);
		gameScreen.startGame();
	}
	
	public static void main(String args[]) throws FileNotFoundException, UnsupportedAudioFileException, IOException {
		new GameWindow().startGame();
	}
}
