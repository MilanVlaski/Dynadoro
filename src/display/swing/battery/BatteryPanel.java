package display.swing.battery;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BatteryPanel extends JPanel
{

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		BatteryDrawer.draw(g2d, 60);
	}
}
