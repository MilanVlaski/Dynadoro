package display.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

	public MainFrame() {
		JLabel timeLabel = new JLabel("00:00");
		Font font = new Font("Loto", Font.PLAIN, 50);
		timeLabel.setFont(font);
		
		
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(200, 200);
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        int x = screenSize.width - getWidth() - 120; 
        int y = 60;
        
        setLocation(x, y);
		
		add(timeLabel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void setTime(int displayedTime) {
		// TODO Auto-generated method stub
		
	}

	public void showStartButton() {
		Font font1 = new Font("Loto", Font.PLAIN, 40);
		JButton startButton = new JButton("Start");
		startButton.setFont(font1);
		add(startButton);
	}
}
