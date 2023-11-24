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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import display.swing.MainFrame;

public class Day
{
	private final List<Period> states;
	private Color clockBackground = new Color(235, 247, 252);

	public Day(List<Period> states)
	{ this.states = states; }

	// TODO separate methods for thumbnail vs full sized image
	public void draw(Graphics2D g)
	{
		int large = 280;
		int thumbnail = 140;

		int centerX = large / 2;
		int centerY = large / 2;
		int radius = (int) (Math.min(centerX, centerY) * 0.9);

		//
		drawCircle(g, centerX, centerY, radius);
		drawClockBorder(g, centerX, centerY, radius, 5);
		drawHours(g, centerX, centerY, radius);
		drawMinutes(g, centerX, centerY, radius);

		for (Period state : states)
			drawState(g, state.startTime(), state.duration(), state.type(), centerX,
			        centerY, radius);

		drawCircleThatHidesPie(g, centerX, centerY, radius);
		//

		g.dispose();
	}

	private void drawState(Graphics2D g, LocalDateTime startTime, Duration duration,
	                       State type, int centerX, int centerY, int radius)
	{
		Color borderColor = type.equals(State.WORKING)
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

		float startDegrees = timeToDegrees(startTime.toLocalTime());
		float durationDegrees = durationToDegrees(duration);

		Arc2D arc = new Arc2D.Float(centerX - radius, centerY - radius, 2 * radius,
		        2 * radius, startDegrees, durationDegrees, Arc2D.PIE);

		g.fill(arc);

		drawDate(g, startTime, centerX, centerY, radius);
	}

	private void drawDate(Graphics2D g, LocalDateTime startTime, int centerX, int centerY,
	                      int radius)
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE, d, LLLL, yyyy");
		String date = format.format(startTime);

		// Set font size relative to the radius
		int fontSize = (int) (radius * 0.15); // Adjust the multiplier as needed
		Font font = new Font("Loto", Font.PLAIN, fontSize);
		g.setFont(font);

		// Calculate text width and height
		FontMetrics fontMetrics = g.getFontMetrics();
		int textWidth = fontMetrics.stringWidth(date);
		int textHeight = fontMetrics.getHeight();

		// Center the text at the bottom
		int textX = centerX - textWidth / 2;
		int textY = (int) ((centerY + radius + textHeight) * 1.02);

		g.setColor(new Color(40, 50, 50));
		g.drawString(date, textX, textY);
	}

	private void drawCircleThatHidesPie(Graphics2D g, int centerX, int centerY,
	                                    int radius)
	{
		g.setStroke(new BasicStroke(1));
		g.setColor(clockBackground);
		g.setComposite(AlphaComposite.SrcOver);
		int smallerRadius = (int) (radius * 0.4);

		g.fillOval(centerX - smallerRadius, centerY - smallerRadius, 2 * smallerRadius,
		        2 * smallerRadius);
	}

	private void drawMinutes(Graphics2D g, int centerX, int centerY, int radius)
	{
		for (int i = 0; (i < 60 && i % 5 == 0); i++)
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
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(180, 190, 200));

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
		g.setColor(new Color(180, 190, 200)); // grey
		BasicStroke borderStroke = new BasicStroke(thickness, BasicStroke.CAP_ROUND,
		        BasicStroke.JOIN_BEVEL);
		g.setStroke(borderStroke);

		int thickRadius = radius + thickness / 2;
		g.drawOval(centerX - thickRadius, centerY - thickRadius, 2 * thickRadius,
		        2 * thickRadius);
	}

	public static float timeToDegrees(LocalTime time)
	{
		int hours = time.getHour();
		int minutes = time.getMinute();

		if (hours >= 12)
			hours -= 12;

		return (float) -(hours * 30 + (minutes * 0.5) - 90);
	}

	private static float durationToDegrees(Duration duration)
	{ return -(float) (duration.toMinutes() * 0.5); }
}
