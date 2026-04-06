package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;
import exceptions.StringIsEmptyException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class ErreklamazioaJarriGUI extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	private JTextField azalpenaText;
	private DefaultComboBoxModel<Sale> comboList= new DefaultComboBoxModel<Sale>();
	private Sale selectedSale = null;
	JLabel errorLabel = new JLabel();


	/**
	 * Create the frame.
	 */
	public ErreklamazioaJarriGUI(String usermail, List<Sale> boughtList) {
		frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(40, 271, 143, 38);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);			}
		});
		contentPane.add(itxiBt);
		
		JButton errekJarriButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaJarriGUI.jarri")); //$NON-NLS-1$ //$NON-NLS-2$
		errekJarriButton.setBounds(483, 271, 143, 38);
		errekJarriButton.setEnabled(false);
		errekJarriButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//EREREKLAMAZIO JARRI
				try {
					errorLabel.setVisible(false);
					if(azalpenaText.getText().isEmpty()) {
						throw new StringIsEmptyException();
					}else {
						BLFacade facade = MainGUI.getBusinessLogic();
						facade.erreklamazioaJarri(usermail,selectedSale.getSaleNumber(),azalpenaText.getText());
						comboList.removeElement(selectedSale);
						frame.setVisible(false);//Uneko leihoa itxi
					}
				}catch(StringIsEmptyException exception) {
					errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.StringIsEmptyException"));
					errorLabel.setVisible(true);
				}
			}
		});
		contentPane.add(errekJarriButton);
		
		JComboBox<Sale> comboBox = new JComboBox<Sale>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedSale= (Sale) comboBox.getSelectedItem();
				if(selectedSale==null) {
					errekJarriButton.setEnabled(false);
				}else {
					errekJarriButton.setEnabled(true);
				}
			}
		});
		comboBox.setBounds(217, 66, 216, 51);
		contentPane.add(comboBox);
		comboBox.setModel(comboList);
		comboList.removeAllElements();
		for (Sale s : boughtList) {
			if (s.isErreklamatuta() == false) {
				comboList.addElement(s);
			}
		}
		
		JLabel produkAukeratuLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaJarriGUI.produktuaAukeratu")); //$NON-NLS-1$ //$NON-NLS-2$
		produkAukeratuLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		produkAukeratuLabel.setBounds(176, 25, 425, 31);
		contentPane.add(produkAukeratuLabel);
		
		azalpenaText = new JTextField();
		azalpenaText.setBounds(134, 156, 364, 51);
		contentPane.add(azalpenaText);
		azalpenaText.setColumns(10);
		
		JLabel azalpenaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioaJarriGUI.azalpena")); //$NON-NLS-1$ //$NON-NLS-2$
		azalpenaLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		azalpenaLabel.setBounds(217, 133, 203, 13);
		contentPane.add(azalpenaLabel);
		
		errorLabel = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		errorLabel.setBounds(237, 284, 196, 13);
		contentPane.add(errorLabel);
		errorLabel.setVisible(false);
	}
}
