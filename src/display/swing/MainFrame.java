package display.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import display.ConsoleDisplay;
import timer.Timer;

public class MainFrame extends JFrame {

	private final JPanel mainPanel = new JPanel(true);
	private final JPanel controlPanel = new JPanel();
	private final JLabel clock = new JLabel();
	private JPanel clockPanel;

	public static final Font BUTTON = new Font("Loto", Font.PLAIN, 30);
	public static final Font CLOCK = new Font("Loto", Font.PLAIN, 50);

	private static final Color WORK = new Color(126, 223, 202);;
	public Timer timer;

	public MainFrame() {

		mainPanelSetup();

		locationSetup();

		setSize(310, 290);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void mainPanelSetup() {
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		mainPanel.add(Box.createVerticalGlue());

		clock.setFont(CLOCK);
		clockPanel = new JPanel();
		clockPanel.add(clock);

		mainPanel.add(clockPanel);
		mainPanel.add(controlPanel);

		mainPanel.add(Box.createVerticalGlue());
	}

	private void locationSetup() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width - getWidth() - 400;
		int y = 100;
		setLocation(x, y);
	}

	private void paintBackground(Color color) {
		mainPanel.setBackground(color);
		clockPanel.setBackground(color);
		controlPanel.setBackground(color);
		clock.setForeground(new Color(color.getRed() / 10, color.getGreen() / 10,
				color.getBlue() / 10));
	}

	public void setTime(int time) {
		clock.setText(ConsoleDisplay.displayedTime(time));
	}

	public void showStartButton() {
		showButton("Start", 120, 70, (e) -> timer.begin());
	}

	public void showPauseButton() {
		JButton pauseButton = new JButton("| |");
		pauseButton.setFont(BUTTON);
		pauseButton.setPreferredSize(new Dimension(70, 70));
		pauseButton.setFocusPainted(false);
		pauseButton.addActionListener((e) -> timer.pause());

		controlPanel.add(pauseButton);
	}

	public void showBreakButton() {
		JButton breakButton = new JButton("Take break");
		breakButton.setFont(BUTTON);
		breakButton.setPreferredSize(new Dimension(182, 70));
		breakButton.setFocusPainted(false);
		breakButton.addActionListener((e) -> timer.takeBreak());

		controlPanel.add(breakButton);
	}

	private void showResumeButton() {
		JButton resumeButton = new JButton(">");
		resumeButton.setFont(BUTTON);
		resumeButton.setPreferredSize(new Dimension(70, 70));
		resumeButton.setFocusPainted(false);
		resumeButton.addActionListener((e) -> timer.resume());

		controlPanel.add(resumeButton);
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
			paintBackground(WORK);
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
			paintBackground(new Color(207, 176, 243));
			clock.setForeground(new Color(21, 18, 23));
		});
	}

	public void showBreakFinished() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showStartButton(/* stronger */);
			paintBackground(new Color(217, 176, 243));
			clock.setForeground(new Color(25, 18, 23));
		});
	}

	public void showBreakPause() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showResumeButton();
			showStartButton(/* lighter */);
			paintBackground(new Color(207, 182, 237));
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

	private void showButton(String text, int width, int height, ActionListener actionListener) {
		JButton startButton = new JButton(text);
		startButton.setFont(BUTTON);
		startButton.setPreferredSize(new Dimension(width, height));
		startButton.setFocusPainted(false);
		startButton.addActionListener(actionListener);

		controlPanel.add(startButton);
	}

}
