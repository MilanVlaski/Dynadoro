package recording;

import java.awt.Graphics2D;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import javax.swing.ImageIcon;

import recording.clock.ClockDrawer;
import recording.clock.ProductivityClock;

public class Day
{
	private final List<Period> periods;
	private final LocalDate date;
	private ProductivityClock productivityClock;

	public Day(List<Period> periods)
	{
		this.periods = periods;
		if (!periods.isEmpty())
		{
			this.date = periods.get(0).date();
		}
		else
		{
			throw new IllegalArgumentException("A day must have at least one period!");
		}
	}

	public void draw(Graphics2D g, int size)
	{ ClockDrawer.draw(g, periods, size); }

	public int numberOfPeriods()
	{ return periods.size(); }

	public LocalDate date()
	{ return date; }

	public boolean hasClock()
	{ return productivityClock != null; }

	public void assignClock(ProductivityClock clock)
	{ this.productivityClock = clock; }

	public ProductivityClock clock()
	{ return productivityClock; }

	public Duration timeWorked()
	{ return timeSpent(State.WORKING); }

	public Duration timeRested()
	{ return timeSpent(State.RESTING); }

	public ImageIcon clockImage()
	{ return productivityClock.image(); }

	private Duration timeSpent(State state)
	{
		return periods.stream()
		        .filter(p -> p.type().equals(state))
		        .map(Period::duration)
		        .reduce(Duration::plus)
		        .orElse(Duration.ZERO);
	}
}
