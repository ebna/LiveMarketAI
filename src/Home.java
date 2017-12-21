// Live Market Analysis

// Creator: Mohammed I.A. Sharieff

// This program allows you to see how various parts of the financial markets are 
// moving, how risky are they to invest in, and how much of your total investment
// you should allocate into each sector to keep an optimal Sharpe ratio.

// Markets Analyzed
// - Stock (S&P 500), HighYield Bonds, Gold, Oil, Comodities, and Bitcoin

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.net.*;
import java.io.*;

public class Home extends JFrame {

	private static JPanel contentPane;
	private static JTextField timeClock;
	public static JLabel timeBox;
	public static JTable correlationTable;
	public static JTable opTable;
	public static JTable riskTable;
	public static JLabel quantLabel;
	
	public static int N = 19;
	
	// Contains the thread which powers your web integrated back end
	
	static Thread y = new Thread(){
		public void run(){
			try{
				MainX.Backend(timeClock,timeBox,correlationTable,opTable,riskTable,quantLabel);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
	};
	
	
	// Contains the thread which runs your GUI
	
	static Thread x = new Thread(){
		public void run() {
			try {
				Home frame = new Home();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			y.run();

		}
	};
	
	

	
	
	// Main method runs everything
	
	public static void main(String[] args) throws Exception{
		x.run();
	}

	
	// Creates your home frame
	
	public Home() throws Exception{
		
		String[] pics = {"A.JPG","B.JPG","C.JPG","D.JPG","F.JPG", "G.JPG","H.JPG"};
		
		
		// Main Frame
		
		setTitle("Mohammed Sharieff's Financial Market Analyzer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1899, 1292);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Risk-Metrics frame
				
		JInternalFrame RiskMeasures = new JInternalFrame("Risk Measures");
		RiskMeasures.setBorder(null);
		RiskMeasures.getContentPane().setBackground(new Color(0, 204, 204));
		RiskMeasures.getContentPane().setForeground(Color.YELLOW);
		RiskMeasures.setForeground(Color.BLACK);
		RiskMeasures.setFrameIcon(null);
		RiskMeasures.setClosable(true);
		RiskMeasures.setBounds(70, 594, 554, 395);
		contentPane.add(RiskMeasures);
		RiskMeasures.isClosable();
		RiskMeasures.getContentPane().setLayout(null);
		
		// Creates your Risk table

		DefaultTableModel zx = new DefaultTableModel();
		
		riskTable = new JTable(zx);
		riskTable.setForeground(Color.WHITE);
		riskTable.setOpaque(false);
		riskTable.setBorder(null);
		riskTable.setBackground(Color.BLACK);
		riskTable.setBounds(10, 11, 534, 347);
		RiskMeasures.getContentPane().add(riskTable);
		
		for(int i = 0; i < N; i++){
			zx.addRow(new Object[]{" "});
		}
		for(int i = 0; i < 4; i++){
			zx.addColumn("");
		}
		
		// Settings window
		
		JInternalFrame settingsWindow = new JInternalFrame("Settings");
		settingsWindow.setBounds(881, 844, 474, 155);
		contentPane.add(settingsWindow);
		settingsWindow.getContentPane().setBackground(new Color(255, 0, 255));
		settingsWindow.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("REFRESH TIMER (SECONDS)");
		lblNewLabel_1.setForeground(new Color(255, 215, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(90, 33, 222, 50);
		settingsWindow.getContentPane().add(lblNewLabel_1);
		
		// Creates a text box to input your timer in
		
		timeClock = new JTextField();
		timeClock.setText("60");
		timeClock.setHorizontalAlignment(SwingConstants.CENTER);
		timeClock.setBackground(new Color(124, 252, 0));
		timeClock.setBounds(335, 47, 65, 26);
		settingsWindow.getContentPane().add(timeClock);
		timeClock.setColumns(10);
		settingsWindow.setBorder(null);
		settingsWindow.setFrameIcon(null);
		settingsWindow.setClosable(true);
		
		
		// Correlation Matrix frame
		
		JInternalFrame Correlations = new JInternalFrame("Correlation Matrix");
		Correlations.getContentPane().setBackground(new Color(255, 255, 0));
		Correlations.setBorder(null);
		Correlations.setFrameIcon(null);
		Correlations.setClosable(true);
		Correlations.setBounds(717, 355, 1062, 455);
		contentPane.add(Correlations);
		Correlations.getContentPane().setLayout(null);
		Correlations.isClosable();
		Correlations.getContentPane().setLayout(null);
		
		// Creates your correlation matrix table
		
		DefaultTableModel xz = new DefaultTableModel();
		
		correlationTable = new JTable(xz);
		correlationTable.setCellSelectionEnabled(true);
		correlationTable.setRowHeight(20);
		correlationTable.setForeground(new Color(255, 255, 255));
		correlationTable.setOpaque(false);
		correlationTable.setBorder(null);
		correlationTable.setBackground(new Color(0, 0, 0));
		correlationTable.setBounds(10, 11, 1042, 407);
		Correlations.getContentPane().add(correlationTable);
		
		for(int i = 0; i < N; i++){
			xz.addRow(new Object[]{" "});
		}
		for(int i = 0; i < N; i++){
			xz.addColumn("");
		}

		
		
		// Portfolio Optimzier frame
		
		JInternalFrame optimizeA = new JInternalFrame("Market Portfolio Optimizer");
		optimizeA.getContentPane().setForeground(Color.GREEN);
		optimizeA.getContentPane().setBackground(Color.GREEN);
		optimizeA.setBorder(null);
		optimizeA.setFrameIcon(null);
		optimizeA.setClosable(true);
		optimizeA.setBounds(70, 222, 484, 361);
		contentPane.add(optimizeA);
		optimizeA.getContentPane().setLayout(null);
		
		// Creates the table for your portfolio optimizer
		
		DefaultTableModel xy = new DefaultTableModel();
		
		opTable = new JTable(xy);
		opTable.setOpaque(false);
		opTable.setForeground(new Color(255, 255, 255));
		opTable.setBorder(null);
		opTable.setBackground(new Color(0, 0, 0));
		opTable.setBounds(43, 11, 399, 312);
		optimizeA.getContentPane().add(opTable);
		
		for(int i = 0; i < N-1; i++){
			xy.addRow(new Object[]{});
		}
		for(int i = 0; i < 2; i++){
			xy.addColumn("");
		}
		
		
		// Displays your timer count down
		
		timeBox = new JLabel("LOADING");
		timeBox.setHorizontalTextPosition(SwingConstants.CENTER);
		timeBox.setVerticalAlignment(SwingConstants.TOP);
		timeBox.setHorizontalAlignment(SwingConstants.RIGHT);
		timeBox.setForeground(new Color(255, 255, 255));
		timeBox.setFont(new Font("OCR A Extended", Font.BOLD, 25));
		timeBox.setIcon(new ImageIcon(FP(pics[5])));
		timeBox.setBounds(1555, 886, 205, 103);
		contentPane.add(timeBox);
		
		// Title Bar
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FP(pics[0])));
		lblNewLabel.setBounds(453, 42, 990, 88);
		contentPane.add(lblNewLabel);
		
		// Button Creation
		
		JButton corrButton = new JButton("");
		corrButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Correlations.setVisible(true);
			}
		});
		corrButton.setBorder(null);
		corrButton.setIcon(new ImageIcon(FP(pics[1])));
		corrButton.setBounds(295, 151, 323, 61);
		contentPane.add(corrButton);

		
		JButton riskMes = new JButton("");
		riskMes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RiskMeasures.setVisible(true);
			}
		});
		riskMes.setBorder(null);
		riskMes.setIcon(new ImageIcon(FP(pics[2])));
		riskMes.setBounds(616, 151, 323, 61);
		contentPane.add(riskMes);
		
		JButton optimizer = new JButton("");
		optimizer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optimizeA.setVisible(true);
			}
		});
		optimizer.setBorder(null);
		optimizer.setIcon(new ImageIcon(FP(pics[3])));
		optimizer.setBounds(937, 151, 323, 59);
		contentPane.add(optimizer);

		JButton settings = new JButton("");
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settingsWindow.setVisible(true);
			}
		});
		settings.setBorder(null);
		settings.setIcon(new ImageIcon(FP(pics[4])));
		settings.setBounds(1258, 151, 323, 59);
		contentPane.add(settings);
		
		quantLabel = new JLabel("Finance");
		quantLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		quantLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantLabel.setFont(new Font("OCR A Extended", Font.BOLD | Font.ITALIC, 17));
		quantLabel.setForeground(Color.BLACK);
		quantLabel.setIcon(new ImageIcon(FP(pics[6])));
		quantLabel.setBounds(388, 292, 1127, 583);
		contentPane.add(quantLabel);
		
		
		
	}
	
	// Imports your icons from the file path
	
	public static String FP(String x){
		
		String m = System.getProperty("os.name");
		m = m.toLowerCase();
		m = m.replace("[^a-z]", "");
						
		if(m.equals("windows")){
			x = "icons\\" + x;
		} else {
			x = "icons/" + x;
		}
		
		File u = new File(x);
		
		String y = u.getAbsolutePath();
		
		return y;
	}
}
