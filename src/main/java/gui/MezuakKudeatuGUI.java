package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Chat;
import domain.Sale;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;

public class MezuakKudeatuGUI extends JFrame {

	private JPanel contentPane;
	private JFrame thisFrame;
	private JScrollPane scrollPane;
    private JList anitzalist ;
    private DefaultListModel<Chat> Chatlist = new DefaultListModel<Chat>();
    private JButton ChatBilatuBtn;
    private JButton ChatIkusi;
    private JButton ChatIreki;
    private JTextField textField;
    private JLabel errortext;
    private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public MezuakKudeatuGUI(String mail) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		thisFrame=this;
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 121, 421, 175);
		contentPane.add(scrollPane);
		
		anitzalist = new JList();
		anitzalist.setModel(Chatlist);
		scrollPane.setViewportView(anitzalist);
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(483, 272, 143, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});
		contentPane.add(itxiBt);
		
		ChatBilatuBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MezuakKudeatuGUI.ChatBilatu"));
		ChatBilatuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				List<Chat> a = facade.chatakLortu(mail);
				Chatlist.removeAllElements();
				for(Chat chat : a) {
					Chatlist.addElement(chat);
				}
			}
		});
		ChatBilatuBtn.setBounds(36, 41, 143, 37);
		contentPane.add(ChatBilatuBtn);
		
		ChatIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MezuakKudeatuGUI.ChatIkusi"));
		ChatIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Mezuak ikusi gui
				Chat c = Chatlist.get(anitzalist.getSelectedIndex());
				System.out.println(c.toString()+"IKUSIKO DA");
				JFrame a = new MezuakIkusiGUI(mail,c);
				a.setVisible(true);
			}
		});
		ChatIkusi.setBounds(483, 128, 143, 37);
		contentPane.add(ChatIkusi);
		
		ChatIreki = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MezuakKudeatuGUI.ChatIreki"));
		ChatIreki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chat bat ireki norbaitekin
				errortext.setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				String t = textField.getText();
				boolean b = facade.isCorrectEmail(t);
				if(b) {
					boolean b2 =facade.chatIreki(mail,t);
					if(!b2) {//ondo joan ez da, dagoeneko eginda dago chat-a
						errortext.setVisible(true);
						errortext.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ChatGenerateError"));
					}
				}else {
					errortext.setText(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.StringIsEmptyException"));
					errortext.setVisible(true);
				}
			}
		});
		ChatIreki.setBounds(260, 41, 143, 37);
		contentPane.add(ChatIreki);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		 //$NON-NLS-1$ //$NON-NLS-2$
		textField.setBounds(413, 45, 127, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		errortext = new JLabel();//$NON-NLS-1$ //$NON-NLS-2$
		errortext.setBounds(270, 84, 270, 17);
		contentPane.add(errortext);
		
		lblNewLabel = new JLabel("Email:"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(455, 27, 45, 13);
		contentPane.add(lblNewLabel);
		errortext.setVisible(false);
	}
}
