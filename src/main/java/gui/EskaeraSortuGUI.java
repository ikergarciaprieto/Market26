package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Erreklamazioa;
import domain.Sale;
import domain.Seller;
import exceptions.StringIsEmptyException;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JTextField;

public class EskaeraSortuGUI extends JFrame {

	private JPanel contentPane;
	private JFrame thisFrame;
	private JTextField titleField;
	private JTextField deskField;
	private JLabel labelTitle;
	private JLabel labelDesk;
	private JLabel lblNewLabel;


	/**
	 * Create the frame.
	 */
	public EskaeraSortuGUI(String zuremail) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		thisFrame=this;
		
		
		
		
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(483, 272, 143, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});
		contentPane.add(itxiBt);
		
		titleField = new JTextField(); //$NON-NLS-1$ //$NON-NLS-2$
		titleField.setBounds(73, 48, 456, 37);
		contentPane.add(titleField);
		titleField.setColumns(10);
		
		deskField = new JTextField();
		deskField.setBounds(73, 135, 456, 37);
		contentPane.add(deskField);
		deskField.setColumns(10);
		
		JButton sortuBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EskaeraSortuGUI.sortu")); //$NON-NLS-1$ //$NON-NLS-2$
		sortuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setVisible(false);
				String title = titleField.getText();
				String desk = deskField.getText();
				try {
					if(title.isEmpty()||desk.isEmpty()) {
						throw new StringIsEmptyException();
					}
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.eskaeraSortu(title,desk,zuremail);
					thisFrame.setVisible(false);//errorerik egon ezean leihioa ixten da
				}catch(StringIsEmptyException a) {
					lblNewLabel.setVisible(true);
					lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.StringIsEmptyException"));
				}
			}
		});
		sortuBtn.setBounds(220, 193, 161, 28);
		contentPane.add(sortuBtn);
		
		labelTitle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EskaeraSortuGUI.title")); //$NON-NLS-1$ //$NON-NLS-2$
		labelTitle.setBounds(73, 20, 161, 19);
		contentPane.add(labelTitle);
		
		labelDesk = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EskaeraSortuGUI.desk"));
		labelDesk.setBounds(73, 106, 161, 19);
		contentPane.add(labelDesk);
		
		lblNewLabel = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(73, 238, 456, 24);
		contentPane.add(lblNewLabel);
		lblNewLabel.setVisible(false);
	
	}
}
