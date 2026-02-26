package gui;

import java.awt.Color;
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
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField loginText;
	private JPasswordField passText;
	private JLabel errorText;

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
		LabelLogin.setBounds(78, 34, 46, 14);
		contentPane.add(LabelLogin);
		
		JLabel LabelPassword = new JLabel("Password");
		LabelPassword.setBounds(78, 92, 46, 14);
		contentPane.add(LabelPassword);
		
		loginText = new JTextField();
		loginText.setBounds(232, 31, 86, 20);
		contentPane.add(loginText);
		loginText.setColumns(10);
		
		passText = new JPasswordField();
		passText.setBounds(232, 89, 86, 20);
		contentPane.add(passText);
		
		errorText = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorText.setForeground(Color.RED);
		errorText.setBounds(27, 180, 382, 50);
		errorText.setVisible(false);
		contentPane.add(errorText);
		
		JButton btnLogin = new JButton("Login egin");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
				if(loginText.getText().isEmpty()||passText.getPassword().toString().isEmpty()) {
					throw new NullPointerException();//testu hutsak daude
				}
				Seller b = facade.isUserLogin(loginText.getText(), new String(passText.getPassword()));
				if(b!=null) {
					new MainGUIErregistratu(null).setVisible(true);
				}
				}
				catch(NullPointerException e) {
					System.out.println("StringIsEmptyException");
					errorText.setVisible(true);
					errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.StringIsEmptyException"));
			}
			}
		});
		btnLogin.setBounds(155, 146, 89, 23);
		contentPane.add(btnLogin);

	}
}
