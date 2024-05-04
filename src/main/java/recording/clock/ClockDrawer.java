package recording.clock;

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_BEVEL;
import static recording.display.DayPanel.dayBackground;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import display.swing.MainFrame;
import recording.Session;
import recording.State;

public class ClockDrawer
{

	private static final Color clockBackground = new Color(235, 247, 252);
	private static final Color grey = new Color(180, 190, 200);

	public static void draw(Graphics2D g, List<Session> sessions, int size)
	{

		int centerX = size / 2;
		int centerY = size / 2;
		int clockRadius = (int) (Math.min(centerX, centerY) * 0.9);

		//
		drawBackground(g, centerX, centerY, (Math.min(centerX, centerY)));
		drawCircle(g, centerX, centerY, clockRadius);
		drawClockBorder(g, centerX, centerY, clockRadius, 5);
		drawHours(g, centerX, centerY, clockRadius);
		drawMinutes(g, centerX, centerY, clockRadius);

		for (Session session : sessions)
		{
			drawState(g, session.startTime(), session.duration(), session.type(), centerX,
			        centerY, clockRadius);
		}

		drawCircleThatHidesPie(g, centerX, centerY, clockRadius);
		//

	}

	private static void drawBackground(Graphics2D g, int centerX, int centerY, int radius)
	{
		g.setColor(dayBackground);
		((Graphics) g).fillRect(centerX - radius, centerY - radius, radius * 2,
		        radius * 2);
	}

	private static void drawState(Graphics2D g, LocalTime startTime, Duration duration,
	                              State type, int centerX, int centerY, int radius)
	{
		Color borderColor = type.equals(State.WORKING) ? MainFrame.WORK : MainFrame.REST;
		float alpha = 0.5f;

		// Set a transparent stroke using AlphaComposite
		AlphaComposite alphaComposite = AlphaComposite
		        .getInstance(AlphaComposite.SRC_OVER, alpha);
		g.setComposite(alphaComposite);

		g.setColor(borderColor);
		BasicStroke borderStroke = new BasicStroke(4, CAP_ROUND, JOIN_BEVEL);
		g.setStroke(borderStroke);

		float startDegrees = timeToDegrees(startTime);
		float durationDegrees = durationToDegrees(duration);

		Arc2D arc = new Arc2D.Float(centerX - radius, centerY - radius, 2 * radius,
		                            2 * radius, startDegrees, durationDegrees, Arc2D.PIE);

		g.fill(arc);

//		drawDate(g, startTime, centerX, centerY, radius);
	}

	private static void drawDate(Graphics2D g, LocalDateTime startTime, int centerX,
	                             int centerY, int radius)
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

	private static void drawCircleThatHidesPie(Graphics2D g, int centerX, int centerY,
	                                           int radius)
	{
		g.setStroke(new BasicStroke(1));
		g.setColor(clockBackground);
		g.setComposite(AlphaComposite.SrcOver);
		int smallerRadius = (int) (radius * 0.4);

		g.fillOval(centerX - smallerRadius, centerY - smallerRadius, 2 * smallerRadius,
		        2 * smallerRadius);
	}

	private static void drawMinutes(Graphics2D g, int centerX, int centerY, int radius)
	{
		for (int i = 0; i < 60; i++)
		{
			if (i % 5 != 0)
			{
				double angle = Math.toRadians(6 * i - 90);
				int lineX1 = (int) (centerX + 0.93 * radius * Math.cos(angle));
				int lineY1 = (int) (centerY + 0.93 * radius * Math.sin(angle));
				int lineX2 = (int) (centerX + 0.96 * radius * Math.cos(angle));
				int lineY2 = (int) (centerY + 0.96 * radius * Math.sin(angle));

				g.setColor(new Color(200, 200, 200));
				BasicStroke mainStroke = new BasicStroke(1, BasicStroke.CAP_ROUND,
				                                         JOIN_BEVEL);
				g.setStroke(mainStroke);
				g.draw(new Line2D.Double(lineX1, lineY1, lineX2, lineY2));
			}
		}
	}

	private static void drawHours(Graphics2D g, int centerX, int centerY, int radius)
	{
		for (int i = 0; i < 12; i++)
		{
			double angle = Math.toRadians(30 * i - 60);
			int lineX1 = (int) (centerX + 0.9 * radius * Math.cos(angle));
			int lineY1 = (int) (centerY + 0.9 * radius * Math.sin(angle));
			int lineX2 = (int) (centerX + 0.96 * radius * Math.cos(angle));
			int lineY2 = (int) (centerY + 0.96 * radius * Math.sin(angle));

			BasicStroke mainStroke = new BasicStroke(2, BasicStroke.CAP_ROUND,
			                                         JOIN_BEVEL);
			g.setStroke(mainStroke); // Adjust the thickness as needed
			g.setColor(new Color(150, 160, 170));
			g.draw(new Line2D.Double(lineX1, lineY1, lineX2, lineY2));

			int translatedCloserX = (int) (centerX + 0.75 * radius * Math.cos(angle));
			int translatedCloserY = (int) (centerY + 0.75 * radius * Math.sin(angle));
			drawHourNumber(g, i + 1, translatedCloserX, translatedCloserY, radius);
		}
	}

	private static void drawHourNumber(Graphics2D g, int number, int x, int y, int radius)
	{
		g.setColor(new Color(50, 60, 70)); // Adjust color as needed
		int fontSize = (int) (radius * 0.15);
		Font font = new Font("Andale Mono", Font.PLAIN, fontSize);
		g.setFont(font);
		g.setColor(new Color(100, 110, 120));

		FontMetrics fontMetrics = g.getFontMetrics();
		int textWidth = fontMetrics.stringWidth(Integer.toString(number));
		int textHeight = fontMetrics.getHeight();

		// Adjust the position to move the number closer to the center
		int textX = x - textWidth / 2;
		int textY = (int) (y + textHeight / 3.5);

		g.drawString(Integer.toString(number), textX, textY);
	}

	private static void drawCircle(Graphics2D g, int centerX, int centerY, int radius)
	{
		g.setColor(clockBackground);
		g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
	}

	private static void drawClockBorder(Graphics2D g, int centerX, int centerY,
	                                    int radius, int thickness)
	{
		g.setColor(grey); // grey
		BasicStroke borderStroke = new BasicStroke(thickness, BasicStroke.CAP_ROUND,
		                                           JOIN_BEVEL);
		g.setStroke(borderStroke);

		int thickRadius = radius + thickness / 2;
		g.drawOval(centerX - thickRadius, centerY - thickRadius, 2 * thickRadius,
		        2 * thickRadius);
	}

	private static float durationToDegrees(Duration duration)
	{ return -(float) (duration.toMinutes() * 0.5); }

	public static float timeToDegrees(LocalTime time)
	{
		int hours = time.getHour();
		int minutes = time.getMinute();

		if (hours >= 12)
		{
			hours -= 12;
		}

		return (float) -(hours * 30 + (minutes * 0.5) - 90);
	}

}
