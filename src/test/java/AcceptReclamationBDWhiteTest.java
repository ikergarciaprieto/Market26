
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
import domain.Erreklamazioa;
import domain.Sale;
import domain.Seller;
import exceptions.MustBeLaterThanTodayException;
import exceptions.ParamNullException;
import exceptions.SaleAlreadyExistException;
import testOperations.TestDataAccess;

public class AcceptReclamationBDWhiteTest {

	//sut:system under test
	static DataAccess sut=new DataAccess();

	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();

	@SuppressWarnings("unused")
	private  long errekId;
	private String buyerEmail;
	private String sellerEmail;
	private double salePrice;
	private double hasierakoDirua;



	@Before
	public void defaultValues() {
		buyerEmail = "buyerTest@ehu.eus";
		sellerEmail = "sellerTest@ehu.eus";
		salePrice = 10;
		hasierakoDirua = 1000;
	}


	@Test
	//Jarraitutako bidea: try1(F) - 3 - IF1(T) - IF2(T) - 7 - 9 - END
	//Gertatzen da errek != null denean eta b== true
	//Emaitza erreklamazioa "Onartua" bezala ezartzea da eta erosleari dirua bueltatzea
	public void test1() {
		boolean b = true; 

		testDA.open();
		errekId = testDA.addErreklamazioa(buyerEmail, sellerEmail, salePrice, hasierakoDirua);
		testDA.close();

		try {	
			sut.open();
			sut.acceptReclamation(b, errekId);
			sut.close();

			testDA.open();
			Erreklamazioa errek = testDA.getErreklamazioa(errekId);
			assertNotNull(errek);

			assertEquals("Aceptada", errek.getOnartua());

			Seller buyer = testDA.getSeller(buyerEmail);
			assertEquals(hasierakoDirua + salePrice, buyer.getDiruTotala(), 0.01);
			testDA.close();

		}catch(Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeErreklamazioa(errekId, buyerEmail, sellerEmail);
			testDA.close();
		}
	}
	@Test
	//Jarraitutako bidea: try1(F) - 3 - IF1(T) - IF2(F) - 8 - 9 - END
	//Gertatzen da errek != null denean eta b != true
	//Emaitza erreklamazioa "EzOnartua" bezala ezartzea da eta erosleari dirua ez bueltatzea
	public void test2() {
		boolean b = false;

		testDA.open();
		errekId = testDA.addErreklamazioa(buyerEmail, sellerEmail, salePrice, hasierakoDirua);
		testDA.close();

		try {
			sut.open();
			sut.acceptReclamation(b, errekId);
			sut.close();

			testDA.open();
			Erreklamazioa errek = testDA.getErreklamazioa(errekId);
			assertNotNull(errek);

			assertEquals("Denegada", errek.getOnartua());
			assertFalse(errek.getSale().isErreklamatuta());

			Seller buyer = testDA.getSeller(buyerEmail);
			assertEquals(hasierakoDirua, buyer.getDiruTotala(), 0.01);
			testDA.close();

		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeErreklamazioa(errekId, buyerEmail, sellerEmail);
			testDA.close();
		}
	}

	@Test
	//Jarraitutako bidea: try1(T) - EXC - END
	//Gertatzen da errekID negatiboa edo positiboa ez den zenbaki bat ez izatea eta b null izatea
	//Helburua da exzepzio hori hartzen duenean test-a bete dela argitzea
	public void test3() {
		boolean b = true;
		
		testDA.open();
	    errekId = testDA.addErreklamazioaWithoutSale(buyerEmail, sellerEmail, salePrice, hasierakoDirua);
	    testDA.close();
	    
		try {
			sut.open();
			sut.acceptReclamation(b,errekId);
			sut.close();
			
			fail();

		} catch (Exception e) {
			assertTrue(true);
		}finally {
	        
	        testDA.open();
	        testDA.removeErreklamazioa(errekId, buyerEmail, sellerEmail);
	        testDA.close();
	    }
	}

	@Test
	//Jarraitutako bidea: try1(F) - 3 - IF1(F) - 5 - END
	//Gertatzen da errek == null dela 
	//Eragiketa bertan behera utziko da ez dagoelako erreklamaziorik
	public void test4() {
		boolean b = true;
		long idNegatiboa = -1;

		try {
			sut.open();
			sut.acceptReclamation(b,idNegatiboa);
			sut.close();

			testDA.open();
			Erreklamazioa errek = testDA.getErreklamazioa(idNegatiboa);
			assertNull(errek);
			testDA.close();

		} catch (Exception e) {
			fail();
		}
	}




}


