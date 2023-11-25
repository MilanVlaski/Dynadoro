package record;

import java.awt.Graphics2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day
{
	private List<Period> periods = new ArrayList<>();
	private final ProductivityClock productivityClock;

	public Day(List<Period> periods)
	{
		this.periods = periods;
		this.productivityClock = new ProductivityClock();
	}

	public Day()
	{ this.productivityClock = new ProductivityClock(); }

	public void draw(Graphics2D g)
	{ productivityClock.draw(g, periods); }

}
