package gui;

import businessLogic.BLFacade;
import businessLogic.BLFactory;
import domain.Seller;

public class MainAdapter {
	public static void main(String[] args) {
//		the	BL	is	local
		boolean isLocal = true;
		BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);
		Seller s = blFacade.isUserLogin("Aitor Fernandez", "aurrera");
		SellerTable st = new SellerTable(s);
		st.setVisible(true);
	}
}
