package record;

import java.awt.Graphics2D;
import java.util.List;

public class Day
{
	private final List<Period> periods;
	private final ProductivityClock productivityClock;

	public Day(List<Period> periods)
	{
		this.periods = periods;
		this.productivityClock = new ProductivityClock();
	}

	public void draw(Graphics2D g)
	{ productivityClock.draw(g, periods); }

	public int numberOfPeriods()
	{ return periods.size(); }

}
