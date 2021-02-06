package gui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;

import java.awt.Dimension;
import javax.swing.SwingConstants;

import threads.Robot;

public class robotOverview extends JPanel {
	private Robot robot;
	public final static int HEIGHT = 30;
	
	public robotOverview(int width, Robot r) {
		robot = r;
		robot.setOverview(this);
		setPreferredSize(new Dimension(300, HEIGHT));
		setSize(width, HEIGHT);
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel nameLbl = new JLabel("Name: "+robot.getName());
		nameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		add(nameLbl);
		
		JLabel sizeLbl = new JLabel("Size: "+robot.getSize());
		sizeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		add(sizeLbl);
		
		stateLbl = new JLabel("State: "+robot.getStateString());
		stateLbl.setHorizontalAlignment(SwingConstants.CENTER);
		add(stateLbl);
		
		JLabel timeLbl = new JLabel("Time: "+robot.getTime());
		timeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		add(timeLbl);
	}
	private static final long serialVersionUID = 1L;
	private JLabel stateLbl;

	public void updateState(String state) {
		stateLbl.setText("State: "+state);
	}

}
