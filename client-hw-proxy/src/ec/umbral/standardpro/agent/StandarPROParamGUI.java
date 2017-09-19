package ec.umbral.standardpro.agent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

public class StandarPROParamGUI extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Log log = LogFactory.getLog(StandarPROParamGUI.class);
	private final JPanel contentPanel = new JPanel();

	public static final int ID_OK = 1;
    public static final int ID_CANCEL = 0;

    private int exitCode = ID_CANCEL;
    private JTextField txtDeviceID;
    private JTextField txtImpresora;
    private JTextField txtServidor;
    /**
     * @wbp.nonvisual location=27,169
     */
    private Parameters parametros;
    private final Action close_action = new SwingAction();
    private final Action save_action = new SwingAction_1();
	
	/**
	 * Create the dialog.
	 */
	public StandarPROParamGUI(JFrame owner,Parameters param) {
		super(owner);
		this.parametros=param;
		setResizable(false);
		setTitle("Configuraci\u00F3n de Parametros");
		setIconImage(Toolkit.getDefaultToolkit().getImage(StandarPROParamGUI.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		setBounds(100, 100, 602, 219);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 19};
		gbl_contentPanel.columnWidths = new int[]{99, 117, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("DEVICE_ID");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			txtDeviceID = new JTextField();
			GridBagConstraints gbc_txtDeviceID = new GridBagConstraints();
			gbc_txtDeviceID.insets = new Insets(0, 0, 5, 5);
			gbc_txtDeviceID.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtDeviceID.gridx = 1;
			gbc_txtDeviceID.gridy = 0;
			contentPanel.add(txtDeviceID, gbc_txtDeviceID);
			txtDeviceID.setColumns(10);
		}
		{
			JLabel lblImpresora = new JLabel("IMPRESORA");
			GridBagConstraints gbc_lblImpresora = new GridBagConstraints();
			gbc_lblImpresora.anchor = GridBagConstraints.WEST;
			gbc_lblImpresora.insets = new Insets(0, 0, 5, 5);
			gbc_lblImpresora.gridx = 0;
			gbc_lblImpresora.gridy = 1;
			contentPanel.add(lblImpresora, gbc_lblImpresora);
		}
		{
			txtImpresora = new JTextField();
			GridBagConstraints gbc_txtImpresora = new GridBagConstraints();
			gbc_txtImpresora.insets = new Insets(0, 0, 5, 5);
			gbc_txtImpresora.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtImpresora.gridx = 1;
			gbc_txtImpresora.gridy = 1;
			contentPanel.add(txtImpresora, gbc_txtImpresora);
			txtImpresora.setColumns(10);
		}
		{
			JLabel lblServidor = new JLabel("SERVIDOR");
			GridBagConstraints gbc_lblServidor = new GridBagConstraints();
			gbc_lblServidor.anchor = GridBagConstraints.WEST;
			gbc_lblServidor.insets = new Insets(0, 0, 5, 5);
			gbc_lblServidor.gridx = 0;
			gbc_lblServidor.gridy = 2;
			contentPanel.add(lblServidor, gbc_lblServidor);
		}
		{
			txtServidor = new JTextField();
			GridBagConstraints gbc_txtServidor = new GridBagConstraints();
			gbc_txtServidor.insets = new Insets(0, 0, 5, 5);
			gbc_txtServidor.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtServidor.gridx = 1;
			gbc_txtServidor.gridy = 2;
			contentPanel.add(txtServidor, gbc_txtServidor);
			txtServidor.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Guardar");
				okButton.setAction(save_action);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setAction(close_action);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(getParent());
		initDataBindings();
	}

	public int doModal() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setVisible(true);

        return exitCode;
    }
	
	public JTextField getTxtDeviceID() {
		return txtDeviceID;
	}
	protected void initDataBindings() {
		BeanProperty<Parameters, String> parametersBeanProperty = BeanProperty.create("deviceID");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<Parameters, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, parametros, parametersBeanProperty, txtDeviceID, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<Parameters, String> parametersBeanProperty_1 = BeanProperty.create("printerName");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<Parameters, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, parametros, parametersBeanProperty_1, txtImpresora, jTextFieldBeanProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<Parameters, String> parametersBeanProperty_2 = BeanProperty.create("SERVER_URL");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<Parameters, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, parametros, parametersBeanProperty_2, txtServidor, jTextFieldBeanProperty_2);
		autoBinding_2.bind();
	}
	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "Cancelar");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
		
	}
	private class SwingAction_1 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction_1() {
			putValue(NAME, "Guardar");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			saveProperties();
			dispose();
		}
		
		private void saveProperties() {
			Properties properties = new Properties();
			try {
				InputStream is = new FileInputStream("conf.properties");
				properties.load(is);
				String oldDevice=properties.getProperty("DEVICEID");
				properties.put("DEVICEID", parametros.getDeviceID());
				properties.put("PRINTER_NAME", parametros.getPrinterName());
				properties.put("URL_HW_PROXY", parametros.getSERVER_URL().replaceAll(oldDevice, "{0}"));
				FileWriter writer = new FileWriter("conf.properties");
				properties.store(writer, "Umbral S.A.");
				writer.close();
				is.close();
			} catch (IOException e) {
				log.error("Error al cargar archivo conf.properties", e);
			}
			
		}
	}
}
