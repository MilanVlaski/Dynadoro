package record;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import display.swing.MainFrame;
import record.StateData.TrackedState;

public class Day
{
	private final List<StateData> states;
	private Color clockBackground = new Color(216, 239, 250);

	public Day(List<StateData> states)
	{ this.states = states; }

	public void draw(Graphics2D g)
	{
		int large = 280;
		int thumbnail = 140;

		int centerX = large / 2;
		int centerY = large / 2;
		int radius = (int) (Math.min(centerX, centerY) * 0.9);

		drawCircle(g, centerX, centerY, radius);
		drawClockBorder(g, centerX, centerY, radius, 5);
		drawHours(g, centerX, centerY, radius);
		drawMinutes(g, centerX, centerY, radius);

		for (StateData state : states)
			drawState(g, state.startTime(), state.duration(), state.type(), centerX,
			        centerY, radius);
	}

	private void drawState(Graphics2D g, LocalDateTime startTime, Duration duration,
	                       TrackedState type, int centerX, int centerY, int radius)
	{
		Color borderColor = type.equals(TrackedState.WORK)
		        ? MainFrame.WORK
		        : MainFrame.REST;
		float alpha = 0.5f;

		// Set a transparent stroke using AlphaComposite
		AlphaComposite alphaComposite = AlphaComposite
		        .getInstance(AlphaComposite.SRC_OVER, alpha);
		g.setComposite(alphaComposite);

		g.setColor(borderColor);
		BasicStroke borderStroke = new BasicStroke(4, BasicStroke.CAP_ROUND,
		        BasicStroke.JOIN_BEVEL);
		g.setStroke(borderStroke);

		g.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 60, 30);

		g.setStroke(new BasicStroke(1));
		g.setColor(clockBackground);
		g.setComposite(AlphaComposite.SrcOver);
		int smallerRadius = (int) (radius * 0.6);

		g.fillArc(centerX - smallerRadius, centerY - smallerRadius, 2 * smallerRadius,
		        2 * smallerRadius, 60, 30);
	}

	private void drawMinutes(Graphics2D g, int centerX, int centerY, int radius)
	{
		for (int i = 0; i < 60; i++)
		{
			if (i % 5 == 0)
				continue;
			else
			{
				double angle = Math.toRadians(6 * i - 90);
				int lineX1 = (int) (centerX + 0.93 * radius * Math.cos(angle));
				int lineY1 = (int) (centerY + 0.93 * radius * Math.sin(angle));
				int lineX2 = (int) (centerX + 0.96 * radius * Math.cos(angle));
				int lineY2 = (int) (centerY + 0.96 * radius * Math.sin(angle));

				g.setColor(new Color(200, 200, 200));
				BasicStroke mainStroke = new BasicStroke(1, BasicStroke.CAP_ROUND,
				        BasicStroke.JOIN_BEVEL);
				g.setStroke(mainStroke);
				g.draw(new Line2D.Double(lineX1, lineY1, lineX2, lineY2));
			}
		}
	}

	private void drawHours(Graphics2D g, int centerX, int centerY, int radius)
	{
		for (int i = 0; i < 12; i++)
		{
			double angle = Math.toRadians(30 * i - 60);
			int lineX1 = (int) (centerX + 0.9 * radius * Math.cos(angle));
			int lineY1 = (int) (centerY + 0.9 * radius * Math.sin(angle));
			int lineX2 = (int) (centerX + 0.96 * radius * Math.cos(angle));
			int lineY2 = (int) (centerY + 0.96 * radius * Math.sin(angle));

			// Draw thicker line for hours
			BasicStroke mainStroke = new BasicStroke(3, BasicStroke.CAP_ROUND,
			        BasicStroke.JOIN_BEVEL);
			g.setStroke(mainStroke); // Adjust the thickness as needed
			g.setColor(new Color(150, 160, 170));
			g.draw(new Line2D.Double(lineX1, lineY1, lineX2, lineY2));

			// Draw slightly lighter lines on each side
			g.setStroke(new BasicStroke(2)); // Adjust the thickness as needed
			g.setColor(new Color(180, 190, 200)); // Adjust the color as needed

			int midX = (lineX1 + lineX2) / 2;
			int midY = (lineY1 + lineY2) / 2;

			g.draw(new Line2D.Double(lineX1, lineY1, midX, midY));
			g.draw(new Line2D.Double(midX, midY, lineX2, lineY2));
		}
	}

	private void drawCircle(Graphics2D g, int centerX, int centerY, int radius)
	{
		g.setColor(clockBackground);
		g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
	}

	private void drawClockBorder(Graphics2D g, int centerX, int centerY, int radius,
	                             int thickness)
	{
		g.setColor(new Color(180, 190, 200)); // Light border color
		BasicStroke borderStroke = new BasicStroke(thickness, BasicStroke.CAP_ROUND,
		        BasicStroke.JOIN_BEVEL);
		g.setStroke(borderStroke);

		int thickRadius = radius + thickness / 2;
		g.drawOval(centerX - thickRadius, centerY - thickRadius, 2 * thickRadius,
		        2 * thickRadius);
	}
	/**
	 * Converts time to degrees. Precisely, the number of degrees away from 3
	 * o'clock, to the clock hand (zero in the common X-Y coordinate system).
	 * 3 o'clock is 0. 4 is -30. 12 is 90.
	 * @param time of day
	 * @return number of degrees in the common X-Y coordinate system
	 */
	public static int timeToDegrees(LocalTime time)
	{ return 0; }

}
