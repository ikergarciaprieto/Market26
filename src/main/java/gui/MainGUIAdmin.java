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

public class MainGUIAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox comboBoxErreklamazio;
	private DefaultComboBoxModel<Erreklamazioa> erreInfo = new DefaultComboBoxModel<Erreklamazioa>();
	private JButton erreButton;
	

	/**
	 * Create the frame.
	 */
	public MainGUIAdmin(String NAN) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADMIN :)");
		lblNewLabel.setBounds(175, 14, 53, 17);
		contentPane.add(lblNewLabel);
		
		comboBoxErreklamazio = new JComboBox();
		comboBoxErreklamazio.setBounds(76, 102, 221, 26);
		contentPane.add(comboBoxErreklamazio);
		comboBoxErreklamazio.setModel(erreInfo);
		
		
		
		
		
		
		erreButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminGUI.erreklamazioakBilatu"));
		erreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				List<Seller> users=facade.getAllUsers();
				for(int i=0; i<users.size();i++) {
					List<Erreklamazioa>erreList=users.get(i).getJarritakoErreklamazioak();
					
					for(int j=0; j<erreList.size(); j++) {
						erreInfo.addElement(erreList.get(j));
					}
				}
				
			}
		});
		erreButton.setBounds(139, 60, 105, 27);
		contentPane.add(erreButton);

	}
}
