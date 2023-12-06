package record.clock;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ProductivityPanel extends JPanel
{

	public ProductivityPanel()
	{
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setPreferredSize(new Dimension(0, 900));
		setBackground(new Color(109, 152, 181));
	}

}
