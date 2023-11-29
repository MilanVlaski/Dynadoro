package display.drawing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
		Graphics2D swingGraphics = (Graphics2D) g;

		int large = 280;

		// creates image
		BufferedImage image = new BufferedImage(large, large,
		        BufferedImage.TYPE_INT_ARGB);

		Graphics2D imageGraphics = image.createGraphics();

		// draws image
		day.draw(imageGraphics);
		day.draw(swingGraphics);

		// makes file
		try
		{
			// TODO the files go somewhere else.
			ImageIO.write(image, "png", new File(filename(day.date()) + ".png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String filename(LocalDate localDate)
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d_M_yyyy");
		return format.format(localDate);
	}

	private static final long serialVersionUID = 1L;
}
