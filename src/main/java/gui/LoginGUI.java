package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Seller;
import exceptions.UserOrPasswordIsWrongException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField loginText;
	private JPasswordField passText;
	private JLabel errorText;
	private JCheckBox adminCheckBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel LabelLogin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.name"));
		LabelLogin.setBounds(75, 34, 147, 14);
		contentPane.add(LabelLogin);
		
		JLabel LabelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));
		LabelPassword.setBounds(75, 92, 147, 14);
		contentPane.add(LabelPassword);
		
		loginText = new JTextField();
		loginText.setBounds(232, 31, 131, 20);
		contentPane.add(loginText);
		loginText.setColumns(10);
		
		passText = new JPasswordField();
		passText.setBounds(232, 89, 131, 20);
		contentPane.add(passText);
		
		errorText = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorText.setForeground(Color.RED);
		errorText.setBounds(32, 179, 382, 50);
		errorText.setVisible(false);
		contentPane.add(errorText);

		JButton btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.LoginButton"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				errorText.setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					if(loginText.getText().isEmpty()||passText.getPassword().toString().isEmpty()) {
						throw new NullPointerException();//testu hutsak daude
					}
					
					if(!adminCheckBox.isSelected()) {//USER MODUA
						
						Seller b = facade.isUserLogin(loginText.getText(), new String(passText.getPassword()));
						if(b!=null) {
							new MainGUIErregistratu(b.getEmail()).setVisible(true);
						}else {
							//ez da aurkitu databasean beraz zerbait gaizki dago
							throw new UserOrPasswordIsWrongException();
						}
						
					}else {//ADMIN MODUA
						Admin b = facade.isAdminLogin(loginText.getText(), new String(passText.getPassword()));
						if(b!=null) {
							new MainGUIAdmin(b.getNAN()).setVisible(true);
						}else {
							//ez da aurkitu databasean beraz zerbait gaizki dago
							throw new UserOrPasswordIsWrongException();
						}
						
						
					}
				}
				catch(NullPointerException e) {
					System.out.println("StringIsEmptyException");
					errorText.setVisible(true);
					errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.StringIsEmptyException"));
				}catch(UserOrPasswordIsWrongException e) {
					System.out.println("UserOrPasswordIsWrongException");
					errorText.setVisible(true);
					errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.UserOrPasswordIsWrongException"));
				}
			}
		});
		btnLogin.setBounds(155, 146, 139, 23);
		contentPane.add(btnLogin);
		
		adminCheckBox = new JCheckBox("Admin"); //$NON-NLS-1$ //$NON-NLS-2$
		adminCheckBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(adminCheckBox.isEnabled()) {//ADMIN MIDUA
					//JARRI USER ETA PASAHITZA
				}else {//USER MODUA
					//JARRI NAN ETA PASAHIYZA
				}
			}
		});
		adminCheckBox.setBounds(312, 231, 118, 23);
		contentPane.add(adminCheckBox);

	}
}
