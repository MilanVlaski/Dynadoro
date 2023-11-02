package display.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import display.ConsoleDisplay;
import timer.Timer;

public class MainFrame extends JFrame {

	private final JPanel mainPanel = new JPanel(true);
	private final JLabel clock = new JLabel();
	public Timer timer;

	public MainFrame() {

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(Color.red);

		Font font = new Font("Loto", Font.PLAIN, 50);
		clock.setFont(font);
		mainPanel.add(clock);

		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width - getWidth() - 400;
		int y = 100;
		setLocation(x, y);
		
		setLayout(new FlowLayout());

		add(mainPanel);

		
		setSize(210, 210);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void setTime(int displayedTime) {
		clock.setText(ConsoleDisplay.displayedTime(displayedTime));
	}

	public void showStartButton() {
		Font font1 = new Font("Loto", Font.PLAIN, 40);
		JButton startButton = new JButton("Start");
		startButton.setFont(font1);
		startButton.addActionListener((e) -> timer.begin());
		
		mainPanel.add(startButton);
	}

	public void showPauseButton() {
		// TODO Auto-generated method stub
		
	}

	public void showBreakButton() {
		// TODO Auto-generated method stub
		
	}
}
