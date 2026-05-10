package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
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

public class EskaerakIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox saleBox;
	private JButton eskaerakIkusi;
	private JScrollPane scrollPane;
	private JList eskaeraList;
	private JButton eskaeraSartu;
	private DefaultListModel<Eskaera> eskInfo = new DefaultListModel<Eskaera>();
	private DefaultComboBoxModel <Sale> sInfo= new DefaultComboBoxModel <Sale>();
	private JFrame thisFrame;
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public EskaerakIkusiGUI(String usermail) {
	thisFrame=this;
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		saleBox = new JComboBox();
		saleBox.setBounds(77, 27, 158, 26);
		contentPane.add(saleBox);
		saleBox.setModel(sInfo);
		BLFacade facade = MainGUI.getBusinessLogic();
		Seller u=facade.getUser(usermail);
		List<Sale> salist=u.getSales();
		sInfo.removeAllElements();
		for(int i=0; i<salist.size(); i++) {
			if(!(salist.get(i).getEskaeran()!= null)) {
			sInfo.addElement(salist.get(i));
		}
		}
		JButton eskaerakIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakIkusiGUI.EskaerakIkusi"));
		eskaerakIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				List<Eskaera> eskaList=facade.getEskaerak();
				eskInfo.removeAllElements();
				Seller u=facade.getUser(usermail);
				for(int i=0; i<eskaList.size(); i++) {
					if(eskaList.get(i).getUser()!=u) {
					eskInfo.addElement(eskaList.get(i));
					}
				}
				
				
				
			}
		});
		eskaerakIkusi.setBounds(276, 27, 137, 27);
		contentPane.add(eskaerakIkusi);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 65, 174, 164);
		contentPane.add(scrollPane);
		
		JList eskaeraList = new JList();
		scrollPane.setViewportView(eskaeraList);
		eskaeraList.setModel(eskInfo);
		
		JButton eskaeraSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakIkusiGUI.sartu"));
		eskaeraSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sale sale= (Sale) saleBox.getSelectedItem();
				Eskaera esk =(Eskaera) eskaeraList.getSelectedValue();
				if(sale!=null && esk != null) {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.addSaletoEsk(sale.getSaleNumber(),esk.getEskaeraNumber());
					sInfo.removeAllElements();
					for(int i=0; i<salist.size(); i++) {
						if(!(salist.get(i).getEskaeran()!= null)) {
						sInfo.addElement(salist.get(i));}
					}
				}
			}
		});
		eskaeraSartu.setBounds(278, 111, 105, 27);
		contentPane.add(eskaeraSartu);
		
		JButton itxiButto = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		itxiButto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.setVisible(false);
			}
		});
		itxiButto.setBounds(12, 227, 105, 27);
		contentPane.add(itxiButto);
		
		
}
}
