package gui;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Sale;
import domain.Seller;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowAccount extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JLabel LblUserName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Username"));
    private JLabel lblSaleList = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Salelist"));
    private JButton btnShowSale = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ShowSale"));
    private JComboBox sales;
    private DefaultComboBoxModel saleList= new DefaultComboBoxModel();
    private Sale selectedSale;
    private JFrame thisFrame;
    private JButton jButtonClose;
    private JButton closeButton;
    private JLabel userName;
    
	/**
	 * Launch the application.
	 */
	
	

	/**
	 * Create the frame.
	 */
	public ShowAccount(String User,ArrayList<Sale>SaleList, String zuremail) {
		thisFrame=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		LblUserName.setBounds(98, 12, 129, 17);
		contentPane.add(LblUserName);
		
		sales = new JComboBox();
		sales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedSale= (Sale) sales.getSelectedItem();
				if(selectedSale==null) {
					btnShowSale.setEnabled(false);
				}else {
					btnShowSale.setEnabled(true);
				}
			}
		});
		sales.setBounds(83, 127, 213, 26);
		contentPane.add(sales);
		sales.setModel(saleList);
		for(int i=0; i< SaleList.size(); i++) {
			saleList.addElement(SaleList.get(i));
			
		}
		
		
		lblSaleList.setBounds(83, 100, 237, 17);
		contentPane.add(lblSaleList);
		
		btnShowSale.setBounds(139, 207, 105, 27);
		contentPane.add(btnShowSale);
		
		JLabel lblWarn = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		lblWarn.setBounds(166, 179, 60, 17);
		contentPane.add(lblWarn);
		
		closeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
			}
		});
		closeButton.setBounds(341, 232, 85, 21);
		contentPane.add(closeButton);
		
		userName = new JLabel(User);
		userName.setBounds(98, 56, 193, 13);
		contentPane.add(userName);
		
		
		btnShowSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selectedSale!=null) {
					JFrame a=new ShowSaleGUI(selectedSale,zuremail);
					a.setVisible(true);
				}else {
					lblWarn.setText(ResourceBundle.getBundle("Etiquetas").getString("Warning"));
				}
				
			}
		});
		

	}
}
