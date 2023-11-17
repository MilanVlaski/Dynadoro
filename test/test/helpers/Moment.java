package test.helpers;

import java.time.LocalDateTime;

public class Moment
{
	private LocalDateTime time;

	public Moment(LocalDateTime time)
	{ this.time = time; }

	public LocalDateTime afterSeconds(int seconds)
	{ return this.time = time.plusSeconds(seconds); }

	public LocalDateTime current()
	{ return time; }

	public LocalDateTime afterMinutes(int minutes)
	{ return this.time = time.plusMinutes(minutes); }

}
