package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailaText;
	private JTextField erabText;
	private JPasswordField passText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton registerButtom = new JButton("Register");
		registerButtom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				if(facade.isCorrectEmail(emailaText.getText())){
					if (!facade.exist(erabText.getText())) {
						facade.register(erabText.getText(), passText.getPassword().toString(), emailaText.getText());
					}else {
						
					}
				}
			}
		});
		registerButtom.setBounds(175, 206, 89, 23);
		contentPane.add(registerButtom);
		
		JLabel lblNewLabel = new JLabel("Emaila sartu");
		lblNewLabel.setBounds(61, 38, 71, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Pasahitza");
		lblNewLabel_1.setBounds(61, 136, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Erabiltzailea");
		lblNewLabel_2.setBounds(61, 84, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		emailaText = new JTextField();
		emailaText.setBounds(167, 35, 86, 20);
		contentPane.add(emailaText);
		emailaText.setColumns(10);
		
		erabText = new JTextField();
		erabText.setBounds(167, 81, 86, 20);
		contentPane.add(erabText);
		erabText.setColumns(10);
		
		passText = new JPasswordField();
		passText.setBounds(175, 133, 104, 20);
		contentPane.add(passText);

	}
}
