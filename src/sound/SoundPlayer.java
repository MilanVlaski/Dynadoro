package sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

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
			URL inputStream = getClass().getClassLoader().getResource(audioFilePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);

			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.addLineListener(this);
			audioClip.open(audioStream);
			audioClip.start();

			while (!isPlaybackCompleted)
			{
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException ex)
				{
					Thread.currentThread().interrupt();
				}
			}

			audioClip.close();
			audioStream.close();
		} catch (UnsupportedAudioFileException | LineUnavailableException
		        | IOException ex)
		{
			System.out.println(
			        "Error occurred during playback process: " + ex.getMessage());
		}
	}
}
