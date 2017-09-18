package ec.umbral.standardpro.agent;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;


public class POSApp extends JFrame {

	/**
	* 
	*/
	private JPanel contentPane;
	
	private static final long serialVersionUID = 1L;


	private static final Log log = LogFactory.getLog(POSApp.class);


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					POSApp frame = new POSApp();
					frame.setVisible(true);
				} catch (Exception e) {
					log.error("Error en Cargar formulario", e);
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public POSApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
}