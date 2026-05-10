package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import domain.Seller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class QuerySalesGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelProducts = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products")); 

	private JButton jButtonSearch = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search")); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();

	private JButton karritoButton;
	private ImageIcon errekIcon;
	private DefaultTableModel tableModelProducts;

	private JFrame thisFrame; 

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.PublicationDate"),

	};
	private JTextField jTextFieldSearch;
	private JLabel badgeLabel;
	
	public void igoKarritoNum() {
	    int update = Integer.parseInt(badgeLabel.getText());
	    badgeLabel.setText(String.valueOf(update + 1));
	    badgeLabel.setVisible(true);
	}
	
	public void jaitsiKarritoNum() {
	    int update = Integer.parseInt(badgeLabel.getText());

	    if(update > 0) {
	        badgeLabel.setText(String.valueOf(update - 1));
	    }
	    if (update == 1) {
	    	badgeLabel.setVisible(false);
	    }
	}
	
	public void updateNum() {
		badgeLabel.setText(String.valueOf(0));
		badgeLabel.setVisible(false);
	}
	

	public QuerySalesGUI(String email) {
		tableProducts.setEnabled(false);
		thisFrame=this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.FindProducts"));
		jLabelProducts.setBounds(52, 108, 427, 16);
		this.getContentPane().add(jLabelProducts);

		jButtonClose.setBounds(new Rectangle(220, 379, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				thisFrame.setVisible(false);

			}
		});		
		
		this.getContentPane().add(jButtonClose, null);

		scrollPanelProducts.setBounds(new Rectangle(52, 137, 459, 150));

		scrollPanelProducts.setViewportView(tableProducts);
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);

		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(4); // another column added to allocate ride objects

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);


		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3)); // not shown in JTable

		this.getContentPane().add(scrollPanelProducts, null);
		
		jTextFieldSearch = new JTextField();
		jTextFieldSearch.setBounds(52, 56, 357, 26);
		getContentPane().add(jTextFieldSearch);
		jTextFieldSearch.setColumns(10);
		
		 jButtonSearch.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		try {
					tableModelProducts.setDataVector(null, columnNamesProducts);
					tableModelProducts.setColumnCount(4); // another column added to allocate product object

					BLFacade facade = MainGUI.getBusinessLogic();
					Date today = UtilDate.trim(new Date());

					List<domain.Sale> sales=facade.getPublishedSales(jTextFieldSearch.getText(),today);

					if (sales.isEmpty() ) jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.NoProducts"));
					else jLabelProducts.setText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Products"));
					for (domain.Sale sale:sales){
						Vector<Object> row = new Vector<Object>();
						row.add(sale.getTitle());
						row.add(sale.getPrice());
						row.add(new SimpleDateFormat("dd-MM-yyyy").format(sale.getPublicationDate()));
						row.add(sale); // product object added in order to obtain it with tableModelProducts.getValueAt(i,2)
						tableModelProducts.addRow(row);		
					}
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
				tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
				tableProducts.getColumnModel().getColumn(1).setPreferredWidth(70);

				tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(3)); // not shown in JTable
		 		
		 	}
		 });
		jButtonSearch.setBounds(427, 56, 117, 29);
		getContentPane().add(jButtonSearch);
		
		URL iconURL = getClass().getResource("/images/carro-de-la-compra.png");
		
		ImageIcon originalIcon = new ImageIcon(iconURL);
		Image img = originalIcon.getImage();
		Image scaledImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		errekIcon = new ImageIcon(scaledImg);
		karritoButton = new JButton(errekIcon);
		karritoButton.setToolTipText(ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.karritoaikusi"));
		karritoButton.setBounds(561, 381, 56, 49);
		getContentPane().add(karritoButton);
		
		//Zenbaki gorria
		int n = 0;
		BLFacade facade = MainGUI.getBusinessLogic();
		Seller user = facade.getUser(email);
		if (user.getKarrito() != null) {
			for(Sale s : user.getKarrito().getSales()) {
				n = n + 1;
				System.out.println(s);
			}
		}
				
		
		karritoButton.setLayout(null);
		
		badgeLabel = new JLabel(String.valueOf(n));
		badgeLabel.setBounds(47, 0, 10,16);
		badgeLabel.setFont(new Font("Arial", Font.BOLD, 10));
		badgeLabel.setForeground(Color.WHITE);
		badgeLabel.setBackground(Color.RED);
		badgeLabel.setOpaque(true);
		badgeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		badgeLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		
		if(n==0) {
			badgeLabel.setVisible(false);
		}
		else {
			badgeLabel.setVisible(true); 
		}
		
		karritoButton.add(badgeLabel);
		karritoButton.setComponentZOrder(badgeLabel, 0);

		karritoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a= new KarritoaIkusiGUI(email, QuerySalesGUI.this);
				a.setVisible(true);
			}
		});
		
		
	    
		tableProducts.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {
		            
		            if(mouseEvent.getClickCount() == 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	Sale s=(Sale) tableModelProducts.getValueAt(row, 3);
			            new ShowSaleGUI(s,email, QuerySalesGUI.this);
		            }
		        }
		 });
	}
}
