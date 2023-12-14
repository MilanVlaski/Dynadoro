package display.swing.battery;

import java.awt.*;

import javax.swing.JFrame;

public class BatteryDrawer
{
	public static void draw(Graphics2D g, int size)
	{
		int centerX = size / 2;
		int centerY = centerX;

		drawBatteryShell(g, size, centerX, centerY);
	}

	private static void drawBatteryShell(Graphics2D g, int size, int centerX, int centerY)
	{
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		int height = (int) (size * 0.9);
		int width = (int) (size * 0.7);
		int openingLength = (int) (size * 0.27);
		int openingHeight = (int) (size * 0.1);

		Polygon batteryShell = new Polygon();
		batteryShell.addPoint(centerX - openingLength / 2, 0);
		batteryShell.addPoint(centerX + openingLength / 2, 0);
		batteryShell.addPoint(centerX + openingLength / 2, openingHeight);
		batteryShell.addPoint(centerX + width / 2, openingHeight);
		batteryShell.addPoint(centerX + width / 2, openingHeight + height);
		batteryShell.addPoint(centerX - width / 2, openingHeight + height);
		batteryShell.addPoint(centerX - width / 2, openingHeight);
		batteryShell.addPoint(centerX - openingLength / 2, openingHeight);

		g.draw(batteryShell);
	}

	public static void main(String[] args)
	{ new MyFrame(); }

}

class MyFrame extends JFrame
{

	MyFrame()
	{
		setTitle("Test drawing battery");
		setSize(200, 200);
		setLocationRelativeTo(null);

		setLayout(new GridLayout());
		add(new BatteryPanel());
		setVisible(true);
	}
}