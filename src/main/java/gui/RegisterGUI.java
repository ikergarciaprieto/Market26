package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import exceptions.UserAlreadyExistException;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailaText;
	private JTextField erabText;
	private JPasswordField passText;
	private JLabel errorText;

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
		
		JButton registerButtom = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.ButtonRegister"));
		registerButtom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorText.setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					if(emailaText.getText().isBlank()||erabText.getText().isBlank()||passText.getPassword().toString().isBlank()) {
						throw new NullPointerException();//testu hutsak daude
					}
					if(facade.isCorrectEmail(emailaText.getText())){
						if (!facade.exist(emailaText.getText())) {//find EMAIL-a erabiltzen du !!!!!!
							//dagoeneko username-akin beste pertsonak ez dira existitzen
							facade.register(erabText.getText(), passText.getPassword().toString(), emailaText.getText());
						}else {
							throw new UserAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.UserAlreadyExistException"));
						}
					}
				}catch(NullPointerException e) {
					System.out.println("StringIsEmptyException");
					errorText.setVisible(true);
					errorText.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.StringIsEmptyException"));
				}catch(UserAlreadyExistException e) {
					System.out.println("UserAlreadyExistException");
					errorText.setVisible(true);
					errorText.setText(e.getMessage());
				}
			}
		});
		registerButtom.setBounds(134, 230, 161, 23);
		contentPane.add(registerButtom);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Email"));
		lblNewLabel.setBounds(92, 38, 183, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Password"));
		lblNewLabel_1.setBounds(92, 136, 172, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Username"));
		lblNewLabel_2.setBounds(92, 84, 183, 14);
		contentPane.add(lblNewLabel_2);
		
		emailaText = new JTextField();
		emailaText.setBounds(285, 36, 86, 20);
		contentPane.add(emailaText);
		emailaText.setColumns(10);
		
		erabText = new JTextField();
		erabText.setBounds(285, 82, 86, 20);
		contentPane.add(erabText);
		erabText.setColumns(10);
		
		passText = new JPasswordField();
		passText.setBounds(285, 134, 120, 20);
		contentPane.add(passText);
		
		errorText = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorText.setBounds(23, 163, 382, 57);
		errorText.setVisible(false);
		contentPane.add(errorText);

	}
}
