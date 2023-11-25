package record;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import display.swing.MainFrame;

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
}
