package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;

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
import exceptions.UserOrPasswordIsWrongException;
import domain.Mugimendua;
import javax.swing.JLabel;

import exceptions.MoneyIsNegativeException;

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
	private JLabel errorText1;
	private JLabel errorText2;
	private JLabel errorText3;
	private JLabel errorText4;
	private JLabel errorText5;
	private JFrame thisFrame;

	/**
	 * Launch the application.
	 */



	/**
	 * Create the frame.gErrorLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		gErrorLabel.setBounds(520, 246, 60, 17);
		contentPane.add(gErrorLabel);

	 */
	public MugimenduakIkusiGUI(double dirua, String sellerMail, List<Mugimendua> mugimenduakList ) {
		thisFrame=this;
		this.dirua = dirua;
		this.sellerMail = sellerMail;
		this.mugimenduakList = mugimenduakList;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAteraDirua = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.getMoney"));
		btnAteraDirua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					double ateraDirua = Double.parseDouble(AdiruKantitatea.getText());
					if(ateraDirua>0 && ateraDirua<=Double.parseDouble(diruTotala.getText())) {
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
					if (ateraDirua <= 0) {
						throw new MoneyIsNegativeException();
					}

					if (ateraDirua > Double.parseDouble(diruTotala.getText())) {
					    throw new IllegalStateException();
					}

				}catch(NumberFormatException exception){
					System.out.println("NumberFormatException");
					errorText4.setVisible(false);
					errorText5.setVisible(false);
					errorText1.setVisible(true);
					errorText1.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.NumberFormatException"));
				}
				catch(MoneyIsNegativeException exception) {
					System.out.println("MoneyIsNegativeException");
					errorText1.setVisible(false);
					errorText5.setVisible(false);
					errorText4.setVisible(true);
					errorText4.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.MoneyIsNegativeException"));
				}
				catch(IllegalStateException exception) {
					System.out.println("IllegalStateException");
					errorText1.setVisible(false);
					errorText4.setVisible(false);
					errorText5.setVisible(true);
					errorText5.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.IllegalStateException"));
				}
			}});

		btnAteraDirua.setBounds(483, 60, 143, 46);
		contentPane.add(btnAteraDirua);

		btnGordeDirua = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.setMoney"));
		btnGordeDirua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					double gordeDirua = Double.parseDouble(gDiruKant.getText());
					if(gordeDirua>0) {
						facade.diruaSartu(sellerMail, gordeDirua);
						Seller kontua = facade.getUser(sellerMail);
						diruTotala.setText(""+ kontua.getDiruTotala());

						List<Mugimendua> mugList= kontua.getMugimenduak();


						movModel.addRow(new Object[] {
								mugList.get(mugList.size()-1).getDeskripzioa(),
								mugList.get(mugList.size()-1).getDirua(),
								new SimpleDateFormat("dd-MM-yyyy").format(mugList.get(mugList.size()-1).getData())});



					}
					else{    
						throw new MoneyIsNegativeException();
					}
					}catch(NumberFormatException exception) {
						System.out.println("NumberFormatException");
						errorText3.setVisible(false);
						errorText2.setVisible(true);
						errorText2.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.NumberFormatException"));

					}
				catch(MoneyIsNegativeException exception) {
					System.out.println("MoneyIsNegativeException");
					errorText2.setVisible(false);
					errorText3.setVisible(true);
					errorText3.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.MoneyIsNegativeException"));

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
		scrollPane.setBounds(37, 75, 420, 234);
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
					new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(m.getData())

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
		
		errorText1 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorText1.setForeground(Color.RED);
		errorText1.setBounds(467, 117, 169, 14);
		contentPane.add(errorText1);
		
		errorText2 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorText2.setForeground(Color.RED);
		errorText2.setBounds(467, 247, 169, 14);
		contentPane.add(errorText2);
		
		errorText3 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorText3.setForeground(Color.RED);
		errorText3.setBounds(467, 247, 169, 14);
		contentPane.add(errorText3);
		
		errorText4 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorText4.setForeground(Color.RED);
		errorText4.setBounds(467, 117, 169, 14);
		contentPane.add(errorText4);
		
		errorText5 = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		errorText5.setForeground(Color.RED);
		errorText5.setBounds(467, 117, 187, 14);
		contentPane.add(errorText5);
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(483, 272, 143, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});
		contentPane.add(itxiBt);
		
		JLabel MugimenduakLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Mov")); //$NON-NLS-1$ //$NON-NLS-2$
		MugimenduakLabel.setBounds(186, 11, 143, 28);
		MugimenduakLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(MugimenduakLabel);





		//labelStatus.setText(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));

	}
}
