package gui;

import java.awt.Color;
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
import domain.Mezua;
import domain.Sale;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;

public class MezuakIkusiGUI extends JFrame {

	private JPanel contentPane;
	private JFrame thisFrame;
	private JScrollPane scrollPane;
    private JList anitzalist ;
    private DefaultListModel<Mezua> mezuList = new DefaultListModel<Mezua>();
    private JButton MezuakBilatuBtn;
    private JTextField textField;
    private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public MezuakIkusiGUI(String mail,Chat chat) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		thisFrame=this;
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 411, 252);
		contentPane.add(scrollPane);
		
		anitzalist = new JList();
		anitzalist.setModel(mezuList);
		scrollPane.setViewportView(anitzalist);
		
		JButton itxiBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		itxiBt.setBounds(483, 272, 143, 37);
		itxiBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);		
			}
		});
		contentPane.add(itxiBt);
		
		MezuakBilatuBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MezuakIkusiGUI.MezuakBilatu"));
		MezuakBilatuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mezuList.removeAllElements();
				
				for(Mezua m: chat.getMezuak()) {
					mezuList.addElement(m);
				}
				
			}
		});
		MezuakBilatuBtn.setBounds(10, 10, 177, 37);
		contentPane.add(MezuakBilatuBtn);
		
		textField = new JTextField();
		textField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textField.setBounds(449, 103, 177, 45);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.StringIsEmptyException")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(449, 158, 177, 58);
		contentPane.add(lblNewLabel);
		lblNewLabel.setVisible(false);
		
		JButton MezuaBidaliBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MezuakIkusiGUI.MezuaBidali"));
		MezuaBidaliBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String t = textField.getText();
				lblNewLabel.setVisible(false);
				if(t.isEmpty()) {
					lblNewLabel.setVisible(true);
				}else {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.mezuaBidali(mail,chat,t);
				}
			}
		});
		MezuaBidaliBtn.setBounds(449, 66, 177, 27);
		contentPane.add(MezuaBidaliBtn);
		
		
		
	}
}
