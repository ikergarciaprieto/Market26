package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Chat;
import domain.Sale;
import exceptions.CartDoesntExistException;
import exceptions.NotEnoughMoneyException;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;


public class KarritoaIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
    private JList anitzalist ;
    private DefaultListModel<Sale> saleInfo = new DefaultListModel<Sale>();
    private JFrame thisFrame;
    private JButton ikusKarritoa;
    private JLabel abisuKarrito;
    private Double prez;
    private JLabel PrezioLabel;
    private JButton KenduProdBtn;
    private JButton erosiKarBut;
    private JButton DestroyBtn;
    
	/**
	 * Launch the application.
	 */
	public void updateKarrito(String usermail) {
		
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Sale> karrito=facade.obtainList(usermail);
		prez = facade.getKarritoPrezio(usermail);
		PrezioLabel.setText(""+prez);
		if(karrito!=null) {
			KenduProdBtn.setEnabled(true);
			saleInfo.removeAllElements();
			for(int i=0; i< karrito.size(); i++) {
				saleInfo.addElement(karrito.get(i));
			}
		}else {//karrito == null
			KenduProdBtn.setEnabled(false);
			erosiKarBut.setEnabled(false);
			DestroyBtn.setEnabled(false);
			saleInfo.removeAllElements();
		}

	}

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
		scrollPane.setBounds(36, 76, 421, 191);
		contentPane.add(scrollPane);
		
		anitzalist = new JList();
		anitzalist.setModel(saleInfo);
		scrollPane.setViewportView(anitzalist);
		
		JLabel karritoLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.karritoa"));
		karritoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		karritoLabel.setBounds(36, 25, 272, 31);
		contentPane.add(karritoLabel);
		
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(467, 275, 143, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});
		contentPane.add(itxiBt);
		
		erosiKarBut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.erosi")); //$NON-NLS-1$ //$NON-NLS-2$
		erosiKarBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					abisuKarrito.setVisible(false);
					BLFacade facade = MainGUI.getBusinessLogic();
					List<Sale> karrito=facade.obtainList(usermail);
					boolean diruEgoki=true;
					if(karrito!= null) {
						diruEgoki = facade.karrituaErosi(usermail);
						if(!diruEgoki) {
							throw new NotEnoughMoneyException();
						}else {//dirua egokia zuen
							facade.DESTROY(usermail);
						}
					}else {
						throw new CartDoesntExistException(); 
					}
					
				}catch(CartDoesntExistException e) {
					abisuKarrito.setVisible(true);
					abisuKarrito.setText(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.abisua"))	;
				}catch(NotEnoughMoneyException e) {
					abisuKarrito.setVisible(true);
					abisuKarrito.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.NotEnoughMoneyException"))	;
				}
				updateKarrito(usermail);
			}
		});
		erosiKarBut.setBounds(467, 134, 143, 31);
		contentPane.add(erosiKarBut);
		
		abisuKarrito = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		abisuKarrito.setFont(new Font("Tahoma", Font.PLAIN, 12));
		abisuKarrito.setForeground(Color.RED);
		abisuKarrito.setBounds(46, 277, 411, 24);
		contentPane.add(abisuKarrito);
		
		
		

		PrezioLabel = new JLabel(""+prez); //$NON-NLS-1$ //$NON-NLS-2$
		PrezioLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		PrezioLabel.setBounds(467, 100, 421, 24);
		contentPane.add(PrezioLabel);
		
		DestroyBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.DESTROY"));
		DestroyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.DESTROY(usermail);
				updateKarrito(usermail);
			}
		});
		DestroyBtn.setBounds(467, 210, 143, 31);
		contentPane.add(DestroyBtn);
		
		 KenduProdBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.Kendu"));
		KenduProdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(anitzalist.getSelectedIndex()!=-1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					Sale s = saleInfo.get(anitzalist.getSelectedIndex());
					facade.kenduKarritotik(s.getSaleNumber());
				}
				updateKarrito(usermail);
			}
		});
		KenduProdBtn.setBounds(467, 172, 143, 31);
		contentPane.add(KenduProdBtn);
		
		updateKarrito(usermail);
				
			
		
		

	}
}
