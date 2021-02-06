package gui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import threads.Robot;
import java.awt.FlowLayout;

public class RobotPanel extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	private JTextField sizeTxt;
	private Robot robot;
	public static int height = 80;
	private JSlider sizeSlider;
	private JFormattedTextField timeTxt;
	private JSlider timeSlider;
	
	public RobotPanel(Robot r) {
		robot = r;
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel namePanel = new JPanel();
		add(namePanel);
		namePanel.setLayout(null);
		
		JLabel nameLabel = new JLabel("Name: " + robot.getName());
		nameLabel.setBounds(28, 9, 93, 32);
		namePanel.add(nameLabel);
		
		JPanel sizePanel = new JPanel();
		add(sizePanel);
		sizePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel sizeLabel = new JLabel("Size: ");
		sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sizePanel.add(sizeLabel);
		
		DecimalFormat format = new DecimalFormat("###");
		
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(Robot.getMaxSize());
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		
		sizeTxt = new JFormattedTextField(formatter);
		sizeTxt.setText(String.valueOf(robot.getSize()));
		sizePanel.add(sizeTxt);
		sizeTxt.setColumns(10);
		sizeTxt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					int size = Integer.parseInt(sizeTxt.getText());
					robot.setSize(size);
					sizeSlider.setValue(size);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		
		sizeSlider = new JSlider(1, Robot.getMaxSize(), robot.getSize());
		sizeSlider.addChangeListener(this);
		sizePanel.add(sizeSlider);
		
		JPanel timePanel = new JPanel();
		add(timePanel);
		
		JLabel timeLabel = new JLabel("Time:");
		timePanel.add(timeLabel);
		
		NumberFormatter formatter2 = new NumberFormatter(format);
		formatter2.setValueClass(Integer.class);
		formatter2.setMinimum(1);
		formatter2.setMaximum(100);
		formatter2.setAllowsInvalid(false);
		formatter2.setCommitsOnValidEdit(true);
		
		timeTxt = new JFormattedTextField(formatter2);
		timeTxt.setText(String.valueOf(robot.getTime()));
		timeTxt.setColumns(10);
		timeTxt.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					int time = Integer.parseInt(timeTxt.getText());
					if(time<10)time=10;
					robot.setTime(time);
					timeSlider.setValue(time);
					sizeTxt.setText(String.valueOf(time));
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		timePanel.add(timeTxt);
		
		timeSlider = new JSlider(10, 100, robot.getTime());
		timeSlider.addChangeListener(this);
		timePanel.add(timeSlider);
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource().equals(sizeSlider) && !sizeSlider.getValueIsAdjusting()) {
			int size = (int)sizeSlider.getValue();
			robot.setSize(size);
			sizeTxt.setText(String.valueOf(size));
		}
		else if(e.getSource().equals(timeSlider) && !timeSlider.getValueIsAdjusting()) {
			int time = (int)timeSlider.getValue();
			robot.setTime(time);
			timeTxt.setText(String.valueOf(time));
		}
	}
}
