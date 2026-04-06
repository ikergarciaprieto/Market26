package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erreklamazioa;
import domain.Sale;
import domain.Seller;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class ErreklamazioakKudeatuGUI extends JFrame {

	private JPanel contentPane;
	private JFrame thisFrame;
	private JList erreList;
	private DefaultListModel<Erreklamazioa> erreInfo = new DefaultListModel<Erreklamazioa>();
	private JLabel errorLabel;
	private JLabel badgeLabel;
	private JButton errekButton;
	private ImageIcon errekIcon;
	private List<Erreklamazioa> errekOnartuList;


	/**
	 * Create the frame.
	 */
	public ErreklamazioakKudeatuGUI(String zuremail) {
		errekOnartuList = new ArrayList<Erreklamazioa>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		thisFrame=this;
		
		
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/mail.png"));
		Image scaledImg = originalIcon.getImage().getScaledInstance(47, 43, Image.SCALE_SMOOTH);
		errekIcon = new ImageIcon(scaledImg);
		errekButton = new JButton(errekIcon);
		URL imgUrl = getClass().getResource("/images/mail.png");
		System.out.println("URL IMAGEN = " + imgUrl);
		
		
		errekButton.setBorderPainted(true);
		errekButton.setContentAreaFilled(true);
		errekButton.setFocusPainted(true);
		errekButton.setLayout(null);

		
		errekButton.setBounds(565, 10, 47, 43);  
		contentPane.add(errekButton);

		//Zenbaki gorria
		int n = 0;
		BLFacade facade = MainGUI.getBusinessLogic();
		Seller user = facade.getUser(zuremail);
		for(Erreklamazioa e : user.getJasotakoErreklamazioak()) {
			if (!e.getOnartua().equals("ez begiratuta")) {
				errekOnartuList.add(e);
				n = n + 1;
			}
		}
		for(Erreklamazioa e : user.getJarritakoErreklamazioak()) {
			if (!e.getOnartua().equals("ez begiratuta")) {
				errekOnartuList.add(e);
				n = n + 1;
			}
		}
		
		badgeLabel = new JLabel(String.valueOf(n));
		badgeLabel.setBounds(32, -2, 15, 15);
		badgeLabel.setFont(new Font("Arial", Font.BOLD, 10));
		badgeLabel.setForeground(Color.WHITE);
		badgeLabel.setBackground(Color.RED);
		badgeLabel.setOpaque(true);
		badgeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		badgeLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		if(n==0) {
			badgeLabel.setVisible(false);
		}
		else {
			badgeLabel.setVisible(true); 
		}
		errekButton.add(badgeLabel);
		errekButton.setComponentZOrder(badgeLabel, 0);
		errekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ErreklamazioOnartuakGUI(zuremail, errekOnartuList);
				a.setVisible(true);
				badgeLabel.setVisible(false);
			}
		});

		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 75, 420, 234);
		contentPane.add(scrollPane);
		
		erreList = new JList<Erreklamazioa>();
		erreList.setModel(erreInfo);
		scrollPane.setViewportView(erreList);
		
		
		
		JButton jarritakoakButton= new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakKudeatuGUI.jarritakoak"));
		jarritakoakButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//JARRITAKO ERREKLAMAZIOAK
				BLFacade facade = MainGUI.getBusinessLogic();
				List<Erreklamazioa> jErreList=facade.getJrerreklamazioak(zuremail);
		
					erreInfo.removeAllElements();
					for(int i=0; i< jErreList.size(); i++) {
						erreInfo.addElement(jErreList.get(i));
						
					}
				
				
			}
		});
		jarritakoakButton.setBounds(479, 148, 167, 21);
		contentPane.add(jarritakoakButton);
		
		JButton jasotakoakButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakKudeatuGUI.jasotakoak"));
		jasotakoakButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//JASOTAKO ERREKLAMAZIOAK
				
				BLFacade facade = MainGUI.getBusinessLogic();
				List<Erreklamazioa> jErreList=facade.getJserreklamazioak(zuremail);
					
					erreInfo.removeAllElements();
					for(int i=0; i< jErreList.size(); i++) {
						erreInfo.addElement(jErreList.get(i));
						
					}
					
				
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
