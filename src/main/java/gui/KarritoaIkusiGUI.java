package gui;

import java.awt.EventQueue;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;

public class KarritoaIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
    private JList anitzalist ;
    private DefaultListModel<Sale> saleInfo = new DefaultListModel<Sale>();
    
    
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public KarritoaIkusiGUI(String usermail) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 36, 271, 175);
		contentPane.add(scrollPane);
		
		anitzalist = new JList();
		anitzalist.setModel(saleInfo);
		scrollPane.setViewportView(anitzalist);
		
		JLabel karritoLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("KarritoaIkusiGUI.karritoa"));
		karritoLabel.setBounds(107, 7, 131, 17);
		contentPane.add(karritoLabel);
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Sale> karrito=facade.obtainList(usermail);
		if(karrito!=null) {
		saleInfo.removeAllElements();
		for(int i=0; i< karrito.size(); i++) {
			saleInfo.addElement(karrito.get(i));
		}
		}
		
		
		
		

	}
}
