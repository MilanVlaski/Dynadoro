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

public class SoundPlayer implements LineListener
{

	private static final String AUDIO_FILE = "Cute Bell Sound Effect.wav";
	boolean isPlaybackCompleted;

	@Override
	public void update(LineEvent event)
	{
		if (LineEvent.Type.STOP == event.getType())
		{
			isPlaybackCompleted = true;
			System.out.println("Playback completed.");
		}
	}

	public void play()
	{
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
	private void play(String audioFilePath)
	{
		try
		{
			InputStream inputStream = getClass().getClassLoader()
			        .getResourceAsStream(AUDIO_FILE);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.addLineListener(this);
			audioClip.open(audioStream);
			audioClip.start();

			while (!isPlaybackCompleted)
			{
				Thread.sleep(1000);
			}

			audioClip.close();
			audioStream.close();
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException
		        | InterruptedException ex)
		{
			System.out.println(
			        "Error occurred during playback process: " + ex.getMessage());
		}
	}
}
