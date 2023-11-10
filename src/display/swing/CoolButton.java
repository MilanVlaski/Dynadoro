package display.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CoolButton extends JButton
{

	public CoolButton(String text, int width, int height, ActionListener actionListener)
	{
		this(text, width, height, actionListener, 30);
	}

	public CoolButton(String text, int width, int height, ActionListener actionListener,
	                  int fontSize)
	{
		super(text);

		Font BUTTON_FONT = new Font("Loto", Font.PLAIN, fontSize);

		setFont(BUTTON_FONT);
		setPreferredSize(new Dimension(width, height));
		setFocusPainted(false);
		addActionListener(actionListener);
	}
}
