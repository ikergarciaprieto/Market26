package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class MugimenduakIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField gDiruKant;
	private JTextField AdiruKantitatea;

	/**
	 * Launch the application.
	 */
	
	

	/**
	 * Create the frame.
	 */
	public MugimenduakIkusiGUI(float dirua, String mail) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAteraDirua = new JButton("Atera Dirua");
		btnAteraDirua.setBounds(285, 24, 143, 46);
		contentPane.add(btnAteraDirua);
		
		JButton btnGordeDirua = new JButton("Gorde dirua");
		btnGordeDirua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnGordeDirua.setBounds(285, 107, 143, 55);
		contentPane.add(btnGordeDirua);
		
		gDiruKant = new JTextField();
		gDiruKant.setBounds(295, 175, 114, 21);
		contentPane.add(gDiruKant);
		gDiruKant.setColumns(10);
		
		AdiruKantitatea = new JTextField();
		AdiruKantitatea.setBounds(295, 82, 114, 21);
		contentPane.add(AdiruKantitatea);
		AdiruKantitatea.setColumns(10);

	}
}
