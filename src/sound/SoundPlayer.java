package sound;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer implements LineListener {

	private static final String AUDIO_FILE = "Cute Bell Sound Effect.wav";
	boolean isPlaybackCompleted;

	@Override
	public void update(LineEvent event) {

		if (LineEvent.Type.START == event.getType()) {
//			System.out.println("Playback started.");
		} else if (LineEvent.Type.STOP == event.getType()) {
			isPlaybackCompleted = true;
//			System.out.println("Playback completed.");
		}
	}

	public static void play() {
		SoundPlayer player = new SoundPlayer();
		player.play(AUDIO_FILE);
	}

	/**
	 * 
	 * Play a given audio file.
	 * 
	 * @param audioFilePath Path of the audio file.
	 * 
	 */
	private void play(String audioFilePath) {
		try {
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(audioFilePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.addLineListener(this);
			audioClip.open(audioStream);
			audioClip.start();
			while (!isPlaybackCompleted) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			audioClip.close();
			audioStream.close();

		} catch (UnsupportedAudioFileException | LineUnavailableException
				| IOException ex) {
			System.out.println("Error occured during playback process:"
					+ ex.getMessage());
		}
	}
}
