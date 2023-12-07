package display.swing;

import java.awt.Color;

public class GreyerColor
{
//	private static double[] WEIGHTS = { 0.2989, 0.5870, 0.1140 };
	private static double RED = 0.2989;
	private static double GREEN = 0.5870;
	private static double BLUE = 0.1140;

	public static Color of(Color color)
	{ return new Color(
	        red(color.getRed()),
	        green(color.getGreen()),
	        blue(color.getBlue())); }

	public static int red(int red)
	{ return (int) Math.min(red * RED, 255); }

	public static int green(int green)
	{ return (int) Math.min(green * GREEN, 255); }

	public static int blue(int blue)
	{ return (int) Math.min(blue * BLUE, 255); }

}
