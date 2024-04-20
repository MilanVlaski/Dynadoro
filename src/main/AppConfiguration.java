package main;

import java.time.LocalDateTime;

import org.springframework.context.annotation.*;

import display.swing.SwingDisplay;
import recording.SessionHistory;
import sound.SoundPlayer;
import timer.Timer;
import timer.counter.ScheduledCounter;

@Configuration
@ComponentScan(basePackageClasses = { SwingDisplay.class, ScheduledCounter.class,
        SoundPlayer.class, Timer.class })
public class AppConfiguration
{
	@Bean
	public LocalDateTime now()
	{ return LocalDateTime.now(); }

	@Bean
	public SessionHistory history()
	{ return new SessionHistory("sessions"); }
}
