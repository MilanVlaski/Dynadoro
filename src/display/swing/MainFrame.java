package display.swing;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.*;

import display.ConsoleDisplay;
import record.Day;
import record.display.ProductivityFrame;
import timer.Timer;

public class MainFrame extends JFrame
{

	private static final JPanel MAIN_PANEL = new JPanel(true);
	private static final JPanel CONTROL_PANEL = new JPanel();
	private static final JPanel TOP_PANEL = new JPanel();
	private static final JPanel CLOCK_PANEL = new JPanel();
	private static final JLabel CLOCK = new JLabel();

	public static final Font CLOCK_FONT = new Font("Loto", Font.PLAIN, 50);

	public static final Color IDLE = new Color(133, 204, 230);
	public static final Color WORK = new Color(126, 223, 202);;
	public static final Color REST = new Color(207, 176, 243);

	public Timer timer;

	private int xOffset, yOffset;

	public MainFrame()
	{
		setUndecorated(true);

		mainPanelSetup();
		locationSetup();

		// 240, 200 looks smaller and cute
		setSize(280, 200);

		layoutTopPanel();

		//
		setLayout(new BorderLayout());
		add(MAIN_PANEL, BorderLayout.CENTER);
		add(TOP_PANEL, BorderLayout.NORTH);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{ closeApplication(); }
		});

		makeDraggable();

		setVisible(true);
	}

	private void makeDraggable()
	{
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				xOffset = e.getX();
				yOffset = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{ setLocation(e.getXOnScreen() - xOffset, e.getYOnScreen() - yOffset); }
		});
	}

	private void closeApplication()
	{ System.exit(0); }

	private void layoutTopPanel()
	{
		JButton reset = new CoolButton("Reset", 70, 24,
		        (e) -> timer.reset(LocalDateTime.now()), 12);
		JButton x = new CoolButton("X", 50, 16,
		        (e) -> closeApplication(), 12);
		JButton history = new CoolButton("Check history", 120, 16,
		        (e) -> SwingUtilities.invokeLater(() ->
		        {
			        List<Day> days = timer.retrieveDays();
			        ProductivityFrame productivityFrame = new ProductivityFrame();
			        productivityFrame.showDays(days);
		        }), 12);

		TOP_PANEL.setLayout(new BoxLayout(TOP_PANEL, BoxLayout.X_AXIS));

		TOP_PANEL.add(reset);
		TOP_PANEL.add(Box.createHorizontalGlue());
		TOP_PANEL.add(history);
		TOP_PANEL.add(Box.createHorizontalGlue());
		TOP_PANEL.add(x);
	}

	private void mainPanelSetup()
	{
		MAIN_PANEL.setLayout(new BoxLayout(MAIN_PANEL, BoxLayout.Y_AXIS));
		//

		MAIN_PANEL.add(Box.createVerticalGlue());

		CLOCK.setFont(CLOCK_FONT);
		CLOCK_PANEL.add(CLOCK);

		MAIN_PANEL.add(CLOCK_PANEL);
		MAIN_PANEL.add(CONTROL_PANEL);

		MAIN_PANEL.add(Box.createVerticalGlue());
	}

	public void setTime(int time)
	{ CLOCK.setText(ConsoleDisplay.displayedTime(time)); }

	private void locationSetup()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width - getWidth() - 400;
		int y = 100;
		setLocation(x, y);
	}

	private void showStartButton()
	{ showButton("Start", 170, 55, (e) -> timer.begin(LocalDateTime.now()), 25); }

	private void showStartButtonNarrower()
	{ showButton("Start", 110, 55, (e) -> timer.begin(LocalDateTime.now()), 25); }

	private void showBreakButton()
	{ showButton("Rest", 110, 55, (e) -> timer.rest(LocalDateTime.now()), 25); }

	private void showPauseButton()
	{ showButton("| |", 60, 55, (e) -> timer.pause(LocalDateTime.now()), 25); }

	private void showResumeButton()
	{ showButton(">", 60, 55, (e) -> timer.resume(LocalDateTime.now())); }

	private void clearControls()
	{
		CONTROL_PANEL.removeAll();
		CONTROL_PANEL.revalidate();
		CONTROL_PANEL.repaint();
	}

	public void showWorking()
	{
		SwingUtilities.invokeLater(() ->
		{
			clearControls();
			showPauseButton();
			showBreakButton();
			paintBackground(WORK);
		});
	}

	public void showIdle()
	{
		SwingUtilities.invokeLater(() ->
		{
			clearControls();
			showStartButton();
			paintBackground(IDLE);
		});
	}

	public void showBreak()
	{
		SwingUtilities.invokeLater(() ->
		{
			clearControls();
			showPauseButton();
			showStartButtonNarrower();
			paintBackground(REST);
		});
	}

	public void showBreakFinished()
	{
		SwingUtilities.invokeLater(() ->
		{
			clearControls();
			showStartButton(/* stronger */);
			paintBackground(new Color(217, 176, 243));
			toFront();
			requestFocus();
		});
	}

	public void showBreakPause()
	{
		SwingUtilities.invokeLater(() ->
		{
			clearControls();
			showResumeButton();
			showStartButton(/* lighter */);
			paintBackground(new Color(207, 182, 237));
		});
	}

	public void showWorkPause()
	{
		SwingUtilities.invokeLater(() ->
		{
			clearControls();
			showResumeButton();
			showBreakButton();
			paintBackground(new Color(134, 215, 197));
		});
	}

	private void paintBackground(Color color)
	{
		MAIN_PANEL.setBackground(color);
		CLOCK_PANEL.setBackground(color);
		CONTROL_PANEL.setBackground(color);
		CLOCK.setForeground(new Color(
		        color.getRed() / 10,
		        color.getGreen() / 10,
		        color.getBlue() / 10));
		TOP_PANEL.setBackground(new Color(
		        color.getRed() - 30,
		        color.getGreen() - 30,
		        color.getBlue() - 30));
	}

	private void showButton(String text, int width, int height,
	                        ActionListener actionListener)
	{
		CoolButton button = new CoolButton(text, width, height, actionListener, 30);
		CONTROL_PANEL.add(button);
	}

	private void showButton(String text, int width, int height,
	                        ActionListener actionListener, int fontSize)
	{
		CoolButton button = new CoolButton(text, width, height, actionListener, fontSize);
		CONTROL_PANEL.add(button);
	}

	private static final long serialVersionUID = 1L;
}