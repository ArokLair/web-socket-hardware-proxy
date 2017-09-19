package ec.umbral.standardpro.agent;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Properties;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.google.gson.Gson;

@ClientEndpoint
public class StandarPROAgentGUI extends JFrame {

	/**
	* 
	*/
	private JPanel contentPane;
	private Parameters param;

	private Properties properties;

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(StandarPROAgentGUI.class);
	private final Action exit = new SwingAction();

	private SystemTray tray;
	private TrayIcon trayIcon;
	private JLabel lblStatusserver;
	private JLabel lblStatusprinter;
	private Session session_ws;
	private PrintService printer;
	private Gson jsonparse = new Gson();
	private JTextArea textArea;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {

					/* Use an appropriate Look and Feel */
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (UnsupportedLookAndFeelException ex) {
						ex.printStackTrace();
					} catch (IllegalAccessException ex) {
						ex.printStackTrace();
					} catch (InstantiationException ex) {
						ex.printStackTrace();
					} catch (ClassNotFoundException ex) {
						ex.printStackTrace();
					}
					/* Turn off metal's use of bold fonts */
					UIManager.put("swing.boldMetal", Boolean.FALSE);
					StandarPROAgentGUI frame = new StandarPROAgentGUI();
					frame.setLocationRelativeTo(null);
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
	public StandarPROAgentGUI() {

		// Metodo Agregado por Luis Ulloa
		setSystemTray();

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(StandarPROAgentGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		setResizable(false);
		setTitle("StandarPRO Agent");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 469, 256);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenuItem mntmParametros = new JMenuItem("Parametros");
		mntmParametros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						StandarPROParamGUI paragui = new StandarPROParamGUI(StandarPROAgentGUI.this, getParam());
						if (paragui.doModal() == StandarPROParamGUI.ID_OK) {

						}

					}
				});
			}
		});
		mnArchivo.add(mntmParametros);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setAction(exit);

		mnArchivo.add(mntmSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 150, 150, 150, 0 };
		gbl_contentPane.rowHeights = new int[] { 16, 16, 16, 99, 26, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblEstatusDeLa = new JLabel("Estatus de la conexion al Servidor");
		lblEstatusDeLa.setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagConstraints gbc_lblEstatusDeLa = new GridBagConstraints();
		gbc_lblEstatusDeLa.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEstatusDeLa.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstatusDeLa.gridwidth = 2;
		gbc_lblEstatusDeLa.gridx = 0;
		gbc_lblEstatusDeLa.gridy = 0;
		contentPane.add(lblEstatusDeLa, gbc_lblEstatusDeLa);

		lblStatusserver = new JLabel("STATUS_SERVER");
		GridBagConstraints gbc_lblStatusserver = new GridBagConstraints();
		gbc_lblStatusserver.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblStatusserver.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatusserver.gridx = 2;
		gbc_lblStatusserver.gridy = 0;
		contentPane.add(lblStatusserver, gbc_lblStatusserver);

		JLabel lblEstatusImpre = new JLabel("Estatus de conexion Impresora");
		lblEstatusImpre.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		GridBagConstraints gbc_lblEstatusImpre = new GridBagConstraints();
		gbc_lblEstatusImpre.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblEstatusImpre.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstatusImpre.gridwidth = 2;
		gbc_lblEstatusImpre.gridx = 0;
		gbc_lblEstatusImpre.gridy = 1;
		contentPane.add(lblEstatusImpre, gbc_lblEstatusImpre);

		lblStatusprinter = new JLabel("STATUS_PRINTER");
		GridBagConstraints gbc_lblStatusprinter = new GridBagConstraints();
		gbc_lblStatusprinter.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblStatusprinter.insets = new Insets(0, 0, 5, 0);
		gbc_lblStatusprinter.gridx = 2;
		gbc_lblStatusprinter.gridy = 1;
		contentPane.add(lblStatusprinter, gbc_lblStatusprinter);

		JLabel lblConsolaDeConexion = new JLabel("Consola de conexi\u00F3n:");
		GridBagConstraints gbc_lblConsolaDeConexion = new GridBagConstraints();
		gbc_lblConsolaDeConexion.gridwidth = 2;
		gbc_lblConsolaDeConexion.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblConsolaDeConexion.insets = new Insets(0, 0, 5, 5);
		gbc_lblConsolaDeConexion.gridx = 0;
		gbc_lblConsolaDeConexion.gridy = 2;
		contentPane.add(lblConsolaDeConexion, gbc_lblConsolaDeConexion);

		JButton btnTestPrint = new JButton("Test Print!");
		btnTestPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printDocument("This is demo printing textFile Device.....");
			}
		});
		GridBagConstraints gbc_btnTestPrint = new GridBagConstraints();
		gbc_btnTestPrint.anchor = GridBagConstraints.EAST;
		gbc_btnTestPrint.insets = new Insets(0, 0, 5, 0);
		gbc_btnTestPrint.gridx = 2;
		gbc_btnTestPrint.gridy = 2;
		contentPane.add(btnTestPrint, gbc_btnTestPrint);

		JScrollPane scrollPane = new JScrollPane();

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);

		JButton btnReiniciarConexion = new JButton("Reiniciar conexion");
		btnReiniciarConexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset_conexion();
			}
		});
		GridBagConstraints gbc_btnReiniciarConexion = new GridBagConstraints();
		gbc_btnReiniciarConexion.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReiniciarConexion.anchor = GridBagConstraints.NORTH;
		gbc_btnReiniciarConexion.insets = new Insets(0, 0, 0, 5);
		gbc_btnReiniciarConexion.gridx = 0;
		gbc_btnReiniciarConexion.gridy = 4;
		contentPane.add(btnReiniciarConexion, gbc_btnReiniciarConexion);

		JButton btnMinimizar = new JButton("Minimizar");
		btnMinimizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(Frame.ICONIFIED);
			}
		});

		GridBagConstraints gbc_btnMinimizar = new GridBagConstraints();
		gbc_btnMinimizar.anchor = GridBagConstraints.NORTH;
		gbc_btnMinimizar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMinimizar.insets = new Insets(0, 0, 0, 5);
		gbc_btnMinimizar.gridx = 1;
		gbc_btnMinimizar.gridy = 4;
		contentPane.add(btnMinimizar, gbc_btnMinimizar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setAction(exit);
		GridBagConstraints gbc_btnSalir = new GridBagConstraints();
		gbc_btnSalir.anchor = GridBagConstraints.NORTH;
		gbc_btnSalir.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSalir.gridx = 2;
		gbc_btnSalir.gridy = 4;
		contentPane.add(btnSalir, gbc_btnSalir);

		// Metodos Agregado por Luis Ulloa
		loadProperties();
		conecta_ws();
		conecta_impresora();

	}

	private void printinfoTextArea(String message) {
		textArea.append(message);
	}
	
	private void setSystemTray() {
		if (SystemTray.isSupported()) {
			log.info("system tray supported");
			tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage(StandarPROAgentGUI.class.getResource("images/run.gif"));
			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					log.info("Exiting....");
					try {
						if (session_ws.isOpen()) {
							session_ws.close();
						}
					} catch (IOException e1) {
						log.error(e1);
					}
					System.exit(0);
				}
			};
			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem = new MenuItem("Salir");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);
			defaultItem = new MenuItem("Abrir");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					setExtendedState(JFrame.NORMAL);
				}
			});
			popup.add(defaultItem);
			trayIcon = new TrayIcon(image, "SystemTray Demo", popup);
			trayIcon.setImageAutoSize(true);
		} else {
			System.out.println("system tray not supported");
		}
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == ICONIFIED) {
					try {
						tray.add(trayIcon);
						setVisible(false);
						log.info("added to SystemTray");
					} catch (AWTException ex) {
						log.error("unable to add to tray", ex);
					}
				}
				if (e.getNewState() == 7) {
					try {
						tray.add(trayIcon);
						setVisible(false);
						log.info("added to SystemTray");
					} catch (AWTException ex) {
						log.error("unable to add to system tray", ex);
					}
				}
				if (e.getNewState() == MAXIMIZED_BOTH) {
					tray.remove(trayIcon);
					setVisible(true);
					log.info("Tray icon removed");
				}
				if (e.getNewState() == NORMAL) {
					tray.remove(trayIcon);
					setVisible(true);
					log.info("Tray icon removed");
				}
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (session_ws.isOpen()) {
						session_ws.close();
					}
				} catch (IOException e1) {
					log.error(e1);
				}
			}
		});

	}

	private void loadProperties() {
		properties = new Properties();
		try {
			InputStream is = new FileInputStream("conf.properties");
			properties.load(is);
			String devID = properties.getProperty("DEVICEID");
			String printer = properties.getProperty("PRINTER_NAME");
			String WS_URL = MessageFormat.format(
					properties.getProperty("URL_HW_PROXY",
							"ws://localhost:8080/ws-hw-proxy/websocket/hw-proxy?devID=NOIDDEV"),
					properties.getProperty("DEVICEID"));
			param = new Parameters(devID, WS_URL, printer);
			is.close();
		} catch (IOException e) {
			log.error("Error al cargar archivo conf.properties", e);
		}
	}

	private void conecta_ws() {
		try {
			if (session_ws == null) {
				WebSocketContainer container = ContainerProvider.getWebSocketContainer();
				session_ws = container.connectToServer(this, new URI(getParam().getSERVER_URL()));
				setInfoMessage("OK", getLblStatusserver());
			}
		} catch (Exception ex) {
			setErroMessage("ERROR: 107", getLblStatusserver());
			log.error("Error 107 al conectar al ws :", ex);
		}
	}

	private void conecta_impresora() {
		PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService printService : ps) {
			if (printService.getName().equals(getParam().getPrinterName())) {
				printer = printService;
				break;
			}
		}
		if (printer != null) {
			setInfoMessage("OK", getLblStatusprinter());
		} else {
			setErroMessage("ERROR: 109", getLblStatusserver());
			log.error("Error 109: No hay impresora definida con ese nombre revisar la configuracion");
		}

	}

	private void reset_conexion() {
		try {
			this.session_ws.close();
			this.printer = null;
			conecta_ws();
			conecta_impresora();
		} catch (IOException e) {
			log.error(e);
		}
	}

	private void setInfoMessage(String text, JLabel label) {
		label.setForeground(Color.BLUE);
		label.setText(text);
	}

	private void setErroMessage(String text, JLabel label) {
		label.setForeground(Color.RED);
		label.setText(text);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		MessageInfoDevice mid = jsonparse.fromJson(message, MessageInfoDevice.class);
		if (mid.getMessageInfo() != null) {
			printDocument(mid.getMessageInfo().getMessage());
			if(getTextArea().getLineCount()>10) {
				int end;
				try {
					end = getTextArea().getLineEndOffset(0);
					getTextArea().replaceRange("", 0, end);
				} catch (BadLocationException e) {
					log.error(e);
				} 
			}
			printinfoTextArea("Printing to "+getParam().getPrinterName()+"....\n");
		}
	}

	private void printDocument(String message) {
		String text_to_print = message;
		log.info(text_to_print);
		DocPrintJob job = printer.createPrintJob();
		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		Doc doc = new SimpleDoc(text_to_print.getBytes(), flavor, null);
		try {
			job.print(doc, null);
		} catch (PrintException e) {
			log.error(e);
		}
	}

	public Parameters getParam() {
		return param;
	}

	public void setParam(Parameters param) {
		this.param = param;
	}

	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SwingAction() {
			putValue(NAME, "Salir");
			putValue(SHORT_DESCRIPTION, "Salir del programa");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				if (session_ws.isOpen()) {
					session_ws.close();
				}
			} catch (IOException e1) {
				log.error(e1);
			}
			dispose();
		}
	}

	public JLabel getLblStatusserver() {
		return lblStatusserver;
	}

	public JLabel getLblStatusprinter() {
		return lblStatusprinter;
	}
	public JTextArea getTextArea() {
		return textArea;
	}
}