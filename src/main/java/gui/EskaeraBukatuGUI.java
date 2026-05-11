package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Eskaera;
import domain.Sale;
import domain.Seller;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class EskaeraBukatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton eskaerakIkusi;
	private JScrollPane scrollPane;
	private JButton eskaeraSartu;
	private DefaultListModel<Sale> saleInfo = new DefaultListModel<Sale>();
	private DefaultComboBoxModel <Sale> sInfo= new DefaultComboBoxModel <Sale>();
	private JFrame thisFrame;
	private JLabel error;
	private JList eskaeraList;
	
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public EskaeraBukatuGUI(String usermail,long eskaeraNum) {
	thisFrame=this;
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		BLFacade facade = MainGUI.getBusinessLogic();
		Eskaera eskaera=facade.getEskaera(eskaeraNum);
		List<Sale> salelist=eskaera.getSales();
		//saleInfo.removeAllElements();
		for(Sale s: salelist) {
			saleInfo.addElement(s);
		}
		JButton eskaeraBukatuBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaeraBukatuGUI.bukBtn"));
		eskaeraBukatuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				error.setText(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.errorNotSelected"));
				error.setVisible(false);
				if(eskaeraList.getSelectedIndex()!=-1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					Seller ni = facade.getUser(usermail);
					Sale s = salelist.get(eskaeraList.getSelectedIndex());
					System.out.println("Aukeratutako sale: "+s.getTitle());
					if(ni.getDiruTotala()>=s.getPrice()) {//begiratu diru egoki daukan
						facade.buy(s.getSeller().getEmail(), s.getStatus(), usermail);//eskaera buy
						facade.DESTROYEskaera(s.getEskaeran().getEskaeraNumber());//eskaera ezabatu
						thisFrame.setVisible(false);
					}else {
						error.setVisible(true);
						error.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.NotEnoughMoneyException"));
					}
				}else {
					error.setVisible(true);
				}
				
			}
		});
		eskaeraBukatuBtn.setBounds(29, 27, 384, 27);
		contentPane.add(eskaeraBukatuBtn);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 93, 384, 130);
		contentPane.add(scrollPane);
		
		eskaeraList = new JList();
		scrollPane.setViewportView(eskaeraList);
		eskaeraList.setModel(saleInfo);
		
		JButton itxiButto = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		itxiButto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.setVisible(false);
			}
		});
		itxiButto.setBounds(308, 226, 105, 27);
		contentPane.add(itxiButto);
		
		JButton eskaeraBukatuBakarrikBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaeraBukatuGUI.bukBakarrikBtn"));
		eskaeraBukatuBakarrikBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				error.setVisible(false);
				error.setText(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.errorNotSelected"));
				//if(eskaeraList.getSelectedIndex()!=-1) {
				//	BLFacade facade = MainGUI.getBusinessLogic();
				//	Eskaera e = facade.getEskaera(eskaeraNum);
					facade.DESTROYEskaera(eskaera.getEskaeraNumber());//eskaera ezabatu
					//facade.buy(s.getSeller().getEmail(), s.getStatus(), usermail);eskaera buy
				//}else {
				//	error.setVisible(true);
				//}
				thisFrame.setVisible(false);
			}
		});
		eskaeraBukatuBakarrikBtn.setBounds(29, 56, 384, 27);
		contentPane.add(eskaeraBukatuBakarrikBtn);
		
		error = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.errorNotSelected")); //$NON-NLS-1$ //$NON-NLS-2$
		error.setFont(new Font("Tahoma", Font.PLAIN, 10));
		error.setForeground(Color.RED);
		error.setBounds(10, 226, 280, 27);
		contentPane.add(error);
		error.setVisible(false);
		
}
}
