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
	private final JPanel controlPanel = new JPanel();
	private final JLabel clock = new JLabel();
	
	public static final Font BUTTON_FONT = new Font("Loto", Font.PLAIN, 40);
	public static final Font CLOCK_FONT = new Font("Loto", Font.PLAIN, 50);
	
	public Timer timer;

	public MainFrame() {

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.setBackground(Color.red);

		clock.setFont(CLOCK_FONT);
		mainPanel.add(clock);

		controlPanel.setBackground(Color.red);
		
		
		mainPanel.add(controlPanel);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width - getWidth() - 400;
		int y = 100;
		setLocation(x, y);
		
		setLayout(new FlowLayout());

		add(mainPanel);

		
		setSize(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void setTime(int displayedTime) {
		clock.setText(ConsoleDisplay.displayedTime(displayedTime));
	}

	public void showStartButton() {
		JButton startButton = new JButton("Start");
		startButton.setFont(BUTTON_FONT);
		startButton.addActionListener((e) -> timer.begin());
		
		controlPanel.add(startButton);
	}

	public void showPauseButton() {
		JButton pauseButton = new JButton("| |");
		pauseButton.setFont(BUTTON_FONT);
		pauseButton.addActionListener((e) -> timer.pause());
		
		controlPanel.add(pauseButton);
	}

	public void showBreakButton() {
		JButton breakButton = new JButton("Take break");
		breakButton.setFont(BUTTON_FONT);
		breakButton.addActionListener((e) -> timer.takeBreak());
		
		controlPanel.add(breakButton);
	}
	
	@Override
	public void removeAll() {
		controlPanel.removeAll();
		controlPanel.invalidate();
		controlPanel.revalidate();
		controlPanel.repaint();
	}
}
