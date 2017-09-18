package ec.umbral.standardpro.agent;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;


public class POSApp extends JFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;


	private static final Log log = LogFactory.getLog(POSApp.class);
	
	public POSApp() {
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					POSApp frame = new POSApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}