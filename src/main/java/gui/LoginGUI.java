package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Seller;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JPasswordField passwordField;

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
		
		JLabel LabelLogin = new JLabel("Login");
		LabelLogin.setBounds(78, 29, 46, 14);
		contentPane.add(LabelLogin);
		
		JLabel LabelPassword = new JLabel("Password");
		LabelPassword.setBounds(78, 130, 46, 14);
		contentPane.add(LabelPassword);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(232, 26, 86, 20);
		contentPane.add(textFieldLogin);
		textFieldLogin.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(232, 127, 86, 20);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("Login egin");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Seller b = facade.isUserLogin(textFieldLogin.getText(), new String(passwordField.getPassword()));
				if(b!=null) {
					new MainGUIErregistratu(null).setVisible(true);
				}
			}
		});
		btnLogin.setBounds(166, 188, 89, 23);
		contentPane.add(btnLogin);

	}
}
