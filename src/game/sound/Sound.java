package game.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	public static final Sound eat = new Sound("/snd/eat.wav");
	public static final Sound hurt = new Sound("/snd/hurt.wav");
	public static final Sound spawn = new Sound("/snd/spawn.wav");
	private Clip clip;
	
	private Sound(String name) {
		try   {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(Sound.class.getResource(name));
			this.clip = AudioSystem.getClip();
			this.clip.open(audioStream);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		try {
			this.clip.setFramePosition(0);
			this.clip.start();			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setVolume(float val) {
		if(this.clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			FloatControl volume = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float)(Math.log(val) / Math.log(10) * 20);
			if(dB >= 1f) dB = 1f;
			volume.setValue(dB);
		}
	}
	
}