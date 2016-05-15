package com.swipeparser;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ParserUI {

	private JFrame frmSwipeparser;
	private JTextField textField;
	JProgressBar progressBar;
	JButton btnParseFile;
	final JFileChooser fc = new JFileChooser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParserUI window = new ParserUI();
					window.frmSwipeparser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ParserUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSwipeparser = new JFrame();
		frmSwipeparser.setFont(new Font("Dialog", Font.PLAIN, 14));
		frmSwipeparser.setTitle("SwipeParser");
		frmSwipeparser.setBounds(100, 100, 450, 200);
		frmSwipeparser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSwipeparser.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(151, 58, 293, 26);
		frmSwipeparser.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblWelcome = new JLabel("Hello, welcome to SwipeParser.\nPlease select a file to start...");
		lblWelcome.setBounds(29, 6, 415, 29);
		frmSwipeparser.getContentPane().add(lblWelcome);
		
		
		btnParseFile = new JButton("Parse File");
		btnParseFile.setEnabled(false);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(151, 99, 293, 29);
		progressBar.setMinimum(0);
		progressBar.setMaximum(10);
		frmSwipeparser.getContentPane().add(progressBar);
		
		JButton btnChooseFile = new JButton("Choose File");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(frmSwipeparser);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					textField.setText(fc.getSelectedFile().getAbsolutePath());
					btnParseFile.setEnabled(true);
					progressBar.setValue(0);
				}
			}
		});
		btnChooseFile.setBounds(22, 58, 117, 29);
		frmSwipeparser.getContentPane().add(btnChooseFile);
		
		btnParseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Parser parser = new Parser();
				String output = parser.parse(fc.getSelectedFile(), progressBar);
				JTextArea ta = new JTextArea(4, 40);
				ta.setEditable(false);
				ta.setWrapStyleWord(true);
				ta.setLineWrap(true);
				ta.setCaretPosition(0);
				
				if (!"".equals(output)) {
					ta.setText(String.format(ParserConstants.MESSAGE_PARSE_SUCCESSFULLY, output));
					JOptionPane.showMessageDialog(frmSwipeparser, ta, "Successfully!", JOptionPane.PLAIN_MESSAGE);
				} else {
					ta.setText(ParserConstants.MESSAGE_DIFFICULTY_READING);
					JOptionPane.showConfirmDialog(frmSwipeparser, ta, "Failure", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnParseFile.setBounds(22, 99, 117, 29);
		frmSwipeparser.getContentPane().add(btnParseFile);
	}
}
