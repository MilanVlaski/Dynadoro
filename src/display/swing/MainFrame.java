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

	private static final JPanel MAIN_PANEL = new JPanel(true);
	private static final JPanel CONTROL_PANEL = new JPanel();
	private static final JLabel CLOCK = new JLabel();
	private static final JPanel CLOCK_PANEL = new JPanel();

	public static final Font CLOCK_FONT = new Font("Loto", Font.PLAIN, 50);

	private static final Color WORK = new Color(126, 223, 202);;
	private static final Color IDLE = new Color(170, 195, 220);
	private static final Color BREAK = new Color(207, 176, 243);

	public Timer timer;

	public MainFrame() {

		mainPanelSetup();
		locationSetup();

		setSize(310, 290);

		setLayout(new BorderLayout());
		add(MAIN_PANEL, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void mainPanelSetup() {
		MAIN_PANEL.setLayout(new BoxLayout(MAIN_PANEL, BoxLayout.Y_AXIS));

		MAIN_PANEL.add(Box.createVerticalGlue());

		CLOCK.setFont(CLOCK_FONT);
		CLOCK_PANEL.add(CLOCK);

		MAIN_PANEL.add(CLOCK_PANEL);
		MAIN_PANEL.add(CONTROL_PANEL);

		MAIN_PANEL.add(Box.createVerticalGlue());
	}

	public void setTime(int time) {
		CLOCK.setText(ConsoleDisplay.displayedTime(time));
	}

	private void locationSetup() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width - getWidth() - 400;
		int y = 100;
		setLocation(x, y);
	}

	private void showStartButton() {
		showButton("Start", 120, 70, (e) -> timer.begin());
	}

	private void showPauseButton() {
		showButton("| |", 70, 70, (e) -> timer.pause());
	}

	private void showBreakButton() {
		showButton("Take break", 182, 70, (e) -> timer.takeBreak());
	}

	private void showResumeButton() {
		showButton(">", 70, 70, (e) -> timer.resume());
	}

	private void clearControls() {
		CONTROL_PANEL.removeAll();
		CONTROL_PANEL.invalidate();
		CONTROL_PANEL.revalidate();
		CONTROL_PANEL.repaint();
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
			paintBackground(IDLE);
		});
	}

	public void showBreak() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showPauseButton();
			showStartButton(/* lighter */);
			paintBackground(BREAK);
		});
	}

	public void showBreakFinished() {
		SwingUtilities.invokeLater(() -> {
			clearControls();
			showStartButton(/* stronger */);
			paintBackground(new Color(217, 176, 243));
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

	private void paintBackground(Color color) {
		MAIN_PANEL.setBackground(color);
		CLOCK_PANEL.setBackground(color);
		CONTROL_PANEL.setBackground(color);
		CLOCK.setForeground(new Color(color.getRed() / 10,
				color.getGreen() / 10,
				color.getBlue() / 10));
	}

	private void showButton(String text, int width, int height,
							ActionListener actionListener) {
		CONTROL_PANEL.add(new CoolButton(text, width, height, actionListener));
	}

}
