package record;

import java.awt.Graphics2D;
import java.time.LocalDate;
import java.util.List;

import display.drawing.ClockDrawer;

public class Day
{
	private final List<Period> periods;
	private final LocalDate date;
	private ProductivityClock productivityClock;

	public Day(List<Period> periods)
	{
		this.periods = periods;
		if (!periods.isEmpty())
			this.date = periods.get(0).date();
		else
			throw new IllegalArgumentException("A day must have at least one period!");
	}

	public void draw(Graphics2D g)
	{ ClockDrawer.draw(g, periods); }

	public int numberOfPeriods()
	{ return periods.size(); }

	public LocalDate date()
	{ return date; }

}
