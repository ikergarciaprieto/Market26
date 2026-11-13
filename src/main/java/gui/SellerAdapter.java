
package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import domain.Seller;
import domain.Sale;

public class SellerAdapter extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<Sale> sales;
	private String[] columnNames = { "title", "description", "price" };

	public SellerAdapter(Seller seller) {
		this.sales = seller.getSales();
	}

	@Override
	public int getRowCount() {
		return sales.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Sale sale = sales.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return sale.getTitle();

		case 1:
			return sale.getDescription();

		case 2:
			return sale.getPrice();

		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
