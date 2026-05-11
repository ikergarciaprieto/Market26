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
import domain.Seller;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ListCellRenderer;



public class MezuakIkusiGUI extends JFrame {

	private JPanel contentPane;
	private JFrame thisFrame;
	private JScrollPane scrollPane;
    private JList<Mezua> anitzalist ;
    private DefaultListModel<Mezua> mezuList = new DefaultListModel<Mezua>();
    private JButton MezuakBilatuBtn;
    private JTextField textField;
    private JLabel lblNewLabel;
    private String userMail;
    private JLabel chatNameLabel;
    
    public void updateList(Long idChat) {
    	
    	mezuList.removeAllElements();
    	BLFacade facade = MainGUI.getBusinessLogic();
    	List<Mezua> chat = facade.mezuakLortu(idChat);
    	for(Mezua m: chat) {
    		mezuList.addElement(m);
    	}
    	
    }
	/**
	 * Create the frame.
	 */
	public MezuakIkusiGUI(String mail,Long idChat, String besteUser) {
		this.userMail = mail;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		thisFrame=this;
		
		chatNameLabel = new JLabel(besteUser);
		chatNameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		chatNameLabel.setBounds(10, 10, 300, 30);
		contentPane.add(chatNameLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 411, 252);
		contentPane.add(scrollPane);
		
		anitzalist = new JList<>();
		anitzalist.setCellRenderer(new MessageRenderer());
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
		
		updateList(idChat);
		
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
					facade.mezuaBidali(mail,idChat,t);
					updateList(idChat);
				}
			}
		});
		MezuaBidaliBtn.setBounds(449, 66, 177, 27);
		contentPane.add(MezuaBidaliBtn);
		
		
		
	}
	class MessageRenderer extends JPanel implements ListCellRenderer<Mezua> {

	    private JLabel messageLabel = new JLabel();

	    public MessageRenderer() {

	        setLayout(new BorderLayout());

	        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

	        add(messageLabel, BorderLayout.CENTER);
	    }

	    @Override
	    public Component getListCellRendererComponent(
	            JList<? extends Mezua> list,
	            Mezua value,
	            int index,
	            boolean isSelected,
	            boolean cellHasFocus) {

	        removeAll();

	        messageLabel = new JLabel(value.getMezua());

	        // Bidalitako mezua
	        if(value.getBidaliDuena().getEmail().equals(userMail)) {

	            setLayout(new FlowLayout(FlowLayout.RIGHT));

	            messageLabel.setOpaque(true);
	            messageLabel.setBackground(new Color(180, 255, 180));

	        } else {

	            // Jasotako mezua
	            setLayout(new FlowLayout(FlowLayout.LEFT));

	            messageLabel.setOpaque(true);
	            messageLabel.setBackground(new Color(220, 220, 220));
	        }

	        messageLabel.setBorder(
	                BorderFactory.createEmptyBorder(10,10,10,10));

	        add(messageLabel);

	        return this;
	    }
	}
}
