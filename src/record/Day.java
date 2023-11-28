package record;

import java.awt.Graphics2D;
import java.util.List;

import display.drawing.ClockDrawer;

public class Day
{
	private final List<Period> periods;
	private ProductivityClock productivityClock;

	public Day(List<Period> periods)
	{ this.periods = periods; }

	public void draw(Graphics2D g)
	{ ClockDrawer.draw(g, periods); }

	public int numberOfPeriods()
	{ return periods.size(); }

	public boolean hasClock()
	{ return productivityClock != null; }

}
