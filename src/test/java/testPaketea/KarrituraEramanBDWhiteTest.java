package testPaketea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.*;
import exceptions.*;
import testOperations.TestDataAccess;

public class KarrituraEramanBDWhiteTest {

	//sut:system under test
	static DataAccess sut=new DataAccess();

	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();

	@SuppressWarnings("unused")
	private String buyerEmail;
	private String sellerEmail;
	private int saleNum;
	private double salePrice;
	private double hasierakoDirua;

	@Before
	public void defaultValues() {
		buyerEmail = "u2@mail";
		sellerEmail = "u50@mail";
		saleNum = 1;
		salePrice = 100;
		hasierakoDirua = 1000;
	}


	@Test
	//Jarraitutako bidea: 1 - try1(F) - IF1(F) - IF2(F) - 5 - 6 - END
	//Gertatzen da user.getErosketaAnitza() != null eta sale.getSeller() == erosAnitz.getSeller() direnean
	//Erosketa anitzari salenum duen sale-a gehitu, sale hori anitza egin, erosketa anitzari sale horren prezioa gehitu
	public void test1() {

		testDA.open();
		testDA.addErosketaAnitza(buyerEmail, sellerEmail, saleNum, salePrice, hasierakoDirua, false, false);
		testDA.close();

		try {	
			sut.open();
			boolean em = sut.karrituraEraman(buyerEmail, saleNum);
			sut.close();

			testDA.open();
			Seller buyer = testDA.getSeller(buyerEmail);
			ErosketaAnitza ea = buyer.getErosketaAnitza();
			Sale s = testDA.getSale(saleNum);
			assertTrue(ea.getSales().contains(s));
			
			assertNotNull(s.getAnitza());
			
			assertTrue(em);

			testDA.close();

		}catch(Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeErosketaAnitza(saleNum, buyerEmail, sellerEmail);
			testDA.close();
		}
	}
	
	@Test
	//Jarraitutako bidea: 1 - try1(F) - IF1(F) - IF2(T) - EXC - 6 - END
	//Gertatzen da user.getErosketaAnitza() != null eta sale.getSeller() != erosAnitz.getSeller() direnean
	//Aldaketarik ez
	public void test2() {

		testDA.open();
		testDA.addErosketaAnitza(buyerEmail, sellerEmail, saleNum, salePrice, hasierakoDirua, true, false);
		testDA.close();

		try {	
			sut.open();
			boolean em = sut.karrituraEraman(buyerEmail, saleNum);
			sut.close();

			testDA.open();
			Seller buyer = testDA.getSeller(buyerEmail);
			ErosketaAnitza ea = buyer.getErosketaAnitza();
			Sale s = testDA.getSale(saleNum);
			assertTrue(!ea.getSales().contains(s));
			
			assertNull(s.getAnitza());
			
			assertTrue(!em);

			testDA.close();

		}catch(Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeErosketaAnitza(saleNum, buyerEmail, sellerEmail);
			testDA.close();
		}
	}
	
	@Test
	//Jarraitutako bidea: 1 - try1(F) - IF1(T) - 10 - 5 - 6 - END
	//Gertatzen da user.getErosketaAnitza() == null denean
	//Erosketa anitza sortu user-ari, erosketa horri salenum duen sale-a gehitu, sale hori anitza egin, erosketa anitzari sale horren prezioa gehitu
	public void test3() {

		testDA.open();
		testDA.addErosketaAnitza(buyerEmail, sellerEmail, saleNum, salePrice, hasierakoDirua, false, true);
		testDA.close();

		try {	
			sut.open();
			boolean em = sut.karrituraEraman(buyerEmail, saleNum);
			sut.close();

			testDA.open();
			Seller buyer = testDA.getSeller(buyerEmail);
			ErosketaAnitza ea = buyer.getErosketaAnitza();
			Sale s = testDA.getSale(saleNum);
			assertTrue(ea.getSales().contains(s));
			
			assertNotNull(s.getAnitza());
			
			assertTrue(em);

			testDA.close();

		}catch(Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeErosketaAnitza(saleNum, buyerEmail, sellerEmail);
			testDA.close();
		}
	}
}