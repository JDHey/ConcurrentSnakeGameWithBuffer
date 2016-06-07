package edu.unisa.concurrentSnakeGame;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class sound {

	public sound(){
		AudioInputStream gameSound = null;
		try {
			gameSound = AudioSystem.getAudioInputStream(new File("Music.wav"));
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		
		Clip sound = null;
		try {
			sound = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			sound.open(gameSound);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
		sound.start();
	}
}
