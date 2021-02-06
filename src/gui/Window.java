package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import threads.ChargingBase;
import threads.ChargingStation;
import threads.Robot;
import threads.Simulation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Window extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField stationsTxt;
	private JTextField robotsTxt;
	private JPanel dataPanel;
	private JLabel stationsLabel;
	private JLabel robotsLabel;
	private JButton generateBtn;
	
	private int numberOfStations;
	private ChargingBase base;
	private List<Robot> robots;
	private List<JLabel> stationLabels;
	private Simulation sim;
	
	private JPanel _0;
	private JPanel _1;
	private JPanel _2;
	private JScrollPane scrollRobots;
	private JPanel generationCard;
	private JPanel simulationCard;
	private JPanel robotsPanel;
	private JPanel runPanel;
	private JButton runBtn;
	
	private CardLayout cards;
	private JPanel simulationPanel;
	private JPanel robotsOverviewPanel;
	private JScrollPane scrollPane;
	private JPanel chargingList;
	private JScrollPane scrollPane_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		super("Factory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		cards = new CardLayout();
		contentPane.setLayout(cards);
		
		scrollRobots = new JScrollPane();
		
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		stationsLabel = new JLabel("#of charging stations");
		stationsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataPanel.add(stationsLabel);
		
		DecimalFormat format = new DecimalFormat("###");
		
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		
		stationsTxt = new JFormattedTextField(formatter);
		stationsTxt.setText("1");
		dataPanel.add(stationsTxt);
		stationsTxt.setColumns(10);
		
		robotsLabel = new JLabel("#of robots");
		robotsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dataPanel.add(robotsLabel);
		
		robotsTxt = new JFormattedTextField(formatter);
		robotsTxt.setText("1");
		dataPanel.add(robotsTxt);
		robotsTxt.setColumns(10);
		
		_0 = new JPanel();
		dataPanel.add(_0);
		
		_1 = new JPanel();
		dataPanel.add(_1);
		
		_2 = new JPanel();
		dataPanel.add(_2);
		
		generateBtn = new JButton("Generate");
		dataPanel.add(generateBtn);
		generateBtn.addActionListener(this);
		
		generationCard = new JPanel();
		contentPane.add(generationCard, "Generate");
		generationCard.setLayout(new BorderLayout(0, 0));
		
		generationCard.add(dataPanel, BorderLayout.NORTH);
		
		robotsPanel = new JPanel();
		generationCard.add(robotsPanel, BorderLayout.CENTER);
		robotsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		runPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) runPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		generationCard.add(runPanel, BorderLayout.SOUTH);
		
		runBtn = new JButton("Run simulation");
		runBtn.setEnabled(false);
		runBtn.addActionListener(this);
		runPanel.add(runBtn);
		
		simulationCard = new JPanel();
		contentPane.add(simulationCard, "Simulation");
		GridBagLayout gbl_simulationCard = new GridBagLayout();
		gbl_simulationCard.columnWeights = new double[]{0.2, 0.8};
		gbl_simulationCard.rowWeights = new double[]{1.0};
		simulationCard.setLayout(gbl_simulationCard);
		
		simulationPanel = new JPanel();
		GridBagConstraints gbc_simulationPanel = new GridBagConstraints();
		gbc_simulationPanel.insets = new Insets(0, 0, 0, 0);
		gbc_simulationPanel.fill = GridBagConstraints.BOTH;
		gbc_simulationPanel.gridx = 0;
		gbc_simulationPanel.gridy = 0;
		simulationCard.add(simulationPanel, gbc_simulationPanel);
		simulationPanel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		simulationPanel.add(scrollPane);
		
		chargingList = new JPanel();
		scrollPane.setViewportView(chargingList);
		chargingList.setLayout(new BoxLayout(chargingList, BoxLayout.Y_AXIS));
		
		robotsOverviewPanel = new JPanel();
		GridBagConstraints gbc_robotsOverviewPanel = new GridBagConstraints();
		gbc_robotsOverviewPanel.fill = GridBagConstraints.BOTH;
		gbc_robotsOverviewPanel.gridx = 1;
		gbc_robotsOverviewPanel.gridy = 0;
		simulationCard.add(robotsOverviewPanel, gbc_robotsOverviewPanel);
		robotsOverviewPanel.setLayout(new BorderLayout(0, 0));
	}
	
	private void generateRobots(int n, int maxSize) {
		this.robots = new ArrayList<Robot>();
		for(int i = 0; i < n; i++) {
			StringBuilder name = new StringBuilder();
			int j = i+1;
			while(j>0) {
				j-=1;
				name.append((char)('A'+j%26));
				j/=26;
			}
			name.reverse();
			this.robots.add(new Robot(name.toString(), maxSize));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(generateBtn)) {
			robotsPanel.removeAll();
//			System.out.println("Max size: " + stationsTxt.getText() + " Number of robots: " + robotsTxt.getText());
			numberOfStations = Integer.valueOf(stationsTxt.getText());
			generateRobots(Integer.valueOf(robotsTxt.getText()), numberOfStations);
			
			JPanel robotsListPanel = new JPanel();
			robotsListPanel.setLayout(new BoxLayout(robotsListPanel, BoxLayout.Y_AXIS));
			robotsListPanel.setPreferredSize(new Dimension(robotsListPanel.getWidth(), RobotPanel.height*robots.size()));
			for(Robot r: robots) {
				robotsListPanel.add(new RobotPanel(r));
			}
			robotsListPanel.validate();
			robotsListPanel.repaint();
			scrollRobots = new JScrollPane(robotsListPanel);
			scrollRobots.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollRobots.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			robotsPanel.add(scrollRobots);
			
			runBtn.setEnabled(true);
			
			scrollRobots.validate();
			scrollRobots.repaint();
			robotsPanel.validate();
			robotsPanel.repaint();
		}else if(e.getSource().equals(runBtn)) {
			cards.show(contentPane, "Simulation");
			generateSimulationCard();
			sim = new Simulation(robots, base);
		}
		
	}

	private void generateSimulationCard() {
		//Charging Base
		base = new ChargingBase(numberOfStations);
		stationLabels = new ArrayList<JLabel>();
		for(ChargingStation s: base.getStations()) {
			JLabel lbl = new JLabel((s.isEmpty()?"  ":s.getIndex())+":---");
			chargingList.add(lbl);
			stationLabels.add(lbl);
			if(s!=null)s.setLbl(lbl);
		}
		
		//Robots Overview
		JPanel robotsOverviewList = new JPanel();
		robotsOverviewList.setLayout(new BoxLayout(robotsOverviewList, BoxLayout.Y_AXIS));
		robotsOverviewList.setPreferredSize(new Dimension(robotsOverviewPanel.getWidth(), robots.size()*robotOverview.HEIGHT));
		for(Robot r:robots) {
			robotsOverviewList.add(new robotOverview(robotsOverviewPanel.getWidth(), r));
		}
		scrollPane_1 = new JScrollPane(robotsOverviewList);
		scrollPane_1.validate();
		scrollPane_1.repaint();
		robotsOverviewPanel.add(scrollPane_1);
		robotsOverviewPanel.validate();
		robotsOverviewPanel.repaint();
	}
	
	

}
