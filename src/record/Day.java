package record;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;

public class Day
{
	private final List<StateData> states;

	public Day(List<StateData> states)
	{ this.states = states; }

	public void draw(Graphics g)
	{
		drawClock((Graphics2D) g);

		for (StateData state : states)
			state.draw(g);
	}

	private void drawClock(Graphics2D g)
	{

		int centerX = 200 / 2;
		int centerY = 200 / 2;
		int radius = (int) (Math.min(centerX, centerY) * 0.9);

		drawCircle(g, centerX, centerY, radius);
		drawClockBorder(g, centerX, centerY, radius);
		drawClockContrastLines(g, centerX, centerY, radius);
		drawHours(g, centerX, centerY, radius);
		drawMinutes(g, centerX, centerY, radius);
	}

	private void drawMinutes(Graphics2D g, int centerX, int centerY, int radius)
	{
		for (int i = 0; i < 60; i++)
		{
			if(i % 5 == 0)
				continue;
			
			double angle = Math.toRadians(6 * i - 90);
			int lineX1 = (int) (centerX + 0.93 * radius * Math.cos(angle));
			int lineY1 = (int) (centerY + 0.93 * radius * Math.sin(angle));
			int lineX2 = (int) (centerX + 0.96 * radius * Math.cos(angle));
			int lineY2 = (int) (centerY + 0.96 * radius * Math.sin(angle));
			
			g.setColor(new Color(200, 200, 200));
			BasicStroke mainStroke = new BasicStroke(2, BasicStroke.CAP_ROUND,
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
			BasicStroke mainStroke = new BasicStroke(4, BasicStroke.CAP_ROUND,
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
		g.setColor(new Color(216, 239, 250));
		g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
	}
	
	private void drawClockBorder(Graphics2D g, int centerX, int centerY, int radius)
	{
	    g.setColor(new Color(180, 190, 200)); // Light border color
	    BasicStroke borderStroke = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
	    g.setStroke(borderStroke);
	    g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
	}
	
	private void drawClockContrastLines(Graphics2D g, int centerX, int centerY, int radius)
	{
	    g.setColor(new Color(150, 160, 170)); // Slightly darker than the border color
	    BasicStroke contrastStroke = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
	    g.setStroke(contrastStroke);

	    int contrastRadius = radius + 2; // Slightly larger radius for contrast lines

	    // Draw two slightly darker lines around the border
	    g.drawOval(centerX - contrastRadius, centerY - contrastRadius, 2 * contrastRadius, 2 * contrastRadius);
	    g.drawOval(centerX - contrastRadius, centerY - contrastRadius, 2 * contrastRadius, 2 * contrastRadius);
	}

}
