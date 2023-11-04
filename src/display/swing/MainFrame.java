package display.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import display.ConsoleDisplay;
import timer.Timer;

public class MainFrame extends JFrame {

	private final JPanel mainPanel = new JPanel(true);
	private final JPanel controlPanel = new JPanel();
	private final JLabel clock = new JLabel();
	private final JPanel clockPanel;

	public static final Font BUTTON_FONT = new Font("Loto", Font.PLAIN, 40);
	public static final Font CLOCK_FONT = new Font("Loto", Font.PLAIN, 50);

	public Timer timer;

	public MainFrame() {

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		mainPanel.add(Box.createVerticalGlue());

		clock.setFont(CLOCK_FONT);
		clockPanel = new JPanel();
		clockPanel.add(clock);
		mainPanel.add(clockPanel);

		mainPanel.add(controlPanel);
		mainPanel.add(Box.createVerticalGlue());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width - getWidth() - 400;
		int y = 100;
		setLocation(x, y);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);

		setSize(330, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void paintBackground(Color color) {
		mainPanel.setBackground(color);
		clockPanel.setBackground(color);
		controlPanel.setBackground(color);
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

	public void clearControls() {
		controlPanel.removeAll();
		controlPanel.invalidate();
		controlPanel.revalidate();
		controlPanel.repaint();
	}

	public void showWorking() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showPauseButton();
			showBreakButton();
			paintBackground(new Color(126, 223, 202));
			clock.setForeground(new Color(13, 22, 20));
		});
	}

	public void showIdle() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showStartButton();
			paintBackground(new Color(170, 195, 220));
		});
	}

	public void showBreak() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showPauseButton();
			showStartButton(/* lighter */);
			paintBackground(new Color(231, 176, 243));
			clock.setForeground(new Color(23, 18, 24));
		});
	}

	public void showBreakFinished() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showStartButton(/* stronger */);
			paintBackground(new Color(255, 176, 243));
			clock.setForeground(new Color(25, 18, 24));
		});
	}

	public void showBreakPause() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showResumeButton();
			showStartButton(/* lighter */);
			paintBackground(new Color(224, 188, 231));
		});
	}

	public void showWorkPause() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showResumeButton();
			showBreakButton();
			paintBackground(new Color(134, 215, 197));
		});
	}

	private void showResumeButton() {
		JButton pauseButton = new JButton(">");
		pauseButton.setFont(BUTTON_FONT);
		pauseButton.addActionListener((e) -> timer.resume());

		controlPanel.add(pauseButton);
	}

}
