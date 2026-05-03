package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;
import exceptions.CartDoesntExistException;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;


public class KarritoaIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
    private JList anitzalist ;
    private DefaultListModel<Sale> saleInfo = new DefaultListModel<Sale>();
    private JFrame thisFrame;
    private JButton ikusKarritoa;
    private JLabel abisuKarrito;
    
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public KarritoaIkusiGUI(String usermail) {
		thisFrame= this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 88, 421, 175);
		contentPane.add(scrollPane);
		
		anitzalist = new JList();
		anitzalist.setModel(saleInfo);
		scrollPane.setViewportView(anitzalist);
		
		JLabel karritoLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.karritoa"));
		karritoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		karritoLabel.setBounds(156, 29, 272, 31);
		contentPane.add(karritoLabel);
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Sale> karrito=facade.obtainList(usermail);
		if(karrito!=null) {
			saleInfo.removeAllElements();
			for(int i=0; i< karrito.size(); i++) {
				saleInfo.addElement(karrito.get(i));
			}
		}
		
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(467, 275, 143, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});
		contentPane.add(itxiBt);
		
		JButton erosiKarBut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.erosi")); //$NON-NLS-1$ //$NON-NLS-2$
		erosiKarBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					List<Sale> karrito=facade.obtainList(usermail);
					if(karrito!= null) {
						facade.karrituaErosi(usermail);
					}else {
						throw new CartDoesntExistException(); 
					}
					
				}catch(CartDoesntExistException e) {
					abisuKarrito.setText(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.abisua"))	;
				}
			}
		});
		erosiKarBut.setBounds(499, 122, 105, 27);
		contentPane.add(erosiKarBut);
		
		abisuKarrito = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		abisuKarrito.setBounds(460, 185, 188, 17);
		contentPane.add(abisuKarrito);
		
		
				
			
		
		

	}
}
