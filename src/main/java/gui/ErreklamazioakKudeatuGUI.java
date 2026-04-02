package gui;

import java.awt.EventQueue;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErreklamazioakKudeatuGUI extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public ErreklamazioakKudeatuGUI(String zuremail) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 75, 420, 234);
		contentPane.add(scrollPane);
		
		JButton jarritakoakButton= new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakKudeatuGUI.jarritakoak"));
		jarritakoakButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//JARRITAKO ERREKLAMAZIOAK
				BLFacade facade = MainGUI.getBusinessLogic();
				//GAUZAK
			}
		});
		jarritakoakButton.setBounds(479, 91, 167, 21);
		contentPane.add(jarritakoakButton);
		
		JButton jasotakoakButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakKudeatuGUI.jasotakoak"));
		jasotakoakButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//JASOTAKO ERREKLAMAZIOAK
				
			}
		});
		jasotakoakButton.setBounds(479, 143, 167, 21);
		contentPane.add(jasotakoakButton);
	}
}
