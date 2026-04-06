package gui;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.Erreklamazioa;
import domain.Mugimendua;
import domain.Sale;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErreklamazioOnartuakGUI extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel movModel;
	private List<Erreklamazioa> errekList;
	private JLabel TitleLabel;
	private JFrame thisFrame;
	private JButton closeButton;
	
	public ErreklamazioOnartuakGUI(String usermail,List<Erreklamazioa> errekList) {
		thisFrame=this;
		this.errekList = errekList;
		for(Erreklamazioa e: errekList) {
			System.out.println(e);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 473, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 75, 400, 234);
		contentPane.add(scrollPane);

		table = new JTable();
		movModel = new DefaultTableModel();
		movModel.addColumn(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioakOnartuGUI.Author"));
		movModel.addColumn(ResourceBundle.getBundle("Etiquetas").getString("MugimenduakIkusiGUI.Description"));
		movModel.addColumn(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioOnartuGUI.Accepted"));
		for (Erreklamazioa e : errekList) {
			if(e.getOnartua().equals("Accepted") || e.getOnartua().equals("Onartua") || e.getOnartua().equals("Aceptada")) {
				e.setOnartua(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.Onartua"));
			}
			else {
				e.setOnartua(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.EzOnartua"));
			}
			movModel.addRow(new Object[] {
					e.getErreklamatzenDuena().getName(),
					e.getSale().getTitle(),
					e.getOnartua()

			});
		}
		table.setModel(movModel);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(130);
		table.getColumnModel().getColumn(1).setPreferredWidth(200); 
		table.getColumnModel().getColumn(2).setPreferredWidth(70); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TitleLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ErreklamazioOnartuakGUI.Titulua"));
		TitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		TitleLabel.setBounds(75, 24, 300, 25);
		contentPane.add(TitleLabel);
		
		closeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
			}
		});
		closeButton.setBounds(362, 335, 85, 21);
		contentPane.add(closeButton);
	}
}
