package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Eskaera;
import domain.Sale;

import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class EskaerakIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox comboBox;
	private JButton eskaerakIkusi;
	private JScrollPane scrollPane;
	private JList eskaeraList;
	private JButton eskaeraSartu;
	private DefaultListModel<Eskaera> eskInfo = new DefaultListModel<Eskaera>();
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public EskaerakIkusiGUI(String usermail) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(77, 27, 158, 26);
		contentPane.add(comboBox);
		
		JButton eskaerakIkusi = new JButton("Eskaerak Ikusi");
		eskaerakIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				List<Eskaera> eskaList=facade.getEskaerak();
				eskInfo.removeAllElements();
				for(int i=0; i<eskaList.size(); i++) {
					
					eskInfo.addElement(eskaList.get(i));
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
		
		JButton eskaeraSartu = new JButton("Eskaeran Sartu");
		eskaeraSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		eskaeraSartu.setBounds(278, 111, 105, 27);
		contentPane.add(eskaeraSartu);

	}
}
