package display.drawing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import record.Day;

public class DayPanel extends JPanel
{
	private final Day day;

	public DayPanel(Day day)
	{ this.day = day; }

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		day.draw(g2d);
	}
}
