package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import domain.Sale;
import domain.Seller;
import domain.Mugimendua;
import javax.swing.JLabel;

public class MugimenduakIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField gDiruKant;
	private JTextField AdiruKantitatea;
	private JButton btnAteraDirua;
	private JButton btnGordeDirua;
	private JTable table;
	private DefaultTableModel movModel;
	private String sellerMail;
	private double dirua;
	private List<Mugimendua> mugimenduakList;
	private JLabel diruaString;
	private JLabel diruTotala;
	
	/**
	 * Launch the application.
	 */
	
	

	/**
	 * Create the frame.gErrorLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		gErrorLabel.setBounds(520, 246, 60, 17);
		contentPane.add(gErrorLabel);
		
	 */
	public MugimenduakIkusiGUI(double dirua, String sellerMail, List<Mugimendua> mugimenduakList ) {
		this.dirua = dirua;
		this.sellerMail = sellerMail;
		this.mugimenduakList = mugimenduakList;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 652, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAteraDirua = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.getMoney"));
		btnAteraDirua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					double ateraDirua=Double.parseDouble(AdiruKantitatea.getText());
					if(ateraDirua>0 && ateraDirua<Double.parseDouble(diruTotala.getText())) {
						facade.diruaAtera(sellerMail, ateraDirua);
						Seller kontua = facade.getUser(sellerMail);
						diruTotala.setText(""+ kontua.getDiruTotala());
						List<Mugimendua> mugList= kontua.getMugimenduak();
						
							movModel.addRow(new Object[] {
									mugList.get(mugList.size()-1).getDeskripzioa(),
									mugList.get(mugList.size()-1).getDirua(),
									new SimpleDateFormat("dd-MM-yyyy").format(mugList.get(mugList.size()-1).getData())
									
							});
						
					}
					
				}catch(NumberFormatException exception){
			}
			}});
		
		btnAteraDirua.setBounds(483, 60, 143, 46);
		contentPane.add(btnAteraDirua);
		
		btnGordeDirua = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.setMoney"));
		btnGordeDirua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					double gordeDirua=Double.parseDouble(gDiruKant.getText());
					if(gordeDirua>0) {
					facade.diruaSartu(sellerMail, gordeDirua);
					Seller kontua = facade.getUser(sellerMail);
					diruTotala.setText(""+ kontua.getDiruTotala());
					
					List<Mugimendua> mugList= kontua.getMugimenduak();
					
					
				movModel.addRow(new Object[] {
					mugList.get(mugList.size()-1).getDeskripzioa(),
						mugList.get(mugList.size()-1).getDirua(),
						new SimpleDateFormat("dd-MM-yyyy").format(mugList.get(mugList.size()-1).getData())});
								
				
					
					 }}catch(NumberFormatException exception) {
					
				}
			}
		});
		btnGordeDirua.setBounds(483, 188, 143, 46);
		contentPane.add(btnGordeDirua);
		
		gDiruKant = new JTextField();
		gDiruKant.setBounds(501, 156, 114, 21);
		contentPane.add(gDiruKant);
		gDiruKant.setColumns(10);
		
		AdiruKantitatea = new JTextField();
		AdiruKantitatea.setBounds(501, 26, 114, 21);
		contentPane.add(AdiruKantitatea);
		AdiruKantitatea.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 75, 417, 175);
		contentPane.add(scrollPane);
		
		table = new JTable();
		movModel = new DefaultTableModel();
		movModel.addColumn(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Description"));
		movModel.addColumn(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Money"));
		movModel.addColumn(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Date"));
		for (Mugimendua m : mugimenduakList) {
			movModel.addRow(new Object[] {
					m.getDeskripzioa(),
					m.getDirua(),
					new SimpleDateFormat("dd-MM-yyyy").format(m.getData())
					
			});
		}
		table.setModel(movModel);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(200); 
		table.getColumnModel().getColumn(1).setPreferredWidth(70); 
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		diruaString= new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.diruaString")); //$NON-NLS-1$ //$NON-NLS-2$
		diruaString.setBounds(104, 50, 96, 14);
		contentPane.add(diruaString);
		
		diruTotala = new JLabel(Double.toString(dirua)); //$NON-NLS-1$ //$NON-NLS-2$
		diruTotala.setBounds(260, 50, 69, 14);
		contentPane.add(diruTotala);
		
	
		
	
		
		//labelStatus.setText(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));

	}
}
