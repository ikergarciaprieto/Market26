package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ErreklamazioakKudeatuGUI extends JFrame {

	private JPanel contentPane;
	private JFrame thisFrame;


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
		
		thisFrame=this;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 75, 420, 234);
		contentPane.add(scrollPane);
		
		JButton jarritakoakButton= new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakKudeatuGUI.jarritakoak"));
		jarritakoakButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//JARRITAKO ERREKLAMAZIOAK
				BLFacade facade = MainGUI.getBusinessLogic();
				
			}
		});
		jarritakoakButton.setBounds(479, 148, 167, 21);
		contentPane.add(jarritakoakButton);
		
		JButton jasotakoakButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakKudeatuGUI.jasotakoak"));
		jasotakoakButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//JASOTAKO ERREKLAMAZIOAK
				
			}
		});
		jasotakoakButton.setBounds(479, 179, 167, 21);
		contentPane.add(jasotakoakButton);
		
		JLabel ErreklamazioakLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakKudeatuGUI.titulua")); //$NON-NLS-1$ //$NON-NLS-2$
		ErreklamazioakLabel.setBounds(118, 10, 241, 28);
		ErreklamazioakLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(ErreklamazioakLabel);
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(483, 272, 143, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});
		contentPane.add(itxiBt);
		
		JButton erreklamazioJarriButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakKudeatuGUI.erreklamazioakJarri")); //$NON-NLS-1$ //$NON-NLS-2$
		erreklamazioJarriButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				List<Sale> boughtList = facade.getAllBought(zuremail);
				JFrame a=new ErreklamazioaJarriGUI(zuremail,boughtList);
				a.setVisible(true);
			}
		});
		erreklamazioJarriButton.setBounds(479, 86, 167, 37);
		contentPane.add(erreklamazioJarriButton);
	}
}
