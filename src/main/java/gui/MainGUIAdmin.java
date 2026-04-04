package gui;

import java.awt.EventQueue;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erreklamazioa;
import domain.Seller;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class MainGUIAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox comboBoxErreklamazio;
	private DefaultComboBoxModel<Erreklamazioa> erreInfo = new DefaultComboBoxModel<Erreklamazioa>();
	private JButton erreButton;
	private JFrame thisFrame;
	JLabel error = new JLabel();
	

	/**
	 * Create the frame.
	 */
	public MainGUIAdmin(String NAN) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		thisFrame=this;
		
		JLabel lblNewLabel = new JLabel(NAN);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 181, 17);
		contentPane.add(lblNewLabel);
		
		comboBoxErreklamazio = new JComboBox();
		comboBoxErreklamazio.setBounds(20, 48, 595, 97);
		contentPane.add(comboBoxErreklamazio);
		comboBoxErreklamazio.setModel(erreInfo);
		
		
		
		
		
		
		erreButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.erreklamazioakBilatu"));
		erreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				List<Seller> users=facade.getAllUsers();
				erreInfo.removeAllElements();
				for(int i=0; i<users.size();i++) {
					List<Erreklamazioa>erreList=users.get(i).getJarritakoErreklamazioak();
					
					for(int j=0; j<erreList.size(); j++) {
						if(erreList.get(j).getOnartua().equals("ez begiratuta")) {
							erreInfo.addElement(erreList.get(j));
						}
					}
				}
				
			}
		});
		erreButton.setBounds(30, 155, 161, 30);
		contentPane.add(erreButton);
		
		JButton acceptButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUIAdmin.accept")); //$NON-NLS-1$ //$NON-NLS-2$
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//ERREKLMAZIOA ONARTU
				Erreklamazioa errek = (Erreklamazioa) comboBoxErreklamazio.getSelectedItem();
				error.setEnabled(false);
				if(errek!=null) {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.acceptReclamation(true,errek.getId());
				}else {
					error.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIAdmin.ErrorNoSelectedErrek"));
					error.setEnabled(true);
				}
			}
		});
		acceptButton.setBounds(231, 162, 111, 23);
		contentPane.add(acceptButton);
		
		JButton denyButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUIAdmin.deny")); //$NON-NLS-1$ //$NON-NLS-2$
		denyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//ERREKLMAZIOA EZ ONARTU
				Erreklamazioa errek = (Erreklamazioa) comboBoxErreklamazio.getSelectedItem();
				error.setEnabled(false);
				if(errek!=null) {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.acceptReclamation(false,errek.getId());
				}else {
					error.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUIAdmin.ErrorNoSelectedErrek"));
					error.setEnabled(true);
				}
			}
		});
		denyButton.setBounds(352, 162, 111, 23);
		contentPane.add(denyButton);
		
		error = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		error.setForeground(new Color(255, 0, 0));
		error.setBounds(269, 205, 153, 13);
		contentPane.add(error);
		error.setEnabled(false);
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(541, 275, 105, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});
		contentPane.add(itxiBt);
	}
}
