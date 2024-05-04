package recording.display;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ProductivityPanel extends JPanel
{

	public ProductivityPanel()
	{
		setBackground(new Color(109, 152, 181));
		padding(7);
		setLayout(new GridLayout(0, 3, 7, 7));
	}

	private void padding(int size)
	{ setBorder(new EmptyBorder(size, size, size, size)); }
}
