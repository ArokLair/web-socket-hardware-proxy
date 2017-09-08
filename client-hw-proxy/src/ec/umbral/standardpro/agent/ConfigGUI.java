package ec.umbral.standardpro.agent;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class ConfigGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7028814539873502947L;
	private JPanel contentPane;
	private JTextField text_ws_server;
	private JTextField text_posid;
	private JTextField text_ws_port;
	private JTextField text_printerName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigGUI frame = new ConfigGUI();
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
	public ConfigGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfigGUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		setTitle("Agent config");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JLabel lblUrl = new JLabel("Server");
		
		text_ws_server = new JTextField();
		text_ws_server.setToolTipText("Ejemplo: ws://server.com.ec:8080/hw-proxy");
		text_ws_server.setColumns(10);
		
		JLabel lblIdDeCliente = new JLabel("POSID");
		
		text_posid = new JTextField();
		text_posid.setColumns(10);
		
		JLabel lblLogDeEventos = new JLabel("Logs");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblPort = new JLabel("Port");
		
		text_ws_port = new JTextField();
		text_ws_port.setToolTipText("8080");
		text_ws_port.setColumns(10);
		
		JButton btnTestCom = new JButton("Test Com");
		
		JLabel lblNewLabel = new JLabel("Status...");
		
		JLabel lblPrinterName = new JLabel("Printer Name");
		
		text_printerName = new JTextField();
		text_printerName.setColumns(10);
		
		JLabel lblPrinterDriver = new JLabel("Printer Driver");
		
		JComboBox cmb_print_driver = new JComboBox();
		cmb_print_driver.setModel(new DefaultComboBoxModel(new String[] {"EPSON ESC/POS"}));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addComponent(btnTestCom)
					.addGap(388))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addComponent(lblLogDeEventos)
					.addPreferredGap(ComponentPlacement.RELATED, 489, Short.MAX_VALUE)
					.addComponent(lblNewLabel))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(12)
							.addComponent(lblPrinterName))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUrl)
								.addComponent(lblIdDeCliente))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(text_ws_server, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
							.addGap(70)
							.addComponent(lblPort)
							.addGap(18)
							.addComponent(text_ws_port, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
						.addComponent(text_printerName, 192, 192, 192)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(text_posid, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPrinterDriver)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmb_print_driver, 0, 219, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUrl)
						.addComponent(text_ws_server, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPort)
						.addComponent(text_ws_port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIdDeCliente)
						.addComponent(text_posid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmb_print_driver, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPrinterDriver))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPrinterName)
						.addComponent(text_printerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogDeEventos)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTestCom)
					.addContainerGap())
		);
		
		JTextArea text_area_log = new JTextArea();
		text_area_log.setEditable(false);
		scrollPane.setViewportView(text_area_log);
		panel.setLayout(gl_panel);
	}
}
