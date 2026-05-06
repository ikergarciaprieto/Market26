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
import domain.Eskaera;
import domain.Sale;
import domain.Seller;
import exceptions.CartDoesntExistException;
import exceptions.NotEnoughMoneyException;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;


public class EskaerakKudeatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
    private JList anitzalist ;
    private DefaultListModel<Eskaera> eskaeraInfo = new DefaultListModel<Eskaera>();
    private JFrame thisFrame;
    private JButton eskaeraIkusi;
    private JButton eskaeraBukatuBtn;
    private JLabel error;
    private JButton eskaeraEginBtn;
    private JButton eskaeraEguneratu;

    public void eguneratu(String usermail) {
    	BLFacade facade= MainGUI.getBusinessLogic();
    	Seller u = facade.getUser(usermail);
    	List<Eskaera> list = u.getEskaerak();
    	eskaeraInfo.removeAllElements();
    	for(int i=0; i< list.size(); i++) {
			eskaeraInfo.addElement(list.get(i));
		}
    }
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public EskaerakKudeatuGUI(String usermail) {
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
		anitzalist.setModel(eskaeraInfo);
		scrollPane.setViewportView(anitzalist);
		
		JLabel karritoLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.label"));
		karritoLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		karritoLabel.setBounds(198, 35, 332, 31);
		contentPane.add(karritoLabel);
		
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(467, 275, 143, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			
			}
		});
		contentPane.add(itxiBt);
		
		eskaeraIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.eskaerakIkusi"));
		eskaeraIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(anitzalist.getSelectedIndex()!=-1) {
					error.setVisible(false);
					Eskaera s = eskaeraInfo.get(anitzalist.getSelectedIndex());
					//beti eskaeraNum bidali beste kapetara, ez eskaera bera
					
					
					
					
					
				}else {
					error.setVisible(true);
				}
			}
		});
		eskaeraIkusi.setBounds(467, 76, 143, 37);
		contentPane.add(eskaeraIkusi);
		
		eskaeraBukatuBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.eskaerakBukatu"));
		eskaeraBukatuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	if(anitzalist.getSelectedIndex()!=-1) {
		    		error.setVisible(false);
					Eskaera s = eskaeraInfo.get(anitzalist.getSelectedIndex());
					//beti eskaeraNum bidali beste kapetara, ez eskaera bera
					
					
					
					
					
				}else {
					error.setVisible(true);
				}
				eguneratu(usermail);
			}
		});
		eskaeraBukatuBtn.setBounds(467, 123, 143, 37);
		contentPane.add(eskaeraBukatuBtn);
		
		error = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.errorNotSelected")); //$NON-NLS-1$ //$NON-NLS-2$
		error.setFont(new Font("Tahoma", Font.PLAIN, 13));
		error.setForeground(Color.RED);
		error.setBounds(36, 277, 421, 35);
		contentPane.add(error);
		
		eskaeraEginBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.eskaeraSortu"));
		eskaeraEginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new EskaeraSortuGUI(usermail);
				a.setVisible(true);
			}
		});
		eskaeraEginBtn.setBounds(467, 170, 143, 37);
		contentPane.add(eskaeraEginBtn);
		
		eskaeraEguneratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaerakKudeatuGUI.eskaerakEguneratu"));
		eskaeraEguneratu.setFont(new Font("Tahoma", Font.PLAIN, 11));
		eskaeraEguneratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eguneratu(usermail);
			}
		});
		eskaeraEguneratu.setBounds(10, 35, 152, 31);
		contentPane.add(eskaeraEguneratu);
		error.setVisible(false);
		
		

				
			
		
		eguneratu(usermail);
	}
}
