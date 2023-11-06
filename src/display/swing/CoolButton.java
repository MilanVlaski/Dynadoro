package display.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CoolButton extends JButton {

	public static final Font BUTTON_FONT = new Font("Loto", Font.PLAIN, 30);

	public CoolButton(	String text, int width, int height,
						ActionListener actionListener) {
		super(text);

		setFont(BUTTON_FONT);
		setPreferredSize(new Dimension(width, height));
		setFocusPainted(false);
		addActionListener(actionListener);
	}
}
